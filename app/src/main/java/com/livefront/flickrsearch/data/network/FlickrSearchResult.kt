package com.livefront.flickrsearch.data.network

/**
 * Wrapper class for the response we get from the API call to get photos from the feed
 * This will return success is successful or error is there was a throwable.
 */
sealed class FlickrSearchResult {
    data class Success(val value: FlickrPhotos): FlickrSearchResult()
    data class Error(val message: String): FlickrSearchResult()
}