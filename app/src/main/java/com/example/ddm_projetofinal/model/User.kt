package com.example.ddm_projetofinal.model

data class User (
    // Identificador
    val id: String,
    // Conteúdo
    val name: String,
    val email: String,
    val password: String
)

// FOs
val usuario1 = User (
    id = "5jdosl89",
    name = "Luis Miguel",
    email = "lumi@gmail.com",
    password = "senhaDoLuis"
)

val usuario2 = User (
    id = "1ldpsmb8",
    name = "Henrique Chaves",
    email = "chaves@gmail.com",
    password = "senhaDoHenrique"
)

val usuario3 = User (
    id = "aowjg6cb",
    name = "Pedro Oliveira",
    email = "poli@gmail.com",
    password = "senhaDoPedro"
)

