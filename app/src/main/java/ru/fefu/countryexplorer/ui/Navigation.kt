package ru.fefu.countryexplorer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(viewModel: CountryViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            CountryListScreen(navController, viewModel)
        }
        composable("detail/{countryName}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            CountryDetailScreen(countryName, viewModel)
        }
        composable("favourites") {
            FavouritesScreen(navController, viewModel)
        }
    }
}