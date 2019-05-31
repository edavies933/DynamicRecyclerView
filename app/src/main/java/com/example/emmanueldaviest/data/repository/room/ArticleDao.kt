package com.example.emmanueldaviest.data.repository.room

import androidx.room.*
import com.example.emmanueldaviest.domain.Article
import io.reactivex.Single

@Dao
//@TypeConverters(ArticleSourceConverter::class)
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertArticle(article: Article)

    @Query("SELECT * FROM article_table")
    fun getAllArticles () : Single<List<Article>>


    @Query("DELETE FROM article_table")
    fun deleteAllArticles ()
}


@Database(entities = [Article::class], version = 1)
//@TypeConverters(ArticleSourceConverter::class)
abstract class ArticleDatabase  : RoomDatabase() {

    abstract fun articleDatabaseDao(): ArticleDao
}
