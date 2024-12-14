package com.example.movieuniverse.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieuniverse.model.ApiResponse
import com.example.movieuniverse.model.Movie
import com.example.movieuniverse.model.MovieCredits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel : BaseViewModel() {

    fun getMovieDetails(movieId: String): LiveData<Movie> {
        val liveData = MutableLiveData<Movie>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getMovie(movieId)) {
                is ApiResponse.Success -> {
                    withContext(Dispatchers.Main) {
                        liveData.value = response.data
                    }
                }

                is ApiResponse.Error -> {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = response.message
                    }
                }

                else -> {

                }
            }
        }
        return liveData
    }

    fun getCastCredits(movieId: String): LiveData<MovieCredits> {
        val liveData = MutableLiveData<MovieCredits>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getMovieCredits(movieId)) {
                is ApiResponse.Success -> {
                    withContext(Dispatchers.Main) {
                        liveData.value = response.data
                    }
                }

                is ApiResponse.Error -> {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = response.message
                    }
                }

                else -> {

                }
            }
        }
        return liveData
    }

}