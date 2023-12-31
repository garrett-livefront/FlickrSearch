package com.livefront.flickrsearch.repository

import com.livefront.flickrsearch.data.network.FlickrSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Repository class used as an interface to the api call and checks for errors and wraps the result
 * in a result wrapper class
 */
class FlickrRepository @Inject constructor(
    private val flickrService: FlickrService
) {
    suspend fun searchPhotos(
        searchText: String
    ): FlickrSearchResult {
        return withContext(Dispatchers.IO) {
            try {
                FlickrSearchResult.Success(flickrService.searchPublicPhotos(tags = searchText))
            } catch (e: Exception) {
                FlickrSearchResult.Error(message = e.message ?: "Error searching for photos")
            }
        }
    }
}