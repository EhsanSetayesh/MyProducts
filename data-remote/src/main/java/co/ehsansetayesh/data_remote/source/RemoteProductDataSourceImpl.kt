package co.ehsansetayesh.data_remote.source

import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.data_remote.networking.product.ProductApiModel
import co.ehsansetayesh.data_remote.networking.product.ProductService
import co.ehsansetayesh.data_repository.data_source.remote.RemoteProductDataSource
import co.ehsansetayesh.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteProductDataSourceImpl @Inject constructor(private val productService: ProductService) :
    RemoteProductDataSource {

    override fun getProducts(): Flow<List<Product>> = flow {
        emit(productService.getProducts())
    }.map { products ->
        products.map { productApiModel ->
            convert(productApiModel)
        }
    }.catch {
        throw UseCaseException.ProductException(it)
    }

    private fun convert(postApiModel: ProductApiModel) =
        Product(id = postApiModel.id,
            title = postApiModel.title,
            description = postApiModel.description,
            category = postApiModel.category,
            image = postApiModel.image,
            price = postApiModel.price,
            rating = postApiModel.rating)
}