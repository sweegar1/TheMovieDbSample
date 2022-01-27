package com.example.themoviedb.data.api

import com.example.themoviedb.utils.AppConstants


class ApiHelper(private val apiService: ApiService) {
    suspend fun getMoviesList()=apiService.getMoviesList(AppConstants.ACCESS_KEY,
        AppConstants.LANGUAGE,
        AppConstants.PAGE,
    AppConstants.REGION)

}