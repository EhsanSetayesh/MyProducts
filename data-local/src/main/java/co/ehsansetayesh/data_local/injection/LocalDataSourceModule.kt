package co.ehsansetayesh.data_local.injection

import co.ehsansetayesh.data_local.source.LocalProductDataSourceImpl
import co.ehsansetayesh.data_repository.data_source.local.LocalProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindProductDataSource(lostDataSourceImpl: LocalProductDataSourceImpl): LocalProductDataSource
}