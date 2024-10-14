package com.example.lab09_ejercicio1
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun ScreenProducts(navController: NavHostController, servicio: ProductApiService) {
    var productList by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var currentSkip by remember { mutableStateOf(0) }
    val limit = 20

    LaunchedEffect(currentSkip) {
        isLoading = true
        error = null
        try {
            val response = servicio.getProducts(limit = limit, skip = currentSkip)
            productList = productList + response.products
        } catch (e: Exception) {
            error = "Error al cargar productos: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column {
        if (error != null) {
            Text(error!!, color = Color.Red)
        }

        LazyColumn {
            items(productList) { product ->
                ProductItem(product, navController)
            }
            item {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = { currentSkip += limit }) {
                        Text("Cargar más")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("productDetail/${product.id}") }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(product.title, fontWeight = FontWeight.Bold)
                Text("Precio: $${product.price}")
                Text("Marca: ${product.brand}")
            }
        }
    }
}