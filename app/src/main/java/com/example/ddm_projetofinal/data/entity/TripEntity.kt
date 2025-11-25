package com.example.ddm_projetofinal.data.entity

import com.google.gson.annotations.SerializedName

data class TripEntity (
    // Identificadores
    @SerializedName("id")
    val id: String,

    @SerializedName("owner_id")
    val ownerId: String,

    // Conteúdo
    @SerializedName("title")
    val title: String,

    @SerializedName("date")
    val date: String
)