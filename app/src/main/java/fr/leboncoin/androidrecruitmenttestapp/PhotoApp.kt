package fr.leboncoin.androidrecruitmenttestapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import fr.leboncoin.domain.logger.LoggerConfiguration
import javax.inject.Inject

@HiltAndroidApp
class PhotoApp : Application() {

    @Inject
    lateinit var loggerConfiguration: LoggerConfiguration

    override fun onCreate() {
        super.onCreate()
        loggerConfiguration.setup(BuildConfig.DEBUG)
    }
}
