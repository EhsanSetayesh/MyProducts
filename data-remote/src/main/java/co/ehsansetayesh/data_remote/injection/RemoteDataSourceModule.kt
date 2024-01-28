package co.ehsansetayesh.data_remote.injection

import co.ehsansetayesh.data_remote.source.RemoteProductDataSourceImpl
import co.ehsansetayesh.data_repository.data_source.remote.RemoteProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindProductDataSource(productDataSourceImpl: RemoteProductDataSourceImpl): RemoteProductDataSource
}