package com.example.emmanueldaviest.data.repository.remotedatasource

import com.example.emmanueldaviest.domain.model.Headline
import io.reactivex.Single

interface IRemoteDataSource {
    fun getHeadline(): Single<Headline>
}