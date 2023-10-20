package com.livefront.flickrsearch.data.network

sealed class FlickrSearchResult {
    data class Success(val value: FlickrPhotos): FlickrSearchResult()
    data class Error(val message: String): FlickrSearchResult()
}