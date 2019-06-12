package com.emmanueldavies.flixbus.domain.interactor

import android.app.Application
import com.emmanueldavies.domain.interactor.base.SingleUseCase
import com.example.emmanueldaviest.R
import com.example.emmanueldaviest.data.repository.IRepository
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.Single
import javax.inject.Inject

class ShowDepartureTripsUseCase
@Inject
constructor(
    private val teaserRepository: IRepository,
    private val context: Application

) : SingleUseCase<List<Teaser>, Boolean>() {

    override fun buildUseCaseSingle(hasInternet: Boolean?): Single<List<Teaser>> {


        if (hasInternet != null && hasInternet) {


            return teaserRepository.getHeadLine().flatMap {


                if (it.teasers != null) {
                    for (teaser in it.teasers!!) {
                      teaser.categoryName = it.name?: " "
                        assignContentCost(teaser)
                    }

                    teaserRepository.saveTeasersToDatabase(it.teasers!!)
                }
                return@flatMap Single.just(it.teasers)
            }

        } else {

            return teaserRepository.getTeasersDirectFromLocalDatabase().flatMap {


                if (it != null) {

                    for (teaser in it) {

                        assignContentCost(teaser)
                    }

                }
                return@flatMap Single.just(it)
            }


        }

    }

    private fun assignContentCost(teaser: Teaser) {
        if (teaser.isPaid != null && teaser.isPaid!!) {

            teaser.paidDescription = context.getString(R.string.paid_content)
        } else {
            teaser.paidDescription = context.getString(R.string.free_content)
        }
    }


}


