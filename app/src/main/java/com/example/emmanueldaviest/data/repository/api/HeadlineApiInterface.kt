package com.example.emmanueldaviest.data.repository.api

import com.example.emmanueldaviest.domain.model.Headline
import io.reactivex.Maybe
import retrofit2.http.GET


interface HeadlineApiInterface {

    @GET("network/")
    fun getTimeTable(): Maybe<Headline>


}

