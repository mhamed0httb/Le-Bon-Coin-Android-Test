package fr.leboncoin.domain.usecase

import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    suspend fun invoke(albumId: Int): Album? {
        return albumRepository.getAlbumById(albumId)
    }
}
