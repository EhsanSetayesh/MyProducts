package co.ehsansetayesh.data_repository.repository

import co.ehsansetayesh.data_repository.data_source.local.LocalProductDataSource
import co.ehsansetayesh.data_repository.data_source.remote.RemoteProductDataSource
import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteProductDataSource: RemoteProductDataSource,
    private val localProductDataSource: LocalProductDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> = remoteProductDataSource.getProducts()
        .onEach {
            localProductDataSource.addProducts(it)
        }
}