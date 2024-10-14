package com.example.lab09_ejercicio1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun ScreenProductDetail(navController: NavHostController, servicio: ProductApiService, id: Int) {
    var product by remember { mutableStateOf<ProductModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            product = servicio.getProductById(id)
        } catch (e: Exception) {
            error = "Error al cargar el producto: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(error!!, color = Color.Red)
        } else if (product != null) {
            Text(product!!.title, style = MaterialTheme.typography.headlineMedium)
            AsyncImage(
                model = product!!.thumbnail,
                contentDescription = product!!.title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text("Descripción: ${product!!.description}")
            Text("Precio: $${product!!.price}")
            Text("Descuento: ${product!!.discountPercentage}%")
            Text("Valoración: ${product!!.rating}")
            Text("Stock: ${product!!.stock}")
            Text("Marca: ${product!!.brand}")
            Text("Categoría: ${product!!.category}")
        }
    }
}