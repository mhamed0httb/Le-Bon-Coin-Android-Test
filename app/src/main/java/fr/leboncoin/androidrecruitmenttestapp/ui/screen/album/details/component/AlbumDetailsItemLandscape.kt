package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.components.chips.ChipTinted
import fr.leboncoin.androidrecruitmenttestapp.R
import fr.leboncoin.domain.model.Album

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailItemLandscape(
    modifier: Modifier = Modifier,
    album: Album,
    onBackClick: () -> Unit,
) {

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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.url)
                    .size(600, 600)
                    .build(),
                contentDescription = album.title,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ChipTinted(
                        text = "Album #${album.albumId}"
                    )
                    ChipTinted(
                        text = "Track #${album.id}"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Album Details Preview")
@Composable
private fun AlbumDetailItemLandscapePreview() {
    SparkTheme {
        AlbumDetailItemLandscape(
            album = Album(
                id = 1,
                albumId = 42,
                title = "accusamus ea eos quam nesciunt eius",
                url = "https://placehold.co/600x600/771796/white/png",
                thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
            ),
            onBackClick = {}
        )
    }
}