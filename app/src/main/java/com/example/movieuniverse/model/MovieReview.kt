package com.example.movieuniverse.model

data class MovieReview(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

data class AuthorDetails(
    val avatar_path: Any,
    val name: String,
    val rating: Int,
    val username: String
)