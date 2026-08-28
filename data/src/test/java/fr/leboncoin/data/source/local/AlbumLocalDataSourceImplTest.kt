package fr.leboncoin.data.source.local


import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.dao.AlbumDao
import fr.leboncoin.data.source.local.dao.FavoriteAlbumDao
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity
import fr.leboncoin.domain.model.Album
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AlbumLocalDataSourceImplTest {

    private val albumDao: AlbumDao = mockk(relaxed = true)
    private val favoriteAlbumDao: FavoriteAlbumDao = mockk(relaxed = true)

    private lateinit var dataSource: AlbumLocalDataSourceImpl

    @Before
    fun setUp() {
        dataSource = AlbumLocalDataSourceImpl(albumDao, favoriteAlbumDao)
    }

    @Test
    fun `getAlbumsPagingSource delegates to albumDao`() {
        val mockPagingSource = mockk<PagingSource<Int, AlbumEntity>>()
        every { albumDao.getAlbumsPagingSource() } returns mockPagingSource

        val result = dataSource.getAlbumsPagingSource()

        assertEquals(mockPagingSource, result)
        verify(exactly = 1) { albumDao.getAlbumsPagingSource() }
    }

    @Test
    fun `getAlbumById returns domain album when entity exists`() = runTest {
        val albumId = 1
        val entity =
            AlbumEntity(id = albumId, title = "Test Album", url = "url", thumbnailUrl = "thumb", albumId = 13)
        coEvery { albumDao.getAlbumById(albumId) } returns entity

        val result = dataSource.getAlbumById(albumId)

        assertEquals(albumId, result?.id)
        assertEquals("Test Album", result?.title)
        coVerify(exactly = 1) { albumDao.getAlbumById(albumId) }
    }

    @Test
    fun `getAlbumById returns null when entity does not exist`() = runTest {
        val albumId = 999
        coEvery { albumDao.getAlbumById(albumId) } returns null

        val result = dataSource.getAlbumById(albumId)

        assertNull(result)
        coVerify(exactly = 1) { albumDao.getAlbumById(albumId) }
    }

    @Test
    fun `getAlbumByIdFlow maps entity flow to domain model flow`() = runTest {
        val albumId = 1
        val entity = AlbumEntity(id = albumId, title = "Flow Album", url = "url", thumbnailUrl = "thumb", albumId = 13)
        every { albumDao.getAlbumByIdFlow(albumId) } returns flowOf(entity)

        val result = dataSource.getAlbumByIdFlow(albumId).first()

        assertEquals(albumId, result?.id)
        assertEquals("Flow Album", result?.title)
        verify(exactly = 1) { albumDao.getAlbumByIdFlow(albumId) }
    }

    @Test
    fun `saveAlbums converts domain models to entities and inserts into albumDao`() = runTest {
        val domainAlbums = listOf(
            Album(id = 1, title = "Album 1", url = "url1", thumbnailUrl = "thumb1", albumId = 13),
            Album(id = 2, title = "Album 2", url = "url2", thumbnailUrl = "thumb2", albumId = 13)
        )

        dataSource.saveAlbums(domainAlbums)

        coVerify(exactly = 1) {
            albumDao.insertAlbums(withArg { entities ->
                assertEquals(2, entities.size)
                assertEquals(1, entities[0].id)
                assertEquals("Album 1", entities[0].title)
            })
        }
    }

    @Test
    fun `clearAlbums delegates to albumDao`() = runTest {
        dataSource.clearAlbums()

        coVerify(exactly = 1) { albumDao.clearAlbums() }
    }

    @Test
    fun `toggleFavorite delegates to favoriteAlbumDao`() = runTest {
        val albumId = 42

        dataSource.toggleFavorite(albumId)

        coVerify(exactly = 1) { favoriteAlbumDao.toggleFavorite(albumId) }
    }

    @Test
    fun `getFavoriteAlbumsFlow delegates to favoriteAlbumDao`() = runTest {
        val favoriteEntities = listOf(FavoriteAlbumEntity(albumId = 42))
        every { favoriteAlbumDao.getAllFavorites() } returns flowOf(favoriteEntities)

        val result = dataSource.getFavoriteAlbumsFlow().first()

        assertEquals(favoriteEntities, result)
        verify(exactly = 1) { favoriteAlbumDao.getAllFavorites() }
    }

    @Test
    fun `observeIsFavorite delegates to favoriteAlbumDao`() = runTest {
        val albumId = 42
        every { favoriteAlbumDao.observeIsFavorite(albumId) } returns flowOf(true)

        val result = dataSource.observeIsFavorite(albumId).first()

        assertEquals(true, result)
        verify(exactly = 1) { favoriteAlbumDao.observeIsFavorite(albumId) }
    }
}