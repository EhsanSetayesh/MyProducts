package co.ehsansetayesh.presentation_products.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel
) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            label = { Text("Search") },
            placeholder = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)

        )
        Spacer(modifier = Modifier.height(16.dp))
        viewModel.loadProducts()
        viewModel.productsState.collectAsState().value.let { state ->
            when (state) {
                is UiState.Loading -> {
                    Loading()
                }

                is UiState.Error -> {
                    Error(state.errorMessage)
                }

                is UiState.Success -> {
                    viewModel.setProducts(state.data)
                }
            }
        }

        viewModel.products.collectAsState().value.let {
            if (isSearching) {
                Loading()
            } else {
                if (it.isNotEmpty())
                    DisplayProductList(it)
            }
        }
    }
}


@Composable
fun DisplayProductList(
    productList: List<ProductUiModel>
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(productList) { item ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = item.title)
                Text(text = item.category)
            }
        }
    }
}

@Composable
fun Error(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}