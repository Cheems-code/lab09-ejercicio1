package com.example.lab09_ejercicio1

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 20, @Query("skip") skip: Int = 0): ProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductModel

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductResponse
}

data class ProductResponse(
    val products: List<ProductModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
)