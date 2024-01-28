package co.ehsansetayesh.data_remote.source

import co.ehsansetayesh.data_remote.networking.product.ProductApiModel
import co.ehsansetayesh.data_remote.networking.product.ProductService
import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.entity.Rating
import co.ehsansetayesh.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteProductDataSourceImplTest {

    private val productService = mock<ProductService>()
    private val productDataSource = RemoteProductDataSourceImpl(productService)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetProducts() = runBlockingTest {
        val remoteProducts = listOf(
            ProductApiModel(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = "",
                rating = Rating(3.0  ,10)
            )
        )
        val expectedProducts = listOf(
            Product(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = "",
                rating = Rating(3.0  ,10)
            )
        )
        whenever(productService.getProducts()).thenReturn(remoteProducts)
        val result = productDataSource.getProducts().first()
        Assert.assertEquals(expectedProducts, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetProductsThrowsError() = runBlockingTest {
        whenever(productService.getProducts()).thenThrow(RuntimeException())
        productDataSource.getProducts().catch {
            Assert.assertTrue(it is UseCaseException.ProductException)
        }.collect()
    }
}