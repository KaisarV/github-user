package com.kai.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kai.githubuser.database.FavoriteUser
import com.kai.githubuser.databinding.UserListBinding
import com.kai.githubuser.helper.FavoriteUserDiffCallback

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
                    .load(favoriteUser.avatarUrl) // URL Gambar
                    .into(imgItemPhoto)
//                cvItemNote.setOnClickListener {
//                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
//                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
//                    it.context.startActivity(intent)
//                }
            }
        }
    }
}