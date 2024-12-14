package com.example.movieuniverse.viewHolders

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.movieuniverse.activites.MovieDetailsActivity
import com.example.movieuniverse.databinding.ItemMovieBinding
import com.example.movieuniverse.model.Result
import com.example.movieuniverse.utiles.Constants
import com.example.movieuniverse.utiles.loadImage

class CategoryMovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, movie: Result) {
        val imageUrl = Constants.IMAGE_BASE_URL + movie.poster_path
        val dummyImageUrl = "https://www.w3schools.com/w3images/lights.jpg"
        binding.ivMovie.loadImage(imageUrl)
        binding.ivMovie.setOnClickListener {
            val movieId = movie.id.toString()
            showMovieDetails(context, movieId)
        }
    }

    private fun showMovieDetails(context: Context, movieId: String) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID, movieId)
        context.startActivity(intent)
    }
}