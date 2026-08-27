package fr.leboncoin.androidrecruitmenttestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
) : ViewModel() {

    val albums =
        getAlbumsUseCase.invoke()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}
