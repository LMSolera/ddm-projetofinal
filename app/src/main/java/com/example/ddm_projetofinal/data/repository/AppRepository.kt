package com.example.ddm_projetofinal.data.repository

interface AppRepository {
    // User methods
    suspend fun login()
    suspend fun insertUser()
    suspend fun updateUser()

    // Trip Methods
    suspend fun insertTrip()
    suspend fun deleteTrip()
    suspend fun updateTrip()
    suspend fun getTripsByUser()

    // Expense methods
    suspend fun insertExpense()
    suspend fun deleteExpense()
    suspend fun updateExpense()
    suspend fun getExpensesByUser()
    suspend fun getExpensesByTrip()
}