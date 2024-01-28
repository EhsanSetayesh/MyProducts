package co.ehsansetayesh.domain.usecase

import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.entity.Rating
import co.ehsansetayesh.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetProductUseCaseTest {

    private val productRepository = mock<ProductRepository>()
    private val useCase = GetProductsUseCase(
        mock(),
        productRepository,
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlockingTest {
        val product1 = Product(
            id = 1,
            title = "Jeans1",
            category = "Mens",
            price = 100.5,
            description = "desc",
            image = "",
            rating = Rating(3.0, 10)
        )
        val product2 = Product(
            id = 2,
            title = "Jeans2",
            category = "Mens",
            price = 100.5,
            description = "desc",
            image = "",
            rating = Rating(3.0, 10)
        )
        val product3 = Product(
            id = 3,
            title = "Jeans3",
            category = "Mens",
            price = 100.5,
            description = "desc",
            image = "",
            rating = Rating(3.0, 10)
        )
        val product4 = Product(
            id = 4,
            title = "Jeans4",
            category = "Mens",
            price = 100.5,
            description = "desc",
            image = "",
            rating = Rating(3.0, 10)
        )
        whenever(productRepository.getProducts()).thenReturn(
            flowOf(
                listOf(
                    product1,
                    product2,
                    product3,
                    product4
                )
            )
        )
        val response = useCase.process(GetProductsUseCase.Request).first()
        assertEquals(
            GetProductsUseCase.Response(
                listOf(
                    product1,
                    product2,
                    product3,
                    product4
                ),
            ),
            response
        )
    }
}