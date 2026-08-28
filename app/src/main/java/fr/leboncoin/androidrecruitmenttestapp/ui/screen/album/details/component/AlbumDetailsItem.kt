package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.components.chips.ChipTinted
import fr.leboncoin.androidrecruitmenttestapp.ui.component.FavoriteIcon
import fr.leboncoin.domain.model.Album

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailItem(
    modifier: Modifier = Modifier,
    album: Album,
    onFavoriteToggle: (Album) -> Unit,
    isFavorite: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = album.url,
            contentDescription = album.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = album.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                FavoriteIcon(
                    onClick = { onFavoriteToggle(album) },
                    isFavorite = isFavorite
                )
            }

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

@Preview(showBackground = true, name = "Album Details Preview")
@PreviewScreenSizes
@Composable
private fun AlbumDetailItemPreview() {
    SparkTheme {
        AlbumDetailItem(
            album = Album(
                id = 1,
                albumId = 42,
                title = "accusamus ea eos quam nesciunt eius",
                url = "https://placehold.co/600x600/771796/white/png",
                thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
            ),
            onFavoriteToggle = {},
            isFavorite = false,
        )
    }
}