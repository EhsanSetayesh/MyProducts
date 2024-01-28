package co.ehsansetayesh.presentation_products.list

import android.content.Context
import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.entity.Rating
import co.ehsansetayesh.domain.entity.Result
import co.ehsansetayesh.domain.entity.UseCaseException
import co.ehsansetayesh.domain.usecase.GetProductsUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProductListConverterTest {

    private val context = mock<Context>()
    private val converter = ProductListConverter(context)

    @Test
    fun testConvertError() {
        val errorMessage = "errorMessage"
        val exception = mock<UseCaseException>()
        whenever(exception.localizedMessage).thenReturn(errorMessage)
        val errorResult = Result.Error(exception)
        val result = converter.convert(errorResult)
        assertEquals(UiState.Error(errorMessage), result)
    }

    @Test
    fun testConvertSuccess() {
        val response = GetProductsUseCase.Response(
            products = listOf(
                Product(
                    id = 1,
                    title = "Jeans",
                    category = "Mens",
                    price = 100.5,
                    description = "desc",
                    image = "",
                    rating = Rating(3.0, 10)
                )
            )
        )

        val successResult = Result.Success(response)
        val result = converter.convert(successResult)
        assertEquals(
            UiState.Success(
                data = listOf(
                    ProductUiModel(
                        id = 1,
                        title = "Jeans",
                        category = "Mens",
                        image = "",
                    )
                )
            ), result
        )
    }
}