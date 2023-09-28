package ai.ntr.domain.repository

import ai.ntr.domain.entity.City

interface CityRepository {
    fun getCities() : List<City>
    fun shuffleCities() : List<City>
}