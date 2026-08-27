package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.model

import fr.leboncoin.domain.model.Album

data class AlbumsUiState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false
)