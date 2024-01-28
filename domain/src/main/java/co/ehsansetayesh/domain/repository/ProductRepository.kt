package co.ehsansetayesh.domain.repository

import co.ehsansetayesh.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
}