package fr.leboncoin.data.repository

import fr.leboncoin.data.network.api.AlbumApiService
import fr.leboncoin.data.network.model.toDomain
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository

class AlbumRepository(
    private val albumApiService: AlbumApiService,
) : DomainAlbumRepository {
    
    override suspend fun getAlbums(): List<Album> {
        return albumApiService.getAlbums().toDomain()
    }
}
