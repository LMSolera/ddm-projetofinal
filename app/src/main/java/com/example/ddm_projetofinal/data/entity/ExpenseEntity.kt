package com.example.ddm_projetofinal.data.entity

import com.google.gson.annotations.SerializedName

data class ExpenseEntity (
    // Identificadores
    @SerializedName("id")
    val id: String,

    @SerializedName("trip_id")
    val tripId: String,

    @SerializedName("owner_id")
    val ownerId: String,

    // Conteúdo
    @SerializedName("value")
    val value: Double,

    @SerializedName("observation")
    val observation: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("type")
    val type: String
)