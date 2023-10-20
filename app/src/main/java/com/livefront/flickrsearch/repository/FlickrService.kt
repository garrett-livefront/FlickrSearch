package com.livefront.flickrsearch.repository

import com.livefront.flickrsearch.data.network.FlickrPhotos
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("services/feeds/photos_public.gne")
    suspend fun searchPublicPhotos(
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
    ): FlickrPhotos
}