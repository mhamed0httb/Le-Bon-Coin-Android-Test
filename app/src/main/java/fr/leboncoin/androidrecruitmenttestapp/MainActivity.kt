package fr.leboncoin.androidrecruitmenttestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adevinta.spark.SparkTheme
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.androidrecruitmenttestapp.ui.navigation.Route
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.AlbumDetailScreen
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.details.AlbumDetailsViewModel
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.AlbumsScreen
import fr.leboncoin.androidrecruitmenttestapp.ui.screen.album.list.AlbumsViewModel
import fr.leboncoin.domain.repository.AnalyticsRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsRepository: AnalyticsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SparkTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.AlbumList.route,
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(400)
                        )
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(400)
                        )
                    }
                ) {
                    composable(Route.AlbumList.route) {
                        val viewModel: AlbumsViewModel = hiltViewModel()
                        AlbumsScreen(
                            viewModel = viewModel,
                            onItemSelected = {
                                analyticsRepository.trackSelection(it.id.toString())
                                navController.navigate(Route.AlbumDetails.createRoute(it.id))
                            }
                        )
                    }
                    composable(
                        route = Route.AlbumDetails.route,
                        arguments = listOf(
                            navArgument(Route.AlbumDetails.ARG_ALBUM_ID) {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val albumId =
                            backStackEntry.arguments?.getInt(Route.AlbumDetails.ARG_ALBUM_ID) ?: -1
                        val viewModel: AlbumDetailsViewModel = hiltViewModel()

                        val screenDetailsTitle = stringResource(R.string.screen_details)
                        LaunchedEffect(Unit) {
                            analyticsRepository.trackScreenView(screenDetailsTitle)
                        }

                        AlbumDetailScreen(
                            albumId = albumId,
                            onBackClick = { navController.popBackStack() },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
