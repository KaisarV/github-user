package com.kai.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.githubuser.R
import com.kai.githubuser.response.FollowerFollowingResponseItem
import com.kai.githubuser.response.ItemsItem

class FollowerFollowingAdapter (private val listUser: List<FollowerFollowingResponseItem>) : RecyclerView.Adapter<FollowerFollowingAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    //membuat ViewHolder baru yang berisi layout item user_list
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.follower_list ,viewGroup, false))

    //menetapkan data
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem.text = listUser[position].login
        viewHolder.url.text = listUser[position].url

        Glide.with(viewHolder.itemView.context)
            .load(listUser[position].avatarUrl) // URL Gambar
            .into(viewHolder.img)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = listUser.size

    //Inisialisasi setiap komponen
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.name)
        val url: TextView = view.findViewById(R.id.url_item)
        val img : ImageView = view.findViewById(R.id.img_item_photo)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowerFollowingResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
}