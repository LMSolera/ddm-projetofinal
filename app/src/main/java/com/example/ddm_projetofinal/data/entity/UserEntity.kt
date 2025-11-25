package com.example.ddm_projetofinal.data.entity

import com.google.gson.annotations.SerializedName

data class UserEntity (
    // Identificador
    @SerializedName("id")
    val id: String,

    // Conteúdo
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)