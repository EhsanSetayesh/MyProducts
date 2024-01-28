package co.ehsansetayesh.data_repository.data_source.local

import co.ehsansetayesh.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface LocalProductDataSource {

    fun getProducts(): Flow<List<Product>>

    suspend fun addProducts(products: List<Product>)
}