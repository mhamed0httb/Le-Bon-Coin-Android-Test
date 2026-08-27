package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.domain.usecase.GetAlbumDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumDetailsUiState>(AlbumDetailsUiState.INIT)
    val uiState = _uiState.asStateFlow()


    fun getAlbum(id: Int) {
        viewModelScope.launch {
            val album = getAlbumDetailsUseCase.invoke(id)
            val state = if (album != null) {
                AlbumDetailsUiState.Found(album)
            } else {
                AlbumDetailsUiState.NotFound
            }
            _uiState.update { state }
        }
    }
}
