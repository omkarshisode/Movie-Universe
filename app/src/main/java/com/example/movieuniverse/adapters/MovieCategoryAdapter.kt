package com.example.movieuniverse.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieuniverse.databinding.ItemMovieBinding
import com.example.movieuniverse.model.Result
import com.example.movieuniverse.viewHolders.CategoryMovieViewHolder

class MovieCategoryAdapter(private val context: Context): RecyclerView.Adapter<CategoryMovieViewHolder>() {

    private val movieList = ArrayList<Result>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieList(movieList: ArrayList<Result>) {
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: CategoryMovieViewHolder, position: Int) {
         val movie = movieList[position]
        holder.bind(context, movie)
    }
}
