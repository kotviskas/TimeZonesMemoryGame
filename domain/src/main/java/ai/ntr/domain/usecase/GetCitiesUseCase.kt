package ai.ntr.domain.usecase

import ai.ntr.domain.base.BaseUseCaseNoParams
import ai.ntr.domain.entity.City
import ai.ntr.domain.repository.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val cityRepository: CityRepository) :
    BaseUseCaseNoParams<List<City>>() {
    override fun doUseCase(): List<City> {
        return cityRepository.getCities()
    }
}