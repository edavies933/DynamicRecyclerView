package com.example.emmanueldaviest.data.repository.api

import com.example.emmanueldaviest.domain.Headline
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HeadlineApiInterface {

    @GET("top-headlines?country=us&apiKey=17a2a7662fd142acb47ea71cfc447122&category=technology")
    fun getHeadlines(): Maybe< Headline>
}

//@GET("canteens")
//fun getCanteens(@Query("near[lat]") latitude: Double, @Query("near[lng]") longitude: Double, @Query("near[dist]") distance: Int): Maybe<List<Canteen>>
//
////    http://openmensa.org/api/v2/canteens/1/days/2018-12-05/meals
//
//@GET("canteens/{canteenId}/days/{date}/meals")
//fun getMeals(@Path("canteenId") canteenId: Int, @Path("date") date: String): Maybe<List<Meal>>