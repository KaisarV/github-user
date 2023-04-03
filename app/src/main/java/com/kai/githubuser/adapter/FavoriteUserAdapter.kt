package com.kai.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.githubuser.database.FavoriteUser
import com.kai.githubuser.databinding.UserListBinding
import com.kai.githubuser.helper.FavoriteUserDiffCallback
import com.kai.githubuser.ui.DetailUserActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private val listFavoriteUser = ArrayList<FavoriteUser>()

    fun setListFavoriteUser(listFavoriteUser: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(listFavoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUser[position])
    }
    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }

    inner class FavoriteUserViewHolder(private val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {

            with(binding) {
                name.text = favoriteUser.username
                urlItem.text = favoriteUser.url

                val context = itemView.context
                Glide.with(context)
                    .load(favoriteUser.avatarUrl)
                    .into(imgItemPhoto)

                cvItemUser.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.LOGIN, name.text)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}