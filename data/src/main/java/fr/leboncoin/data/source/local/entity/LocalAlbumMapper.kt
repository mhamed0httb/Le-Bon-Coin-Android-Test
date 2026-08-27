package fr.leboncoin.data.source.local.entity

import fr.leboncoin.domain.model.Album

fun AlbumEntity.toDomain() = Album(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun Album.toEntity() = AlbumEntity(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)