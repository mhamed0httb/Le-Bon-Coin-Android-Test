package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import fr.leboncoin.domain.usecase.GetFavoriteAlbumsIdsUseCase
import fr.leboncoin.domain.usecase.ToggleAlbumFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsUseCase: GetAlbumsUseCase,
    getFavoriteAlbumsIdsUseCase: GetFavoriteAlbumsIdsUseCase,
    private val toggleAlbumFavoriteUseCase: ToggleAlbumFavoriteUseCase,
) : ViewModel() {

    val favorites: StateFlow<Set<Int>> = getFavoriteAlbumsIdsUseCase.invoke().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptySet()
    )

    val pagedAlbums: Flow<PagingData<Album>> =
        getAlbumsUseCase.invoke()
            .cachedIn(viewModelScope)

    private val _showOnlyFavorites = MutableStateFlow<Boolean>(false)
    val showOnlyFavorites = _showOnlyFavorites.asStateFlow()

    fun toggleFavorite(album: Album) {
        viewModelScope.launch {
            toggleAlbumFavoriteUseCase.invoke(album)
        }
    }

    fun toggleOnlyFavorites() {
        _showOnlyFavorites.update { current -> !current }
    }
}
