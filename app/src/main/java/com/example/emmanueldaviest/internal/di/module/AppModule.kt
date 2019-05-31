package com.example.emmanueldaviest.internal.di.module

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.emmanueldavies.mensapluse1.internal.Utility.INetworkManager
import com.emmanueldavies.mensapluse1.internal.Utility.NetWorkManagerImpl
import com.example.emmanueldaviest.data.repository.*
import com.example.emmanueldaviest.data.repository.api.HeadlineApiInterface
import com.example.emmanueldaviest.data.repository.room.ArticleDao
import com.example.emmanueldaviest.data.repository.room.ArticleDatabase
import com.example.emmanueldaviest.presentation.AppViewModelFactory
import com.example.emmanueldaviest.presentation.MainActivity
import com.example.emmanueldaviest.presentation.detailView.DetailsFragment
import com.example.emmanueldaviest.presentation.detailView.DetailsViewModel
import com.example.emmanueldaviest.presentation.listView.ListFragment
import com.example.emmanueldaviest.presentation.listView.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {


    @Singleton
    @Provides
    fun provideHeadlineService(okHttpClient: OkHttpClient): HeadlineApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

            .create(HeadlineApiInterface::class.java)


    }

    @Provides
    @Singleton
    fun  providesINetWorkManager () : INetworkManager {

        return NetWorkManagerImpl()
    }



    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {


        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()


    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {

        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }

}


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailsFragment
}


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMealViewModel(mealViewModel: MainActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindMapViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}

@Module(includes = [AppModule::class])
class RepositoryModule{
    @Provides
    @Reusable
    internal fun provideRemoteDataSource(apiInterface: HeadlineApiInterface): IRemoteDataSource {

        return RemoteDataSource(apiInterface)
    }

    @Provides
    @Singleton
    fun providesMealDataBase(application: Application):ArticleDatabase{

        var mensalDataBase = Room.databaseBuilder(application,ArticleDatabase::class.java,
            "meal_database.db").fallbackToDestructiveMigration().build()
        return  mensalDataBase
    }

    @Provides
    @Singleton
    fun providesMealDao (articleDatabase: ArticleDatabase) : ArticleDao {

        return  articleDatabase.articleDatabaseDao()
    }

    @Provides
    @Reusable
    internal fun providesLocalDataSource(articleDao: ArticleDao): ILocalDataSource {

        return LocalDataSource(articleDao)
    }



    @Provides
    @Reusable
    internal fun getMensaRepository( remoteDataSource: IRemoteDataSource, localDataSource: ILocalDataSource
    ): IRepository {
        return ArticleRepository(remoteDataSource,localDataSource)
    }

}