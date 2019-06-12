package com.example.emmanueldaviest.internal.di.module

import android.app.Application
import androidx.room.Room
import com.example.emmanueldaviest.data.repository.IRepository
import com.example.emmanueldaviest.data.repository.TripRepository
import com.example.emmanueldaviest.data.repository.api.HeadlineApiInterface
import com.example.emmanueldaviest.data.repository.localdatasource.ILocalDataSource
import com.example.emmanueldaviest.data.repository.localdatasource.LocalDataSource
import com.example.emmanueldaviest.data.repository.remotedatasource.IRemoteDataSource
import com.example.emmanueldaviest.data.repository.remotedatasource.RemoteDataSource
import com.example.emmanueldaviest.data.repository.room.TeaserDao
import com.example.emmanueldaviest.data.repository.room.TeaserDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RepositoryModule {
    @Provides
    @Reusable
    internal fun provideRemoteDataSource(apiInterface: HeadlineApiInterface): IRemoteDataSource {

        return RemoteDataSource(apiInterface)
    }

    @Provides
    @Singleton
    fun providesMealDataBase(application: Application): TeaserDatabase {
        return Room.databaseBuilder(
            application, TeaserDatabase::class.java,
            "trip_database.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesMealDao(teaserDatabase: TeaserDatabase): TeaserDao {
        return teaserDatabase.tripDatabaseDao()
    }

    @Provides
    @Reusable
    internal fun providesLocalDataSource(teaserDao: TeaserDao): ILocalDataSource {
        return LocalDataSource(teaserDao)
    }


    @Provides
    @Reusable
    internal fun getTripRepository(
        remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource
    ): IRepository {
        return TripRepository(
            remoteDataSource,
            localDataSource
        )
    }

}