package com.kai.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.kai.githubuser.database.FavoriteUser

class FavoriteUserDiffCallback (private val mOldFavoriteUserList: List<FavoriteUser>, private val mNewFavoriteUserList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteUserList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavoriteUserList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteUserList[oldItemPosition].username == mNewFavoriteUserList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = mNewFavoriteUserList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username &&
                oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}