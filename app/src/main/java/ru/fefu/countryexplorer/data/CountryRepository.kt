package ru.fefu.countryexplorer.data

class CountryRepository {
    private val api = RetrofitInstance.api

    suspend fun getAllCountries(): Result<List<Country>> {
        return try {
            val countries = api.getAllCountries()
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchCountries(query: String): Result<List<Country>> {
        return try {
            val countries = api.searchCountries(query)
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}