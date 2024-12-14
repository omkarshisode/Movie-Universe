package com.example.movieuniverse.repositories

import com.example.movieuniverse.model.*
import com.example.movieuniverse.network.IAPIService
import com.example.movieuniverse.network.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieRepository(private val movieService: IAPIService) : IRepository {

    private suspend fun <T> getApiResponse(call: suspend () -> T): ApiResponse<T> {
        return try {
            val response = withContext(Dispatchers.IO) {
                call()
            }
            // Return success
            ApiResponse.Success(response)
        } catch (e: SocketTimeoutException) {
            ApiResponse.Error("Request timed out. Please try again.")
        } catch (e: UnknownHostException) {
            ApiResponse.Error("No internet connection. Please check your network.")
        } catch (e: HttpException) {
            ApiResponse.Error("HTTP error occurred. Code: ${e.code()}. ${e.message()}")
        } catch (e: IOException) {
            ApiResponse.Error("Network error. Please check your connection.")
        } catch (e: Exception) {
            ApiResponse.Error("Unexpected error occurred: ${e.localizedMessage}")
        }
    }

    suspend fun prepareTrendingMovies(): ApiResponse<MovieResponse?> {
        return getApiResponse {
            (movieService as MovieService).trendingMovies("day")
        }
    }

    suspend fun getNowPlayingMovie(): ApiResponse<MovieResponse?> {
        return getApiResponse {
            (movieService as MovieService).getNowPlayingMovie()
        }
    }

    suspend fun getUpcomingMovie(): ApiResponse<MovieResponse?> {
        return getApiResponse {
            (movieService as MovieService).getUpcomingMovies()
        }
    }

    suspend fun getTopRatedMovies(): ApiResponse<MovieResponse?> {
        return getApiResponse {
            (movieService as MovieService).getTopRatedMovies()
        }
    }

    suspend fun getPopularMovies(): ApiResponse<MovieResponse?> {
        return getApiResponse {
            (movieService as MovieService).getPopularMovies()
        }
    }

    suspend fun getMovie(movieId: String): ApiResponse<Movie?> {
        return getApiResponse {
            (movieService as MovieService).getMovie(movieId)
        }
    }

    suspend fun getMovieTrailer(movieId: String): ApiResponse<MovieVideo> {
        return getApiResponse {
            (movieService as MovieService).getMovieTrailer(movieId)
        }
    }

    suspend fun getMovieReview(movieId: String): ApiResponse<MovieReview?> {
        return getApiResponse {
            (movieService as MovieService).getMovieReviews(movieId)
        }
    }

    suspend fun getMovieCredits(movieId: String): ApiResponse<MovieCredits?> {
        return getApiResponse {
            (movieService as MovieService).getCredits(movieId)
        }
    }
}
