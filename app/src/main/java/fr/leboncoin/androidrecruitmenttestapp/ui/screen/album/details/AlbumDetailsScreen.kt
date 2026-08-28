package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.component.AlbumDetailItem
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.component.AlbumDetailItemLandscape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    albumId: Int,
    onBackClick: () -> Unit,
    viewModel: AlbumDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(uiState) {
        if (uiState == AlbumDetailsUiState.NotFound) {
            onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAlbum(albumId)
    }

    (uiState as? AlbumDetailsUiState.Found)?.album?.let { album ->
        if (isLandscape) {
            AlbumDetailItemLandscape(
                modifier = modifier,
                album = album,
                onBackClick = onBackClick
            )
        } else {
            AlbumDetailItem(
                modifier = modifier,
                album = album,
                onBackClick = onBackClick
            )
        }
    }
}