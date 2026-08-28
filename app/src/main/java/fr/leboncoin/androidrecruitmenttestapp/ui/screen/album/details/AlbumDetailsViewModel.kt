package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumDetailsUseCase
import fr.leboncoin.domain.usecase.ToggleAlbumFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val toggleAlbumFavoriteUseCase: ToggleAlbumFavoriteUseCase,
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
) : ViewModel() {

    private val _albumId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<AlbumDetailsUiState> = _albumId
        .filterNotNull()
        .flatMapLatest { id ->
            getAlbumDetailsUseCase.invoke(id)
        }
        .map { pair ->
            val album = pair.first
            val isFavorite = pair.second
            if (album != null) {
                AlbumDetailsUiState.Found(album, isFavorite)
            } else {
                AlbumDetailsUiState.NotFound
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AlbumDetailsUiState.INIT
        )

    fun getAlbum(id: Int) {
        viewModelScope.launch {
            _albumId.update { id }
        }
    }

    fun toggleISFavorite(album: Album) {
        viewModelScope.launch {
            toggleAlbumFavoriteUseCase.invoke(album)
        }
    }
}
