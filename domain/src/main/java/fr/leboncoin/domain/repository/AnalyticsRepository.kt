package fr.leboncoin.domain.repository

interface AnalyticsRepository {
    fun trackSelection(itemId: String)
    fun getSelection(): String?
    fun trackScreenView(screenName: String)
}