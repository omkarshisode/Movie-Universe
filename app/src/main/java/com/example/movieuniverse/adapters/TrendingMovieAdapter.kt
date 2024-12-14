package com.example.movieuniverse.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieuniverse.databinding.ItemTrendingMovieBinding
import com.example.movieuniverse.model.Result
import com.example.movieuniverse.viewHolders.MovieViewHolder

class TrendingMovieAdapter(private val context: Context): RecyclerView.Adapter<MovieViewHolder>() {

    private var trendingMovieList = ArrayList<Result>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTrendingMovies(movieList: ArrayList<Result>) {
        this.trendingMovieList.clear()
        this.trendingMovieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemTrendingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        // Show only top five movies
        return trendingMovieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = trendingMovieList[position]
        holder.bind(context, movie)
    }
}