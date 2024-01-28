package co.ehsansetayesh.data_local.source

import co.ehsansetayesh.data_local.db.product.ProductDao
import co.ehsansetayesh.data_local.db.product.ProductEntity
import co.ehsansetayesh.data_repository.data_source.local.LocalProductDataSource
import co.ehsansetayesh.domain.entity.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalProductDataSourceImpl @Inject constructor(private val productDao: ProductDao) :
    LocalProductDataSource {

    override fun getProducts(): Flow<List<Product>> = productDao.getProducts().map { products ->
        products.map {
            Product(
                id = it.id,
                title = it.title,
                price = it.price,
                description = it.description,
                image = it.image,
                category = it.category
            )
        }
    }

    override suspend fun addProducts(products: List<Product>) =
        productDao.insertProducts(products.map {
            ProductEntity(
                id = it.id,
                title = it.title,
                category = it.category,
                price = it.price,
                image = it.image,
                description = it.description
            )
        })
}