package fr.leboncoin.data.source.local

import androidx.paging.PagingSource
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    fun getAlbumsFlow(): Flow<List<Album>>
    fun getAlbumsPagingSource(): PagingSource<Int, AlbumEntity>
    suspend fun getAlbums(): List<Album>
    suspend fun getAlbumById(id: Int): Album?
    suspend fun saveAlbum(album: Album)
    suspend fun deleteAlbum(id: Int)
    suspend fun saveAlbums(albums: List<Album>)
    suspend fun clearAlbums()
}
