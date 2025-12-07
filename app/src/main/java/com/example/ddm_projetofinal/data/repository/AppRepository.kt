package com.example.ddm_projetofinal.data.repository

import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User

interface AppRepository {
    // User methods
    suspend fun login(email: String, password: String): Result<User>
    suspend fun insertUser(user: User): Result<User>
    suspend fun checkUserByEmail(email: String): Result<User>
    suspend fun updateUser(user: User): Result<User>

    // Trip Methods
    suspend fun insertTrip(trip: Trip): Result<Trip>
    suspend fun deleteTrip(id: String): Result<Unit>
    suspend fun updateTrip(trip: Trip): Result<Trip>
    suspend fun getTripById(id: String): Result<Trip>
    suspend fun getTripsByUser(ownerId: String): Result<List<Trip>>

    // Expense methods
    suspend fun insertExpense(expense: Expense): Result<Expense>
    suspend fun deleteExpense(id: String): Result<Unit>
    suspend fun getExpensesByUser(ownerId: String): Result<List<Expense>>
}