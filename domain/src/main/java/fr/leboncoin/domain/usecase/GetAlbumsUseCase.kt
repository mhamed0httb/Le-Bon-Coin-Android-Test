package fr.leboncoin.domain.usecase

import androidx.paging.PagingData
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    fun invoke(): Flow<PagingData<Album>> {
        return albumRepository.getAlbumsPaged()
    }
}
