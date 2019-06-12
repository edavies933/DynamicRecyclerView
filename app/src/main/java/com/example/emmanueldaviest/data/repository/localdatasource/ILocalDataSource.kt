package com.example.emmanueldaviest.data.repository.localdatasource

import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single

interface ILocalDataSource {
    fun queryForAllDeparturesTrips(): Single<List<Teaser>>
    fun saveAllDeparturesTripsToDatabase(teaser: List<Teaser>)
}