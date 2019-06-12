package com.example.emmanueldaviest.data.repository.localdatasource

import com.example.emmanueldaviest.data.repository.room.TeaserDao
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class LocalDataSource(private val teaserDao: TeaserDao) :
    ILocalDataSource {
    override fun queryForAllDeparturesTrips(): Single<List<Teaser>> {
        return teaserDao.getAllTrip().subscribeOn(Schedulers.io())
    }

    override fun saveAllDeparturesTripsToDatabase(teasers: List<Teaser>) {
        teaserDao.deleteAllTrip()
        for (trip in teasers) {
            teaserDao.insertTrip(trip)
        }


    }
}


