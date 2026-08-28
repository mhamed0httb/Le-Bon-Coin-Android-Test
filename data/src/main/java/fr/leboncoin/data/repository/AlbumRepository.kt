package fr.leboncoin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import fr.leboncoin.data.source.local.AlbumLocalDataSource
import fr.leboncoin.data.source.local.mapper.toDomain
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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository

class AlbumRepository @Inject constructor(
    private val albumApiService: AlbumApiService,
    private val albumLocalDataSource: AlbumLocalDataSource,
) : DomainAlbumRepository {

    override fun getAlbumsPaged(): Flow<PagingData<Album>> = flow {
        CoroutineScope(currentCoroutineContext()).launch {
            fetchAndSaveRemoteAlbums()
        }

        val flow = Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { albumLocalDataSource.getAlbumsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }

        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    override suspend fun getAlbumById(id: Int): Album? = withContext(Dispatchers.IO) {
        albumLocalDataSource.getAlbumById(id)
    }

    override fun getAlbumByIdFlow(id: Int): Flow<Album?> {
        return albumLocalDataSource.getAlbumByIdFlow(id).flowOn(Dispatchers.IO)
    }

    override suspend fun toggleFavorite(album: Album) = withContext(Dispatchers.IO) {
        albumLocalDataSource.toggleFavorite(album.id)
    }

    override fun getFavoriteAlbumsIds(): Flow<Set<Int>> {
        return albumLocalDataSource.getFavoriteAlbumsFlow()
            .map { albums ->
                albums.mapTo(hashSetOf()) { it.albumId }
            }.flowOn(Dispatchers.IO)
    }

    override fun observeIsFavorite(albumId: Int): Flow<Boolean> {
        return albumLocalDataSource.observeIsFavorite(albumId).flowOn(Dispatchers.IO)
    }

    private suspend fun fetchAndSaveRemoteAlbums() = withContext(Dispatchers.IO) {
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
