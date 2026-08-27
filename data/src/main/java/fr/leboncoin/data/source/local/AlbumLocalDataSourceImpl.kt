package fr.leboncoin.data.source.local

import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.dao.AlbumDao
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.toDomain
import fr.leboncoin.data.source.local.entity.toEntity
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumLocalDataSourceImpl @Inject constructor(
    private val albumDao: AlbumDao
) : AlbumLocalDataSource {

    override fun getAlbumsFlow(): Flow<List<Album>> {
        return albumDao.getAlbumsFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAlbumsPagingSource(): PagingSource<Int, AlbumEntity> {
        return albumDao.getAlbumsPagingSource()
    }

    override suspend fun getAlbums(): List<Album> {
        return albumDao.getAlbums().map { it.toDomain() }
    }

    override suspend fun getAlbumById(id: Int): Album? {
        return albumDao.getAlbumById(id)?.toDomain()
    }

    override suspend fun saveAlbum(album: Album) {
        albumDao.insertAlbum(album.toEntity())
    }

    override suspend fun deleteAlbum(id: Int) {
        albumDao.deleteAlbum(id)
    }

    override suspend fun saveAlbums(albums: List<Album>) {
        albumDao.insertAlbums(albums.map { it.toEntity() })
    }

    override suspend fun clearAlbums() {
        albumDao.clearAlbums()
    }
}
