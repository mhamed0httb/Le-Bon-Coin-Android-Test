package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import fr.leboncoin.domain.model.Album

sealed class AlbumDetailsUiState {
    data object INIT : AlbumDetailsUiState()
    data object NotFound : AlbumDetailsUiState()
    data class Found(val album: Album, val isFavorite: Boolean) : AlbumDetailsUiState()
}