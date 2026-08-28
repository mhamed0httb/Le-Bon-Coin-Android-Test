package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.leboncoin.androidrecruitmenttestapp.R
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

    (uiState as? AlbumDetailsUiState.Found)?.let { state ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.screen_details)) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            modifier = modifier
        ) { innerPadding ->
            if (isLandscape) {
                AlbumDetailItemLandscape(
                    modifier = Modifier.padding(innerPadding),
                    album = state.album,
                    onFavoriteToggle = {
                        viewModel.toggleISFavorite(it)
                    },
                    isFavorite = state.isFavorite
                )
            } else {
                AlbumDetailItem(
                    modifier = Modifier.padding(innerPadding),
                    album = state.album,
                    onFavoriteToggle = {
                        viewModel.toggleISFavorite(it)
                    },
                    isFavorite = state.isFavorite
                )
            }
        }
    }
}