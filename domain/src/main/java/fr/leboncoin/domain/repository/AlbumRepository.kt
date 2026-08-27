package fr.leboncoin.domain.repository

import androidx.paging.PagingData
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun getAlbums(): List<Album>
    fun getAlbumsFlow(): Flow<List<Album>>
    fun getAlbumsPaged(): Flow<PagingData<Album>>
    suspend fun getAlbumById(id: Int): Album?
    suspend fun saveAlbum(album: Album)
    suspend fun deleteAlbum(id: Int)
}
