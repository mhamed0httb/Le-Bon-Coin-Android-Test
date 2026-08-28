package fr.leboncoin.data.source.local

import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    fun getAlbumsPagingSource(): PagingSource<Int, AlbumEntity>
    suspend fun getAlbumById(id: Int): Album?
    fun getAlbumByIdFlow(id: Int): Flow<Album?>
    suspend fun saveAlbums(albums: List<Album>)
    suspend fun clearAlbums()
    suspend fun toggleFavorite(id: Int)
    fun getFavoriteAlbumsFlow(): Flow<List<FavoriteAlbumEntity>>
    fun observeIsFavorite(albumId: Int): Flow<Boolean>
}
