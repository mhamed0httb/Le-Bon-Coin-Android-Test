package fr.leboncoin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_favorites")
data class FavoriteAlbumEntity(@PrimaryKey val albumId: Int)