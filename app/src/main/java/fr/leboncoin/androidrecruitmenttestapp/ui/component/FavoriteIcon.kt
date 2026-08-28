package fr.leboncoin.androidrecruitmenttestapp.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.leboncoin.androidrecruitmenttestapp.R

@Composable
fun FavoriteIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isFavorite: Boolean
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(
                if (isFavorite)
                    R.string.content_description_remove_favorite
                else
                    R.string.content_description_add_favorite
            ),
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}