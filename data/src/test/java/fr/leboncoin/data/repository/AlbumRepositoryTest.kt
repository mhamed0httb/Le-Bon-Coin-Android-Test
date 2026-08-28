package fr.leboncoin.data.repository


import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.AlbumLocalDataSource
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity
import fr.leboncoin.data.source.network.api.AlbumApiService
import fr.leboncoin.data.source.network.model.AlbumDto
import fr.leboncoin.domain.logger.GlobalLogger
import fr.leboncoin.domain.model.Album
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumRepositoryTest {

    private val albumApiService: AlbumApiService = mockk()
    private val albumLocalDataSource: AlbumLocalDataSource = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AlbumRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(GlobalLogger)

        repository = AlbumRepository(
            albumApiService = albumApiService,
            albumLocalDataSource = albumLocalDataSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkObject(GlobalLogger)
    }

    @Test
    fun `getAlbumById delegates to local data source`() = runTest {
        val albumId = 1
        val expectedAlbum = Album(
            id = albumId,
            title = "Local Album",
            url = "url",
            thumbnailUrl = "thumb",
            albumId = 13,
        )
        coEvery { albumLocalDataSource.getAlbumById(albumId) } returns expectedAlbum

        val result = repository.getAlbumById(albumId)

        assertEquals(expectedAlbum, result)
        coVerify(exactly = 1) { albumLocalDataSource.getAlbumById(albumId) }
    }

    @Test
    fun `getAlbumById returns null when local data source returns null`() = runTest {
        val albumId = 99
        coEvery { albumLocalDataSource.getAlbumById(albumId) } returns null

        val result = repository.getAlbumById(albumId)

        assertNull(result)
        coVerify(exactly = 1) { albumLocalDataSource.getAlbumById(albumId) }
    }

    @Test
    fun `getAlbumByIdFlow emits expected album from local data source`() = runTest {
        val albumId = 1
        val expectedAlbum = Album(
            id = albumId,
            title = "Local Album",
            url = "url",
            thumbnailUrl = "thumb",
            albumId = 13,
        )
        every { albumLocalDataSource.getAlbumByIdFlow(albumId) } returns flowOf(expectedAlbum)

        val result = repository.getAlbumByIdFlow(albumId).first()

        assertEquals(expectedAlbum, result)
        verify(exactly = 1) { albumLocalDataSource.getAlbumByIdFlow(albumId) }
    }

    @Test
    fun `toggleFavorite delegates album id to local data source`() = runTest {
        val album = Album(
            id = 42,
            title = "Local Album",
            url = "url",
            thumbnailUrl = "thumb",
            albumId = 13,
        )

        repository.toggleFavorite(album)

        coVerify(exactly = 1) { albumLocalDataSource.toggleFavorite(42) }
    }

    @Test
    fun `getFavoriteAlbumsIds maps local favorite entities to set of album ids`() = runTest {
        val favorites = listOf(
            FavoriteAlbumEntity(albumId = 101),
            FavoriteAlbumEntity(albumId = 102),
            FavoriteAlbumEntity(albumId = 101)
        )
        every { albumLocalDataSource.getFavoriteAlbumsFlow() } returns flowOf(favorites)

        val result = repository.getFavoriteAlbumsIds().first()

        assertEquals(setOf(101, 102), result)
        verify(exactly = 1) { albumLocalDataSource.getFavoriteAlbumsFlow() }
    }

    @Test
    fun `observeIsFavorite delegates to local data source`() = runTest {
        val albumId = 42
        every { albumLocalDataSource.observeIsFavorite(albumId) } returns flowOf(true)

        val result = repository.observeIsFavorite(albumId).first()

        assertTrue(result)
        verify(exactly = 1) { albumLocalDataSource.observeIsFavorite(albumId) }
    }

    @Test
    fun `getAlbumsPaged triggers remote fetch and configures PagingSource`() = runTest {
        val networkAlbums = listOf(
            AlbumDto(id = 1, title = "Net 1", url = "url1", thumbnailUrl = "thumb1", albumId = 13)
        )
        val mockPagingSource = mockk<PagingSource<Int, AlbumEntity>>(relaxed = true)

        coEvery { albumApiService.getAlbums() } returns networkAlbums
        every { albumLocalDataSource.getAlbumsPagingSource() } returns mockPagingSource

        val flow = repository.getAlbumsPaged()

        flow.first()

        coVerify(exactly = 1) { albumApiService.getAlbums() }
        coVerify(exactly = 1) { albumLocalDataSource.saveAlbums(any()) }
        verify(exactly = 1) { albumLocalDataSource.getAlbumsPagingSource() }
    }

    @Test
    fun `getAlbumsPaged catches remote exception and logs error`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { albumApiService.getAlbums() } throws exception
        every { albumLocalDataSource.getAlbumsPagingSource() } returns mockk(relaxed = true)

        val flow = repository.getAlbumsPaged()
        flow.first()

        coVerify(exactly = 1) { albumApiService.getAlbums() }
        coVerify(exactly = 0) { albumLocalDataSource.saveAlbums(any()) }
        verify(exactly = 1) { GlobalLogger.e(exception) }
    }
}