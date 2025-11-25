package com.example.ddm_projetofinal.model

data class Trip (
    // Identificadores
    val id: String,
    val ownerId: String,
    // Conteúdo
    val title: String,
    val date: String
)

// FOs
val trip1 = Trip (
    id = "algn75hd",
    ownerId = "5jdosl89",
    title = "Viagem pra praia",
    date = "08/11/2025"
)

val trip2 = Trip (
    id = "1nvh73hdi",
    ownerId = "1ldpsmb8",
    title = "Visita de parentes no Paraná",
    date = "10/12/2025"
)

val trip3 = Trip (
    id = "mqny7vcx",
    ownerId = "aowjg6cb",
    title = "Viagem pra curitiba",
    date = "01/01/2026"
)
