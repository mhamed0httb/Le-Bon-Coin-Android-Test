package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list


import androidx.paging.PagingData
import app.cash.turbine.test
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import fr.leboncoin.domain.usecase.GetFavoriteAlbumsIdsUseCase
import fr.leboncoin.domain.usecase.ToggleAlbumFavoriteUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumsViewModelTest {

    private val getAlbumsUseCase: GetAlbumsUseCase = mockk()
    private val getFavoriteAlbumsIdsUseCase: GetFavoriteAlbumsIdsUseCase = mockk()
    private val toggleAlbumFavoriteUseCase: ToggleAlbumFavoriteUseCase = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: AlbumsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { getAlbumsUseCase.invoke() } returns flowOf(PagingData.empty())
        every { getFavoriteAlbumsIdsUseCase.invoke() } returns flowOf(emptySet())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `favorites emits initial empty set then updates when use case emits new set`() = runTest {
        val favoritesFlow = MutableStateFlow(setOf(1, 2))
        every { getFavoriteAlbumsIdsUseCase.invoke() } returns favoritesFlow

        viewModel = AlbumsViewModel(
            getAlbumsUseCase = getAlbumsUseCase,
            getFavoriteAlbumsIdsUseCase = getFavoriteAlbumsIdsUseCase,
            toggleAlbumFavoriteUseCase = toggleAlbumFavoriteUseCase
        )

        viewModel.favorites.test {
            assertEquals(setOf(1, 2), awaitItem())

            favoritesFlow.value = setOf(1, 2, 3)
            assertEquals(setOf(1, 2, 3), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { getFavoriteAlbumsIdsUseCase.invoke() }
    }

    @Test
    fun `pagedAlbums subscribes to getAlbumsUseCase`() = runTest {
        val pagingData = PagingData.empty<Album>()
        every { getAlbumsUseCase.invoke() } returns flowOf(pagingData)

        viewModel = AlbumsViewModel(
            getAlbumsUseCase = getAlbumsUseCase,
            getFavoriteAlbumsIdsUseCase = getFavoriteAlbumsIdsUseCase,
            toggleAlbumFavoriteUseCase = toggleAlbumFavoriteUseCase
        )

        viewModel.pagedAlbums.test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { getAlbumsUseCase.invoke() }
    }

    @Test
    fun `toggleFavorite delegates target album to toggleAlbumFavoriteUseCase`() = runTest {
        viewModel = AlbumsViewModel(
            getAlbumsUseCase = getAlbumsUseCase,
            getFavoriteAlbumsIdsUseCase = getFavoriteAlbumsIdsUseCase,
            toggleAlbumFavoriteUseCase = toggleAlbumFavoriteUseCase
        )
        val album = Album(id = 42, title = "Test Album", url = "url", thumbnailUrl = "thumb", albumId = 13)

        viewModel.toggleFavorite(album)

        coVerify(exactly = 1) { toggleAlbumFavoriteUseCase.invoke(album) }
    }
}