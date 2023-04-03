package com.kai.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.githubuser.R
import com.kai.githubuser.response.FollowResponseItem


class FollowAdapter(private val listFollow: List<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.follow_list ,viewGroup, false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem2.text = listFollow[position].login
        viewHolder.url2.text = listFollow[position].url


        Glide.with(viewHolder.itemView.context)
            .load(listFollow[position].avatarUrl)
            .into(viewHolder.img2)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listFollow[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount() = listFollow.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem2: TextView = view.findViewById(R.id.name2)
        val url2: TextView = view.findViewById(R.id.url_item2)
        val img2 :ImageView = view.findViewById(R.id.img_item_photo2)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}