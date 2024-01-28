package co.ehsansetayesh.data_repository.data_source.remote

import co.ehsansetayesh.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface RemoteProductDataSource {
    fun getProducts(): Flow<List<Product>>
}