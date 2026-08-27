package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.model.AlbumsUiState
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    getAlbumsUseCase: GetAlbumsUseCase,
) : ViewModel() {

    val uiState: StateFlow<AlbumsUiState> =
        getAlbumsUseCase.invoke()
            .map { AlbumsUiState(albums = it, isLoading = false) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AlbumsUiState(isLoading = true)
            )
}
