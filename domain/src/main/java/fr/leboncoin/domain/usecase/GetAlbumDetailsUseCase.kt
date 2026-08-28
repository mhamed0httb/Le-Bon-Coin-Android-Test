package fr.leboncoin.domain.usecase

import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    fun invoke(albumId: Int): Flow<Pair<Album?, Boolean>> {
        return combine(
            albumRepository.getAlbumByIdFlow(albumId),
            albumRepository.observeIsFavorite(albumId)
        ) { album, isFavorite ->
            Pair(album, isFavorite)
        }
    }
}
