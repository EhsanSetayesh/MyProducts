package co.ehsansetayesh.domain.usecase

import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    configuration: Configuration,
    private val productRepository: ProductRepository,
) : UseCase<GetProductsUseCase.Request,
        GetProductsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        productRepository.getProducts()
            .map {
                Response(it)
            }


    object Request : UseCase.Request

    data class Response(
        val posts: List<Product>
    ) : UseCase.Response
}