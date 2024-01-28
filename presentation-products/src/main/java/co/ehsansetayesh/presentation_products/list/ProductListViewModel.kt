package co.ehsansetayesh.presentation_products.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.ehsansetayesh.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val useCase: GetProductsUseCase,
    private val converter: ProductListConverter
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
                delay(2000L)
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
        viewModelScope.launch {
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