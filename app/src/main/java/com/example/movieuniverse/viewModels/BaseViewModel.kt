package com.example.movieuniverse.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieuniverse.network.MovieService
import com.example.movieuniverse.network.RetrofitService
import com.example.movieuniverse.repositories.MovieRepository

open class BaseViewModel: ViewModel() {

    private val movieService = RetrofitService.createService(MovieService::class.java)
    protected val movieRepository = MovieRepository(movieService)
    protected val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

}