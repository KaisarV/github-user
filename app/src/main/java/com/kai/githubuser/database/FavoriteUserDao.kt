package com.kai.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Delete
    fun delete(favoriteUser: FavoriteUser)
}