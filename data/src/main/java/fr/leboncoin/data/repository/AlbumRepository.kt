package fr.leboncoin.data.repository

import fr.leboncoin.data.source.local.AlbumLocalDataSource
import fr.leboncoin.data.source.network.api.AlbumApiService
import fr.leboncoin.data.source.network.model.toDomain
import fr.leboncoin.domain.logger.GlobalLogger
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository

class AlbumRepository @Inject constructor(
    private val albumApiService: AlbumApiService,
    private val albumLocalDataSource: AlbumLocalDataSource,
) : DomainAlbumRepository {

    override suspend fun getAlbums(): List<Album> {
        return try {
            val remoteAlbums = albumApiService.getAlbums().toDomain()
//            albumLocalDataSource.clearAlbums()
            albumLocalDataSource.saveAlbums(remoteAlbums)
            remoteAlbums
        } catch (e: Exception) {
            albumLocalDataSource.getAlbums()
        }
    }

    override fun getAlbumsFlow(): Flow<List<Album>> = flow {
        emitAll(
            albumLocalDataSource.getAlbumsFlow()
        )
    }.onStart {
        fetchAndSaveRemoteAlbums()
    }

    override suspend fun getAlbumById(id: Int): Album? = albumLocalDataSource.getAlbumById(id)

    override suspend fun saveAlbum(album: Album) = albumLocalDataSource.saveAlbum(album)

    override suspend fun deleteAlbum(id: Int) = albumLocalDataSource.deleteAlbum(id)

    private suspend fun fetchAndSaveRemoteAlbums() {
        try {
            val remoteAlbums = albumApiService.getAlbums().toDomain()
//            albumLocalDataSource.clearAlbums()
            albumLocalDataSource.saveAlbums(remoteAlbums)
        } catch (e: Exception) {
            GlobalLogger.e(e)
        }
    }
}
