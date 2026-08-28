package fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.adevinta.spark.ExperimentalSparkApi
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.components.card.Card
import com.adevinta.spark.components.chips.ChipTinted
import fr.leboncoin.androidrecruitmenttestapp.ui.component.FavoriteIcon
import fr.leboncoin.domain.model.Album

@OptIn(ExperimentalSparkApi::class)
@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onFavoriteToggle: (Album) -> Unit,
    onItemSelected: (Album) -> Unit,
    isFavorite: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp),
        onClick = { onItemSelected(album) },
    ) {
        Row {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.thumbnailUrl)
                    .size(150, 150)
                    .build(),
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = album.title,
                        style = SparkTheme.typography.caption,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    FavoriteIcon(
                        onClick = { onFavoriteToggle(album) },
                        isFavorite = isFavorite
                    )
                }

                Spacer(Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

@OptIn(ExperimentalSparkApi::class)
@Preview(showBackground = true, name = "Album Item Preview")
@PreviewScreenSizes
@Composable
private fun AlbumItemPreview() {
    val sampleAlbum = Album(
        id = 1,
        albumId = 42,
        title = "accusamus ea eos quam nesciunt eius",
        url = "https://placehold.co/600x600/771796/white/png",
        thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
    )

    SparkTheme {
        AlbumItem(
            album = sampleAlbum,
            isFavorite = true,
            onFavoriteToggle = {},
            onItemSelected = {}
        )
    }
}
