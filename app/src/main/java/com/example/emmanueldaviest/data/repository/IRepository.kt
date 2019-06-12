package com.example.emmanueldaviest.data.repository

import com.example.emmanueldaviest.domain.model.Headline
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single

interface IRepository {
    fun getHeadLine(): Single<Headline>
    fun getTeasersDirectFromLocalDatabase(): Single<List<Teaser>>
    fun saveTeasersToDatabase(teasers: List<Teaser>)
}