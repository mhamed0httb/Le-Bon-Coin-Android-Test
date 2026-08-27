package fr.leboncoin.androidrecruitmenttestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel(
    private val getAlbumsUseCase: GetAlbumsUseCase,
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    fun loadAlbums() {
        viewModelScope.launch {
            try {
                _albums.emit(getAlbumsUseCase())
            } catch (_: Exception) { /* TODO: Handle errors */
            }
        }
    }

    class Factory(
        private val getAlbumsUseCase: GetAlbumsUseCase,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AlbumsViewModel(getAlbumsUseCase) as T
        }
    }
}