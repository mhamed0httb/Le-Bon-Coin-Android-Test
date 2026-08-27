package fr.leboncoin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import fr.leboncoin.data.source.local.AlbumLocalDataSource
import fr.leboncoin.data.source.local.entity.toDomain
import fr.leboncoin.data.source.network.api.AlbumApiService
import fr.leboncoin.data.source.network.model.toDomain
import fr.leboncoin.domain.logger.GlobalLogger
import fr.leboncoin.domain.model.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository

class AlbumRepository @Inject constructor(
    private val albumApiService: AlbumApiService,
    private val albumLocalDataSource: AlbumLocalDataSource,
) : DomainAlbumRepository {

    override suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        try {
            val remoteAlbums = albumApiService.getAlbums().toDomain()
            albumLocalDataSource.saveAlbums(remoteAlbums)
            remoteAlbums
        } catch (e: Exception) {
            albumLocalDataSource.getAlbums()
        }
    }

    override fun getAlbumsFlow(): Flow<List<Album>> = flow {
        CoroutineScope(currentCoroutineContext()).launch {
            fetchAndSaveRemoteAlbums()
        }

        emitAll(albumLocalDataSource.getAlbumsFlow())
    }

    override fun getAlbumsPaged(): Flow<PagingData<Album>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { albumLocalDataSource.getAlbumsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getAlbumById(id: Int): Album? = withContext(Dispatchers.IO) {
        albumLocalDataSource.getAlbumById(id)
    }

    override suspend fun saveAlbum(album: Album) = withContext(Dispatchers.IO) {
        albumLocalDataSource.saveAlbum(album)
    }

    override suspend fun deleteAlbum(id: Int) = withContext(Dispatchers.IO) {
        albumLocalDataSource.deleteAlbum(id)
    }

    private suspend fun fetchAndSaveRemoteAlbums() {
        GlobalLogger.i("START fetchAndSaveRemoteAlbums")
        try {
            val remoteAlbums = albumApiService.getAlbums().toDomain()
            albumLocalDataSource.saveAlbums(remoteAlbums)
            GlobalLogger.i("END fetchAndSaveRemoteAlbums")
        } catch (e: Exception) {
            GlobalLogger.e(e)
        }
    }
}
