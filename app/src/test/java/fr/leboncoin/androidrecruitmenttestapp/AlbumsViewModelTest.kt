package fr.leboncoin.androidrecruitmenttestapp

import fr.leboncoin.domain.model.Album
import fr.leboncoin.domain.repository.AlbumRepository
import fr.leboncoin.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class AlbumsViewModelTest {

    @Test
    fun loadsAlbums_emitsNonEmptyList() = runTest {
        val fakeRepository = object : AlbumRepository {
            override suspend fun getAlbums(): List<Album> = listOf(
                Album(id = 1, albumId = 1, title = "t", url = "u", thumbnailUrl = "tu")
            )
        }
        val useCase = GetAlbumsUseCase(fakeRepository)
        val vm = AlbumsViewModel(useCase)

        vm.loadAlbums()
        val result = vm.albums.first()

        assertTrue("Expected albums to be loaded", result.isNotEmpty())
    }
}
