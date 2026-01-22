package ru.fefu.countryexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    navController: NavHostController,
    viewModel: CountryViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by remember { mutableStateOf(viewModel.uiState) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchCountries(searchQuery)
        } else {
            viewModel.loadCountries()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Страны мира") },
                actions = {
                    IconButton(onClick = { navController.navigate("favourites") }) {
                        Text("⭐")
                    }
                    IconButton(onClick = { viewModel.loadCountries() }) {
                        Text("🔄")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск страны") },
                placeholder = { Text("Введите название...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Загружаем список стран...")
                        }
                    }
                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Ошибка: ${state.message}",
                                color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadCountries() }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
                is UiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Страны не найдены")
                    }
                }
                is UiState.Success -> {
                    Text(
                        "Найдено стран: ${state.countries.size}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyColumn {
                        items(state.countries) { country ->
                            CountryCard(
                                country = country,
                                isFavourite = viewModel.isFavourite(country.name),
                                onFavouriteClick = { viewModel.toggleFavourite(country.name) },
                                onClick = {
                                    navController.navigate("detail/${country.name}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryCard(
    country: ru.fefu.countryexplorer.data.Country,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(country.name)
            },
            supportingContent = {
                Column {
                    Text("Столица: ${country.capital ?: "Нет"}")
                    Text("Регион: ${country.region}")
                    Text("Население: ${country.population}")
                    if (country.area != null) {
                        Text("Площадь: ${country.area} км²")
                    }
                }
            },
            trailingContent = {
                IconButton(onClick = onFavouriteClick) {
                    Text(if (isFavourite) "★" else "☆")
                }
            }
        )
    }
}