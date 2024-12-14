package com.example.movieuniverse.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieuniverse.model.ApiResponse
import com.example.movieuniverse.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel : BaseViewModel() {

    fun getTrendingMovies(): LiveData<MovieResponse> {
        val liveData = MutableLiveData<MovieResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.prepareTrendingMovies()) {
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

                else -> {}
            }
        }
        return liveData
    }

    fun fetchNowPlayingMovie(): LiveData<MovieResponse> {
        val liveData = MutableLiveData<MovieResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getNowPlayingMovie()) {
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

                else -> {}
            }
        }
        return liveData
    }

    fun getUpcomingMovies(): LiveData<MovieResponse> {
        val liveData = MutableLiveData<MovieResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getUpcomingMovie()) {
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

                else -> {}
            }
        }
        return liveData
    }

    fun getTopRatedMovies(): LiveData<MovieResponse> {
        val liveData = MutableLiveData<MovieResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getTopRatedMovies()) {
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

                else -> {}
            }
        }
        return liveData
    }

    fun getPopularMovies(): LiveData<MovieResponse> {
        val liveData = MutableLiveData<MovieResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getPopularMovies()) {
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

                else -> {}
            }
        }
        return liveData
    }
}