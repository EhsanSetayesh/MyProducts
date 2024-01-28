package co.ehsansetayesh.data_remote.networking.product

import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProducts(): List<ProductApiModel>
}