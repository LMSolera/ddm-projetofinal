package com.example.ddm_projetofinal.data.local

import com.example.ddm_projetofinal.model.User

interface UserLocalRepository {
    suspend fun insert(id: String, email: String, name: String, password: String)

    suspend fun delete(id: String)

    suspend fun getSavedLogin(): User?
}