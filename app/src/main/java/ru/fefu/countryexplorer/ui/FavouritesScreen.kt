package ru.fefu.countryexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navController: NavHostController,
    viewModel: CountryViewModel
) {
    val favourites by remember { mutableStateOf(viewModel.favourites) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Избранные страны") }
            )
        }
    ) { padding ->
        if (favourites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Нет избранных стран")
                    Text("Добавьте страны звёздочкой в списке")
                }
            }
        } else {
            Column(modifier = Modifier.padding(padding)) {
                Text(
                    "Избранных стран: ${favourites.size}",
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn {
                    items(favourites.toList()) { countryName ->
                        Card(
                            onClick = { navController.navigate("detail/$countryName") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            ListItem(
                                headlineContent = { Text(countryName) },
                                trailingContent = {
                                    IconButton(
                                        onClick = { viewModel.toggleFavourite(countryName) }
                                    ) {
                                        Text("★")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}