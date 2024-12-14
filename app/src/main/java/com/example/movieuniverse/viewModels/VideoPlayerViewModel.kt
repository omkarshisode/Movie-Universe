package com.example.movieuniverse.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieuniverse.model.ApiResponse
import com.example.movieuniverse.model.MovieVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoPlayerViewModel: BaseViewModel() {

    fun getMovieVideo(movieId: String): LiveData<MovieVideo> {
        val liveData = MutableLiveData<MovieVideo>()
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = movieRepository.getMovieTrailer(movieId)) {
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