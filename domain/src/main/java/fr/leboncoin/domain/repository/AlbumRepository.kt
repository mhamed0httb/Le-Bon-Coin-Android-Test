package fr.leboncoin.domain.repository

import androidx.paging.PagingData
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAlbumsPaged(): Flow<PagingData<Album>>
    suspend fun getAlbumById(id: Int): Album?
    fun getAlbumByIdFlow(id: Int): Flow<Album?>
    suspend fun toggleFavorite(album: Album)
    fun getFavoriteAlbumsIds(): Flow<Set<Int>>
    fun observeIsFavorite(albumId: Int): Flow<Boolean>
}
