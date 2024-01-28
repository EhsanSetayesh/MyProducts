package co.ehsansetayesh.data_local.source

import co.ehsansetayesh.data_local.db.product.ProductDao
import co.ehsansetayesh.data_local.db.product.ProductEntity
import co.ehsansetayesh.domain.entity.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalProductDataSourceImplTest {

    private val productDao = mock<ProductDao>()
    private val productDataSource = LocalProductDataSourceImpl(productDao)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetProducts() = runBlockingTest {
        val localProducts = listOf(
            ProductEntity(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = ""
            )
        )
        val expectedProducts = listOf(
            Product(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = ""
            )
        )
        whenever(productDao.getProducts()).thenReturn(flowOf(localProducts))
        val result = productDataSource.getProducts().first()
        Assert.assertEquals(expectedProducts, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddProducts() = runBlockingTest {
        val localProducts = listOf(
            ProductEntity(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = ""
            )
        )
        val products = listOf(
            Product(
                id = 1,
                title = "Jeans",
                category = "Mens",
                price = 100.5,
                description = "desc",
                image = ""
            )
        )
        productDataSource.addProducts(products)
        verify(productDao).insertProducts(localProducts)
    }
}