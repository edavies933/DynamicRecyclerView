package com.example.emmanueldaviest.data.repository.remotedatasource

import com.example.emmanueldaviest.data.repository.api.HeadlineApiInterface
import com.example.emmanueldaviest.domain.model.Headline
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RemoteDataSource(private val apiInterface: HeadlineApiInterface) :
    IRemoteDataSource {

    override fun getHeadline(): Single<Headline> {
        return apiInterface.getTimeTable().subscribeOn(Schedulers.io()).toSingle().doOnSuccess {

        }
            .flatMap { return@flatMap Single.just(it) }
    }

}