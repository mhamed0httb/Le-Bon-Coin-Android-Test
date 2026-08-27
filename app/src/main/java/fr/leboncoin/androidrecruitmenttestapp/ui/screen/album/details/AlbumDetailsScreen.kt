package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.component.AlbumDetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    albumId: Int,
    onBackClick: () -> Unit,
    viewModel: AlbumDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState == AlbumDetailsUiState.NotFound) {
            onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAlbum(albumId)
    }

    (uiState as? AlbumDetailsUiState.Found)?.album?.let { album ->
        AlbumDetailItem(
            modifier = modifier,
            album = album,
            onBackClick = onBackClick
        )
    }
}