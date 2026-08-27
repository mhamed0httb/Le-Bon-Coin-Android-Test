package fr.leboncoin.androidrecruitmenttestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.adevinta.spark.SparkTheme
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.androidrecruitmenttestapp.MainActivity.Companion.ALBUM_ID_KEY
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.AlbumDetailScreen
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.AlbumDetailsViewModel
import fr.leboncoin.domain.repository.AnalyticsRepository
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsRepository: AnalyticsRepository

    private val viewModel: AlbumDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        analyticsRepository.trackScreenView(getString(R.string.screen_details))

        val albumId = intent.getIntExtra(ALBUM_ID_KEY, -1)
        if (albumId == -1) {
            finish()
        }

        setContent {
            SparkTheme {
                AlbumDetailScreen(
                    albumId = albumId,
                    onBackClick = { finish() },
                    viewModel = viewModel
                )
            }
        }
    }
}

