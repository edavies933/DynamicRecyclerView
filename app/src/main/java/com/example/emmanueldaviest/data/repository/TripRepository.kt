package com.example.emmanueldaviest.data.repository

import com.example.emmanueldaviest.data.repository.localdatasource.ILocalDataSource
import com.example.emmanueldaviest.data.repository.remotedatasource.IRemoteDataSource
import com.example.emmanueldaviest.domain.model.Headline
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TripRepository(
    private val mRemoteDataSource: IRemoteDataSource,
    private val mLocalDataSource: ILocalDataSource
) : IRepository {
    override fun saveTeasersToDatabase(teaser: List<Teaser>) {
        mLocalDataSource.saveAllDeparturesTripsToDatabase(teaser)
    }

    override fun getTeasersDirectFromLocalDatabase(): Single<List<Teaser>> {
        return mLocalDataSource.queryForAllDeparturesTrips()
    }


    override fun getHeadLine(): Single<Headline> {

        return mRemoteDataSource.getHeadline().subscribeOn(Schedulers.io())

    }
}