package com.example.ddm_projetofinal.data.api

import com.example.ddm_projetofinal.data.entity.ExpenseEntity
import com.example.ddm_projetofinal.data.entity.TripEntity
import com.example.ddm_projetofinal.data.entity.UserEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseService {

    @GET("rest/v1/tb_user")
    suspend fun login (
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("select") select: String = "*"
    ): Response<List<UserEntity>>

    @POST("rest/v1/tb_user")
    @Headers("Prefer: return=representation")
    suspend fun newUser (
        @Body userEntity: UserEntity
    ): Response<List<UserEntity>>

    @GET("rest/v1/tb_user")
    suspend fun getUserByEmail (
        @Query("email") email: String,
        @Query("select") select: String = "*"
    ): Response<List<UserEntity>>

    @PATCH("rest/v1/tb_user")
    @Headers("Prefer: return=representation")
    suspend fun updateUser (
        @Query("id") id: String,
        @Body user: Map<String, String>
    ): Response<List<UserEntity>>

    @GET("rest/v1/tb_trip")
    suspend fun getTrips (
        @Query("owner_id") owner_id: String,
        @Query("select") select: String = "*"
    ): Response<List<TripEntity>>

    @GET("rest/v1/tb_trip")
    suspend fun getTripById (
        @Query("id") id: String,
        @Query("select") select: String = "*"
    ): Response<List<TripEntity>>

    @POST("rest/v1/tb_trip")
    @Headers("Prefer: return=representation")
    suspend fun insertTrip (
        @Body tripEntity: TripEntity
    ): Response<List<TripEntity>>

    @PATCH("rest/v1/tb_trip")
    @Headers("Prefer: return=representation")
    suspend fun updateTrip (
        @Query("id") id: String,
        @Body trip: Map<String, String>
    ): Response<List<TripEntity>>

    @DELETE("rest/v1/tb_trip")
    suspend fun deleteTrip (
        @Query("id") id: String
    ): Response<Unit>

    @GET("rest/v1/tb_expense")
    suspend fun getExpenses (
        @Query("owner_id") owner_id: String,
        @Query("select") select: String = "*"
    ): Response<List<ExpenseEntity>>

    @POST("rest/v1/tb_expense")
    @Headers("Prefer: return=representation")
    suspend fun insertExpense (
        @Body expenseEntity: ExpenseEntity
    ): Response<List<ExpenseEntity>>

    @DELETE("rest/v1/tb_expense")
    suspend fun deleteExpense (
        @Query("id") id: String
    ): Response<Unit>
}