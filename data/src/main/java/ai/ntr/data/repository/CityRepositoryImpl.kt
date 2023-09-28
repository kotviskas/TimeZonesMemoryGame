package ai.ntr.data.repository

import ai.ntr.domain.entity.City
import ai.ntr.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor() : CityRepository {
    private val cities: List<City> = listOf(
        City("9", 9),
        City("10", 10),
        City("11", 11),
        City("12", 12),
        City("5", 5),
        City("6", 6),
        City("7", 7),
        City("8", 8)
    )
    val countOfCities = 6

    override fun getCities(): List<City> {
        return cities.asSequence().shuffled().take(countOfCities).toList()
    }

    override fun shuffleCities(): List<City> {
        return cities.asSequence().shuffled().toList()
    }
}