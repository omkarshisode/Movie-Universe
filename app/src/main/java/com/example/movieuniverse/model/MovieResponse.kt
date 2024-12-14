package com.example.movieuniverse.model

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)
