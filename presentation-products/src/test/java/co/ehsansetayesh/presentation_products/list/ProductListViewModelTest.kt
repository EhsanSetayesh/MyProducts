package co.ehsansetayesh.presentation_products.list

import co.ehsansetayesh.domain.entity.Result
import co.ehsansetayesh.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val useCase = mock<GetProductsUseCase>()
    private val converter = mock<ProductListConverter>()
    //private val viewModel = ProductListViewModel(useCase, converter,testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testLoadProducts() = testDispatcher.runBlockingTest {
        val viewModel = ProductListViewModel(useCase, converter, testDispatcher)

        assertEquals(UiState.Loading, viewModel.productsState.value)
        val uiState = mock<UiState<List<ProductUiModel>>>()
        val result = mock<Result<GetProductsUseCase.Response>>()
        whenever(useCase.execute(GetProductsUseCase.Request)).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.loadProducts()
        assertEquals(uiState, viewModel.productsState.value)
    }
}