package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.adevinta.spark.components.progress.Spinner
import com.adevinta.spark.components.scaffold.Scaffold
import fr.leboncoin.androidrecruitmenttestapp.ui.AlbumItem
import fr.leboncoin.domain.model.Album

@Composable
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    onItemSelected: (Album) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagedAlbums = viewModel.pagedAlbums.collectAsLazyPagingItems()

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
                        AlbumItem(
                            album = album,
                            onItemSelected = onItemSelected,
                        )
                    }
                }
            }
        }
    }
}
