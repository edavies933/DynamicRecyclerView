package com.example.emmanueldaviest.data.repository.room

import androidx.room.*
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single

@Dao
interface TeaserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertTrip(teaser: Teaser)

    @Query("SELECT * FROM teaser_table")
    fun getAllTrip () : Single<List<Teaser>>


    @Query("DELETE FROM teaser_table")
    fun deleteAllTrip ()
}


