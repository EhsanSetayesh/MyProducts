package co.ehsansetayesh.presentation_products.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ehsansetayesh.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val useCase: GetProductsUseCase,
    private val converter: ProductListConverter,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _productsState =
        MutableStateFlow<UiState<List<ProductUiModel>>>(UiState.Loading)
    val productsState: StateFlow<UiState<List<ProductUiModel>>> = _productsState

    private val _products =
        MutableStateFlow<List<ProductUiModel>>(mutableListOf())

    val products = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_products) { query, products ->
            if (query.isBlank()) {
                products
            } else {
                products.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.category.contains(query, ignoreCase = true)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _products.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun loadProducts() {
        viewModelScope.launch (dispatcher){
            useCase.execute(GetProductsUseCase.Request)
                .map {
                    converter.convert(it)
                }
                .collect {
                    _productsState.value = it
                }
        }
    }

    fun setProducts(productsList: List<ProductUiModel>) {
        _products.value = productsList
    }
}