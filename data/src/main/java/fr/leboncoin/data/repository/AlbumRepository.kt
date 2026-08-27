package fr.leboncoin.data.repository

import fr.leboncoin.data.source.network.api.AlbumApiService
import fr.leboncoin.data.source.network.model.toDomain
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumApiService: AlbumApiService,
) : DomainAlbumRepository {
    
    override suspend fun getAlbums(): List<Album> {
        return albumApiService.getAlbums().toDomain()
    }
}
