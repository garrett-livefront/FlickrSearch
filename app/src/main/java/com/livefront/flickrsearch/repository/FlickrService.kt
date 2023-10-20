package com.livefront.flickrsearch.repository

import com.livefront.flickrsearch.data.network.FlickrPhotos
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the REST API call to get the public photos feed
 */
interface FlickrService {
    @GET("services/feeds/photos_public.gne")
    suspend fun searchPublicPhotos(
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
    ): FlickrPhotos
}