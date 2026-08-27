package fr.leboncoin.androidrecruitmenttestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.components.image.Illustration
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.domain.repository.AnalyticsRepository
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsRepository: AnalyticsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticsRepository.trackScreenView(getString(R.string.screen_details))

        setContent {
            SparkTheme {
                Illustration(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.work_in_progress),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                )
            }
        }
    }
}

