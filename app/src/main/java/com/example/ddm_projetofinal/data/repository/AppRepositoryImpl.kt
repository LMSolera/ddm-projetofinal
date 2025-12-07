package com.example.ddm_projetofinal.data.repository

import com.example.ddm_projetofinal.data.api.RetrofitClient
import com.example.ddm_projetofinal.data.entity.ExpenseEntity
import com.example.ddm_projetofinal.data.entity.TripEntity
import com.example.ddm_projetofinal.data.entity.UserEntity
import com.example.ddm_projetofinal.model.Expense
import com.example.ddm_projetofinal.model.Trip
import com.example.ddm_projetofinal.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepositoryImpl: AppRepository {
    private val api = RetrofitClient.supabaseService

    override suspend fun login(email: String, password: String): Result<User> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(
                    "eq." + email,
                    "eq." + password
                )

                if (response.isSuccessful) {
                    val userResponse = response.body()

                    if (!userResponse.isNullOrEmpty()) {
                        val user = User(
                            id = userResponse[0].id,
                            name = userResponse[0].name,
                            email = userResponse[0].email,
                            password = userResponse[0].password
                        )

                        Result.success(user)
                    } else {
                        Result.failure(Exception("Error while trying to log in. (Undefined login credentials)"))
                    }
                } else {
                    Result.failure(Exception("Server error: " + response.code()))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Connection exception: " + e.message))
            }
        }

    override suspend fun insertUser(userInfo: User): Result<User> = withContext(Dispatchers.IO) {
        try {
            val userEntity = UserEntity(
                id = userInfo.id,
                name = userInfo.name,
                email = userInfo.email,
                password = userInfo.password
            )

            val response = api.newUser(userEntity)

            if (response.isSuccessful) {
                val userResponse = response.body()
                if (!userResponse.isNullOrEmpty()) {

                    val user = User(
                        id = userResponse[0].id,
                        name = userResponse[0].name,
                        email = userResponse[0].email,
                        password = userResponse[0].password
                    )

                    Result.success(user)
                } else {
                    Result.failure(Exception("Error while trying to create new user"))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun updateUser(user: User): Result<User> = withContext(Dispatchers.IO) {
        try {
            val body = mapOf(
                "name" to user.name,
                "email" to user.email,
                "password" to user.password)

            val response = api.updateUser(
                id = "eq." + user.id,
                user = body
            )

            if (response.isSuccessful) {
                val users = response.body()
                if (!users.isNullOrEmpty()) {
                    val user = User(
                        id = users[0].id,
                        name = users[0].name,
                        email = users[0].email,
                        password = users[0].password
                    )
                    Result.success(user)
                } else {
                    Result.failure(Exception("Error while trying to update user information."))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun checkUserByEmail(userEmail: String): Result<User> = withContext(Dispatchers.IO) {
            try {
                val response = api.getUserByEmail(email = "eq." + userEmail)

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (!userResponse.isNullOrEmpty()) {

                        val user = User(
                            id = userResponse[0].id,
                            name = "", // Não retorna aspectos "confidenciais" uma conta
                            email = userResponse[0].email,
                            password = "", // Não retorna aspectos "confidenciais" uma conta
                        )

                        Result.success(user)
                    } else {
                        val user = User(
                            id = "",
                            name = "", // Não retorna aspectos "confidenciais" uma conta
                            email = "",
                            password = "", // Não retorna aspectos "confidenciais" uma conta
                        )
                        Result.success(user)
                    }
                } else {
                    Result.failure(Exception("Server error: " + response.code()))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Connection exception: " + e.message))
            }
        }

    override suspend fun getTripsByUser(ownerId: String): Result<List<Trip>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getTrips(owner_id = "eq." + ownerId)

            if (response.isSuccessful) {
                val tripsEntities = response.body() ?: emptyList()

                val trips = tripsEntities.map { tripEntity ->
                    Trip(
                        id = tripEntity.id,
                        ownerId = tripEntity.ownerId,
                        title = tripEntity.title,
                        date = tripEntity.date
                    )
                }

                Result.success(trips)
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun getTripById(id: String): Result<Trip> = withContext(Dispatchers.IO) {
        try {
            val response = api.getTripById(id = "eq." + id)

            if (response.isSuccessful) {
                val tripResponse = response.body()
                if (!tripResponse.isNullOrEmpty()) {
                    val trip = Trip (
                        id = tripResponse[0].id,
                        ownerId = tripResponse[0].ownerId,
                        title = tripResponse[0].title,
                        date = tripResponse[0].date
                    )
                    Result.success(trip)
                } else {
                    Result.failure(Exception("Error while trying to retrieve trip by id"))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun insertTrip(tripInfo: Trip): Result<Trip> = withContext(Dispatchers.IO) {
        try {
            val tripEntity = TripEntity(
                id = tripInfo.id,
                ownerId = tripInfo.ownerId,
                title = tripInfo.title,
                date = tripInfo.date
            )

            val response = api.insertTrip(tripEntity)

            if (response.isSuccessful) {
                val tripResponse = response.body()
                if (!tripResponse.isNullOrEmpty()) {

                    val trip = Trip(
                        id = tripResponse[0].id,
                        ownerId = tripResponse[0].ownerId,
                        title = tripResponse[0].title,
                        date = tripResponse[0].date
                    )

                    Result.success(trip)
                } else {
                    Result.failure(Exception("Error while trying to create new trip"))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun updateTrip(trip: Trip): Result<Trip> = withContext(Dispatchers.IO) {
        try {
            val body = mapOf("title" to trip.title, "date" to trip.date)

            val response = api.updateTrip(
                id = "eq." + trip.id,
                trip = body
            )

            if (response.isSuccessful) {
                val trips = response.body()
                if (!trips.isNullOrEmpty()) {
                    val trip = Trip(
                        id = trips[0].id,
                        ownerId = trips[0].ownerId,
                        title = trips[0].title,
                        date = trips[0].date
                    )
                    Result.success(trip)
                } else {
                    Result.failure(Exception("Error while trying to update trip."))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun deleteTrip(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.deleteTrip(id = "eq."+id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun getExpensesByUser(ownerId: String): Result<List<Expense>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getExpenses(owner_id = "eq." + ownerId)

            if (response.isSuccessful) {
                val expensesEntities = response.body() ?: emptyList()

                val expenses = expensesEntities.map { expenseEntity ->
                    Expense (
                        id = expenseEntity.id,
                        ownerId = expenseEntity.ownerId,
                        tripId = expenseEntity.tripId,
                        value = expenseEntity.value,
                        observation = expenseEntity.observation,
                        date = expenseEntity.date,
                        type = expenseEntity. type
                    )
                }

                Result.success(expenses)
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun insertExpense(expenseInfo: Expense): Result<Expense> = withContext(Dispatchers.IO) {
        try {
            val expenseEntity = ExpenseEntity(
                id = expenseInfo.id,
                tripId = expenseInfo.tripId,
                ownerId = expenseInfo.ownerId,
                value = expenseInfo.value,
                observation = expenseInfo.observation,
                date = expenseInfo.date,
                type = expenseInfo.type
            )

            val response = api.insertExpense(expenseEntity)

            if (response.isSuccessful) {
                val expenseResponse = response.body()
                if (!expenseResponse.isNullOrEmpty()) {

                    val expense = Expense(
                        id = expenseResponse[0].id,
                        ownerId = expenseResponse[0].ownerId,
                        tripId = expenseResponse[0].tripId,
                        value = expenseResponse[0].value,
                        observation = expenseResponse[0].observation,
                        date = expenseResponse[0].date,
                        type = expenseResponse[0].type
                    )

                    Result.success(expense)
                } else {
                    Result.failure(Exception("Error while trying to create new expense"))
                }
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }

    override suspend fun deleteExpense(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.deleteExpense(id = "eq."+id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Server error: " + response.code()))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Connection exception: " + e.message))
        }
    }
}