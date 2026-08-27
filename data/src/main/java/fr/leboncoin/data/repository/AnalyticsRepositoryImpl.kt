package fr.leboncoin.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import fr.leboncoin.data.di.AnalyticsPreferences
import fr.leboncoin.domain.repository.AnalyticsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsRepositoryImpl @Inject constructor(
    @AnalyticsPreferences private val prefs: SharedPreferences
) : AnalyticsRepository {

    override fun trackSelection(itemId: String) {
        prefs.edit { putString(SELECTED_ITEM_KEY, itemId) }
        // Simulate some analytics logging
        println("Analytics: User selected item - $itemId")
    }

    override fun getSelection(): String? {
        return prefs.getString(SELECTED_ITEM_KEY, null)
    }

    override fun trackScreenView(screenName: String) {
        println("Analytics: Screen viewed - $screenName")
    }

    companion object {
        private const val SELECTED_ITEM_KEY = "selected_item"
    }
}
