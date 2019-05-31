package com.example.emmanueldaviest.data.repository

import com.example.emmanueldaviest.data.repository.api.HeadlineApiInterface
import com.example.emmanueldaviest.data.repository.room.ArticleDao
import com.example.emmanueldaviest.domain.Article
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalDataSource(private val articleDao: ArticleDao) : ILocalDataSource {
    override fun saveHeadlinesToDatabase(article: List<Article>) {
        for (article in article){

            articleDao.insertArticle(article)

        }

    }

    override fun queryForAllArticles(): Single<List<Article>> {
        return articleDao.getAllArticles()

    }
}


class RemoteDataSource(private var apiInterface: HeadlineApiInterface) : IRemoteDataSource {

    override fun getArticles(): Single<List<Article>> {
        return apiInterface.getHeadlines().subscribeOn(Schedulers.io()).toSingle()
            .flatMap { return@flatMap Single.just(it.articles) }
    }

}

interface ILocalDataSource {
    fun queryForAllArticles(): Single<List<Article>>
    fun saveHeadlinesToDatabase(article: List<Article>)
}

interface IRemoteDataSource {
    fun getArticles(): Single<List<Article>>
}


interface IRepository {
    fun getHeadlines(): Single<List<Article>>
    fun getHeadlinesDirectFromLocalDatabase(): Single<List<Article>>

}

class ArticleRepository(
    val mRemoteDataSource: IRemoteDataSource,
    val mLocalDataSource: ILocalDataSource
) : IRepository {
    override fun getHeadlinesDirectFromLocalDatabase(): Single<List<Article>> {
return  mLocalDataSource.queryForAllArticles()
    }

    override fun getHeadlines(): Single<List<Article>> {

        return mRemoteDataSource.getArticles().subscribeOn(Schedulers.io()).doOnSuccess {

            mLocalDataSource.saveHeadlinesToDatabase(it)
        }

    }

}
