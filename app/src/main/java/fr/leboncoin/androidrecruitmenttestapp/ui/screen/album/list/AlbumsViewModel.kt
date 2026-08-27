package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsUseCase: GetAlbumsUseCase,
) : ViewModel() {

    val pagedAlbums: Flow<PagingData<Album>> =
        getAlbumsUseCase.invoke()
            .cachedIn(viewModelScope)
}
