package com.example.ddm_projetofinal.model

data class Expense (
    // Identificadores
    val id: String,
    val tripId: String,
    val ownerId: String,
    // Conteúdo
    val value: Double,
    val observation: String,
    val date: String,
    val type: String
)

// FOs
val expense1 = Expense (
    id = "qkg7nbpl",
    tripId = "algn75hd",
    ownerId = "5jdosl89",
    value = 12.50,
    observation = "Compra de água de coco",
    date = "10/11/2025",
    type = "Comida"
)

val expense2 = Expense (
    id = "anvh1jem",
    tripId = "1nvh73hdi",
    ownerId = "1ldpsmb8",
    value = 120.00,
    observation = "Compra de um presente para minha tia",
    date = "11/12/2025",
    type = "Outros"
)

val expense3 = Expense (
    id = "zolepgm3",
    tripId = "mqny7vcx",
    ownerId = "aowjg6cb",
    value = 1780.88,
    observation = "Pagamento prévio da estadia do Airbnb para uma semana",
    date = "25/12/2025",
    type = "Estadia"
)