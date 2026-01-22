package ru.fefu.countryexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    countryName: String,
    viewModel: CountryViewModel
) {
    val country = remember(countryName) {
        viewModel.findCountryByName(countryName)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(countryName) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (country == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Страна не найдена",
                        style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Попробуйте вернуться в список")
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        country.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Информация о стране:",
                        style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    CountryInfoRow(label = "Столица:", value = country.capital ?: "Нет данных")
                    CountryInfoRow(label = "Регион:", value = country.region)
                    CountryInfoRow(label = "Население:", value = formatPopulation(country.population))

                    if (country.area != null) {
                        CountryInfoRow(label = "Площадь:", value = "${country.area} км²")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.toggleFavourite(country.name) },
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text(
                            if (viewModel.isFavourite(country.name)) "Удалить из ★"
                            else "Добавить в ☆"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountryInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

fun formatPopulation(population: Long): String {
    return when {
        population >= 1_000_000 -> "${population / 1_000_000} млн"
        population >= 1_000 -> "${population / 1_000} тыс"
        else -> population.toString()
    }
}