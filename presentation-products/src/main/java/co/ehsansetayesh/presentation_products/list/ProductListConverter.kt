package co.ehsansetayesh.presentation_products.list

import android.content.Context
import co.ehsansetayesh.domain.entity.Result
import co.ehsansetayesh.domain.usecase.GetProductsUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ProductListConverter @Inject constructor(@ApplicationContext private val context: Context) {

    fun convert(productListResult: Result<GetProductsUseCase.Response>): UiState<List<ProductUiModel>> {
        return when (productListResult) {
            is Result.Error -> {
                UiState.Error(productListResult.exception.localizedMessage.orEmpty())
            }

            is Result.Success -> {
                UiState.Success(
                    productListResult.data.products.map {
                        ProductUiModel(
                            it.id,
                            it.title,
                            it.category,
                            it.image
                        )
                    })
            }
        }
    }
}