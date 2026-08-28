package fr.leboncoin.androidrecruitmenttestapp.ui.navigation

sealed class Route(val route: String) {
    data object AlbumList : Route("albumList")
    data object AlbumDetails : Route("albumDetails/{albumId}") {
        fun createRoute(albumId: Int) = "albumDetails/$albumId"
        const val ARG_ALBUM_ID = "albumId"
    }
}
