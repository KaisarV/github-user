package com.kai.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT COUNT() FROM FavoriteUser WHERE username = :username")
    fun count(username: String): LiveData<Int>

    @Delete
    fun delete(favoriteUser: FavoriteUser)
}