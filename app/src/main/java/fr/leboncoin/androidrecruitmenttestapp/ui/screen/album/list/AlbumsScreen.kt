package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.adevinta.spark.components.progress.Spinner
import com.adevinta.spark.components.scaffold.Scaffold
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.component.AlbumItem
import fr.leboncoin.domain.model.Album

@Composable
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    onItemSelected: (Album) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val pagedAlbums = viewModel.pagedAlbums.collectAsLazyPagingItems()
    val favoritesIds by viewModel.favorites.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier) { paddingValues ->
        if (pagedAlbums.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Spinner()
            }
        } else {
            if (isLandscape) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = paddingValues
                ) {
                    items(
                        count = pagedAlbums.itemCount,
                        key = pagedAlbums.itemKey { it.id }
                    ) { index ->
                        pagedAlbums[index]?.let { album ->
                            val isFavorite by remember(album.id, favoritesIds) {
                                derivedStateOf { favoritesIds.contains(album.id) }
                            }
                            AlbumItem(
                                album = album,
                                onItemSelected = onItemSelected,
                                onFavoriteToggle = {
                                    viewModel.toggleFavorite(it)
                                },
                                isFavorite = isFavorite
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = paddingValues,
                ) {
                    items(
                        count = pagedAlbums.itemCount,
                        key = pagedAlbums.itemKey { it.id }
                    ) { index ->
                        pagedAlbums[index]?.let { album ->
                            val isFavorite by remember(album.id, favoritesIds) {
                                derivedStateOf { favoritesIds.contains(album.id) }
                            }
                            AlbumItem(
                                album = album,
                                onItemSelected = onItemSelected,
                                onFavoriteToggle = {
                                    viewModel.toggleFavorite(it)
                                },
                                isFavorite = isFavorite
                            )
                        }
                    }
                }
            }
        }
    }
}
