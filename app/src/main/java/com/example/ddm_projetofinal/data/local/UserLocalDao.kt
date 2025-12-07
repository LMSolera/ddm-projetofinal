package com.example.ddm_projetofinal.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ddm_projetofinal.data.entity.UserEntityLocal

@Dao
interface UserLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (entity: UserEntityLocal)

    @Delete
    suspend fun delete (entity: UserEntityLocal)

    @Query("SELECT * FROM user")
    fun getSavedLogin(): UserEntityLocal?

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: String): UserEntityLocal?
}