package com.example.themoviedb.data.api

import com.example.themoviedb.data.model.ResponsePopularMovies
import com.example.themoviedb.utils.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/popular")
    suspend fun getMoviesList(
        @Query(AppConstants.PARAM_API_KEY) accessKey: String,
        @Query(AppConstants.PARAM_LANGUAGE) language: String,
        @Query(AppConstants.PARAM_PAGE) page: Int,
        @Query(AppConstants.PARAM_REGION) region: String
    ): ResponsePopularMovies



}
