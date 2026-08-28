package fr.leboncoin.domain.usecase

import fr.leboncoin.domain.repository.AlbumRepository
import javax.inject.Inject

class GetFavoriteAlbumsIdsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    fun invoke() = albumRepository.getFavoriteAlbumsIds()
}
