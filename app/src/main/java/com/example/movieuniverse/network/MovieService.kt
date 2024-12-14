package com.example.movieuniverse.network

import com.example.movieuniverse.model.Movie
import com.example.movieuniverse.model.MovieCredits
import com.example.movieuniverse.model.MovieResponse
import com.example.movieuniverse.model.MovieReview
import com.example.movieuniverse.model.MovieVideo
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService: IAPIService {

    @GET("trending/movie/{time_window}")
    suspend fun trendingMovies(@Path("time_window") timeWindow: String): MovieResponse?

    @GET()
    suspend fun getTopMovies(): MovieResponse?

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(): MovieResponse?

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse?

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: String): Movie?

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse?

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse?

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId:String): MovieReview?

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(@Path("movie_id") movieId: String): MovieCredits?

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") movieId: String): MovieVideo

}