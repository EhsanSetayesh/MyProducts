package co.ehsansetayesh.presentation_products.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

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
                .padding(bottom = 8.dp)

        )
        Spacer(modifier = Modifier.height(8.dp))

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
        }
    }
}


@Composable
fun DisplayProductList(
    productList: List<ProductUiModel>
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(productList) { item ->
            ProductListItem(item)
            ProductListSeparator()
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductListItem(item: ProductUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberImagePainter(data = item.image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = item.category , fontStyle = FontStyle.Italic)
        }
    }
}


@Composable
fun ProductListSeparator() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
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