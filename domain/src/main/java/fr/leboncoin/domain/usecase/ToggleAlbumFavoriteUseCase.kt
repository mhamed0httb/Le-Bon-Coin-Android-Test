package fr.leboncoin.domain.usecase

import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import javax.inject.Inject

class ToggleAlbumFavoriteUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    suspend fun invoke(album: Album) {
        return albumRepository.toggleFavorite(album)
    }
}
