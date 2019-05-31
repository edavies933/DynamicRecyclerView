package com.example.emmanueldaviest.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emmanueldavies.mensapluse1.domain.interactor.base.SingleUseCase
import com.example.emmanueldaviest.data.repository.ArticleRepository
import com.example.emmanueldaviest.data.repository.IRepository
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.usecase.reactiveusecasessample.domain.executor.PostExecutionThread
import com.usecase.reactiveusecasessample.domain.executor.ThreadExecutor
import io.reactivex.Single
import javax.inject.Inject

@Entity(tableName = "headline_table")
class Headline (
    // Getter Methods

    @SerializedName("status")
    @Expose
    var status: String?,
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int?,
    @SerializedName("articles")
    @Expose
     var articles : List<Article>
)

@Entity(tableName = "article_table")
class  Article (
    @PrimaryKey(autoGenerate = true)
    var articlePrimaryKey: Int?,
    @SerializedName("source")
    @Expose
    @Ignore
    var source : Source?,
    @SerializedName("author")
    @Expose
    var author: String? ,
    @SerializedName("title")
    @Expose
    var title: String? ,
    @SerializedName("description")
    @Expose
    var description: String? ,
    @SerializedName("url")
    @Expose
    var url: String?,
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? ,
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String?,
    @SerializedName("content")
    @Expose
    var content: String? = null

){
    constructor(): this (null,null,"","","","","","","")
}


class Source (
    @SerializedName("id")
    @Expose
   var id: String?,
    @SerializedName("name")
    @Expose
   var name: String? = null
)


