package com.example.movieuniverse.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.movieuniverse.databinding.ItemCastBinding
import com.example.movieuniverse.model.Cast
import com.example.movieuniverse.utiles.Constants
import com.example.movieuniverse.utiles.loadImage

class CastMember(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cast: Cast) {
        val imageUrl = Constants.IMAGE_BASE_URL + cast.profile_path
        binding.ivCastMember.loadImage(imageUrl)
    }
}