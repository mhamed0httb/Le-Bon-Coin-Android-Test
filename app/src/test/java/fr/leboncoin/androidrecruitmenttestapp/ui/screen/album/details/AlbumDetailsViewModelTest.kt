package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details


import app.cash.turbine.test
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumDetailsUseCase
import fr.leboncoin.domain.usecase.ToggleAlbumFavoriteUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
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
class AlbumDetailsViewModelTest {

    private val toggleAlbumFavoriteUseCase: ToggleAlbumFavoriteUseCase = mockk(relaxed = true)
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: AlbumDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        viewModel = AlbumDetailsViewModel(
            toggleAlbumFavoriteUseCase = toggleAlbumFavoriteUseCase,
            getAlbumDetailsUseCase = getAlbumDetailsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initial state is INIT`() = runTest {
        viewModel.uiState.test {
            assertEquals(AlbumDetailsUiState.INIT, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbum emits Found state when album exists`() = runTest {
        val albumId = 1
        val album = Album(id = albumId, albumId = 13, title = "Album 1", url = "url", thumbnailUrl = "thumb")
        every { getAlbumDetailsUseCase.invoke(albumId) } returns flowOf(Pair(album, true))

        viewModel.uiState.test {
            assertEquals(AlbumDetailsUiState.INIT, awaitItem())

            viewModel.getAlbum(albumId)

            val state = awaitItem()
            assertEquals(AlbumDetailsUiState.Found(album, isFavorite = true), state)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbum emits NotFound state when album is null`() = runTest {
        val albumId = 99
        every { getAlbumDetailsUseCase.invoke(albumId) } returns flowOf(Pair(null, false))

        viewModel.uiState.test {
            assertEquals(AlbumDetailsUiState.INIT, awaitItem())

            viewModel.getAlbum(albumId)

            val state = awaitItem()
            assertEquals(AlbumDetailsUiState.NotFound, state)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState updates dynamically when getAlbumDetailsUseCase flow emits new favorite state`() = runTest {
        val albumId = 1
        val album = Album(id = albumId, albumId = 13, title = "Album 1", url = "url", thumbnailUrl = "thumb")
        val detailsFlow = MutableStateFlow<Pair<Album?, Boolean>>(Pair(album, false))
        every { getAlbumDetailsUseCase.invoke(albumId) } returns detailsFlow

        viewModel.uiState.test {
            assertEquals(AlbumDetailsUiState.INIT, awaitItem())

            viewModel.getAlbum(albumId)
            assertEquals(AlbumDetailsUiState.Found(album, isFavorite = false), awaitItem())

            detailsFlow.value = Pair(album, true)
            assertEquals(AlbumDetailsUiState.Found(album, isFavorite = true), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleISFavorite delegates album to toggleAlbumFavoriteUseCase`() = runTest {
        val album = Album(id = 42, albumId = 13, title = "Album 42", url = "url", thumbnailUrl = "thumb")

        viewModel.toggleISFavorite(album)

        coVerify(exactly = 1) { toggleAlbumFavoriteUseCase.invoke(album) }
    }
}