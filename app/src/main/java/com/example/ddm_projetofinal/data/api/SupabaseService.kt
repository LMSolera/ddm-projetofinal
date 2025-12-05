package com.example.ddm_projetofinal.data.api

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface SupabaseService {

    @GET("rest/v1/tb_user")
    suspend fun login (
        //TODO :: FULL IMPLEMENTATION
    )

    @POST("rest/v1/tb_user")
    suspend fun newUser (
        //TODO :: FULL IMPLEMENTATION
    )

    @PATCH("rest/v1/tb_user")
    suspend fun updateUser (
        //TODO :: FULL IMPLEMENTATION
    )

    @GET("rest/v1/tb_trip")
    suspend fun getTrips (
        //TODO :: FULL IMPLEMENTATION
    )

    @POST("rest/v1/tb_trip")
    suspend fun insertTrip (
        //TODO :: FULL IMPLEMENTATION
    )

    @PATCH("rest/v1/tb_trip")
    suspend fun updateTrip (
        //TODO :: FULL IMPLEMENTATION
    )

    @DELETE("rest/v1/tb_trips")
    suspend fun deleteTrip (
        //TODO :: FULL IMPLEMENTATION
    )

    @GET("rest/v1/tb_expense")
    suspend fun getExpenses (
        //TODO :: FULL IMPLEMENTATION
    )

    @POST("rest/v1/tb_expense")
    suspend fun insertExpense (
        //TODO :: FULL IMPLEMENTATION
    )

    @PATCH("rest/v1/tb_expense")
    suspend fun updateExpense (
        //TODO :: FULL IMPLEMENTATION
    )

    @DELETE("rest/v1/tb_expense")
    suspend fun deleteExpense (
        //TODO :: FULL IMPLEMENTATION
    )
}