package fr.leboncoin.data.network.model

import fr.leboncoin.domain.model.Album

fun AlbumDto.toDomain(): Album {
    return Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}

fun List<AlbumDto>.toDomain(): List<Album> {
    return map { it.toDomain() }
}
