package fr.leboncoin.data.source.local

import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    fun getAlbumsFlow(): Flow<List<Album>>
    suspend fun getAlbums(): List<Album>
    suspend fun getAlbumById(id: Int): Album?
    suspend fun saveAlbum(album: Album)
    suspend fun deleteAlbum(id: Int)
    suspend fun saveAlbums(albums: List<Album>)
    suspend fun clearAlbums()
}
