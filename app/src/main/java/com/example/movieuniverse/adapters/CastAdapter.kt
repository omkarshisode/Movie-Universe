package com.example.movieuniverse.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieuniverse.databinding.ItemCastBinding
import com.example.movieuniverse.model.Cast
import com.example.movieuniverse.viewHolders.CastMember

class CastAdapter : RecyclerView.Adapter<CastMember>() {
    private var castMemberList = ArrayList<Cast>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCastList(castList: ArrayList<Cast>) {
        this.castMemberList.clear()
        this.castMemberList.addAll(castList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMember {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastMember(binding)
    }

    override fun getItemCount(): Int {
        return castMemberList.size
    }

    override fun onBindViewHolder(holder: CastMember, position: Int) {
        val cast = castMemberList[position]
        holder.bind(cast)
    }
}