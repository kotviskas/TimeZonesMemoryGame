package ai.ntr.timezonesmemorygame

import ai.ntr.data.repository.CityRepositoryImpl
import ai.ntr.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule  {

    @Provides
    @ViewModelScoped
    fun cityRepository(): CityRepository = CityRepositoryImpl()
}