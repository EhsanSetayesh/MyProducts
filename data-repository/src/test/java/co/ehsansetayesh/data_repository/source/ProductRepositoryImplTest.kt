package co.ehsansetayesh.data_repository.source

import co.ehsansetayesh.data_repository.data_source.local.LocalProductDataSource
import co.ehsansetayesh.data_repository.data_source.remote.RemoteProductDataSource
import co.ehsansetayesh.data_repository.repository.ProductRepositoryImpl
import co.ehsansetayesh.domain.entity.Product
import co.ehsansetayesh.domain.entity.Rating
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ProductRepositoryImplTest {

    private val remoteProductDataSource = mock<RemoteProductDataSource>()
    private val localProductDataSource = mock<LocalProductDataSource>()
    private val repositoryImpl =
        ProductRepositoryImpl(remoteProductDataSource, localProductDataSource)


    @ExperimentalCoroutinesApi
    @Test
    fun testGetProducts() = runBlockingTest {
        val products = listOf(
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
        whenever(remoteProductDataSource.getProducts()).thenReturn(flowOf(products))
        val result = repositoryImpl.getProducts().first()
        Assert.assertEquals(products, result)
        verify(localProductDataSource).addProducts(products)
    }
}