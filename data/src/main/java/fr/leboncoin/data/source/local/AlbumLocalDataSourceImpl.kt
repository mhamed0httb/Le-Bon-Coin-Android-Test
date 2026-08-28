package fr.leboncoin.data.source.local

import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.dao.AlbumDao
import fr.leboncoin.data.source.local.dao.FavoriteAlbumDao
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity
import fr.leboncoin.data.source.local.mapper.toDomain
import fr.leboncoin.data.source.local.mapper.toEntity
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumLocalDataSourceImpl @Inject constructor(
    private val albumDao: AlbumDao,
    private val favoriteAlbumDao: FavoriteAlbumDao,
) : AlbumLocalDataSource {

    override fun getAlbumsPagingSource(): PagingSource<Int, AlbumEntity> {
        return albumDao.getAlbumsPagingSource()
    }

    override suspend fun getAlbumById(id: Int): Album? {
        return albumDao.getAlbumById(id)?.toDomain()
    }

    override fun getAlbumByIdFlow(id: Int): Flow<Album?> {
        return albumDao.getAlbumByIdFlow(id).map { it?.toDomain() }
    }

    override suspend fun saveAlbums(albums: List<Album>) {
        albumDao.insertAlbums(albums.map { it.toEntity() })
    }

    override suspend fun clearAlbums() {
        albumDao.clearAlbums()
    }

    override suspend fun toggleFavorite(id: Int) {
        favoriteAlbumDao.toggleFavorite(id)
    }

    override fun getFavoriteAlbumsFlow(): Flow<List<FavoriteAlbumEntity>> {
        return favoriteAlbumDao.getAllFavorites()
    }

    override fun observeIsFavorite(albumId: Int): Flow<Boolean> {
        return favoriteAlbumDao.observeIsFavorite(albumId)
    }
}
