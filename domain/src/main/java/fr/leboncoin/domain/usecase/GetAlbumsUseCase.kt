package fr.leboncoin.domain.usecase

import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    suspend operator fun invoke(): List<Album> {
        return albumRepository.getAlbums()
    }
}
