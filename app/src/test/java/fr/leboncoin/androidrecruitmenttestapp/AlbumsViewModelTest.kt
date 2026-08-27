package fr.leboncoin.androidrecruitmenttestapp

import app.cash.turbine.test
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `albums flow emits albums from use case`() = runTest {
        val albums = listOf(
            Album(id = 1, albumId = 1, title = "Album 1", url = "url1", thumbnailUrl = "thumb1"),
            Album(id = 2, albumId = 1, title = "Album 2", url = "url2", thumbnailUrl = "thumb2")
        )
        val fakeRepository = object : AlbumRepository {
            override suspend fun getAlbums(): List<Album> = albums
            override fun getAlbumsFlow(): Flow<List<Album>> = flowOf(albums)
            override suspend fun getAlbumById(id: Int): Album? = null
            override suspend fun saveAlbum(album: Album) {}
            override suspend fun deleteAlbum(id: Int) {}
        }
        val useCase = GetAlbumsUseCase(fakeRepository)
        val viewModel = AlbumsViewModel(useCase)

        viewModel.albums.test {
            val result = awaitItem()
            assertEquals(albums, result)
        }
    }
}
