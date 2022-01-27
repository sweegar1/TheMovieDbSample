package com.example.themoviedb.data.repository

import com.example.themoviedb.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getMoviesList() = apiHelper.getMoviesList()
}