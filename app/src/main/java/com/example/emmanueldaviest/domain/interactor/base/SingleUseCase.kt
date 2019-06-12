package com.emmanueldavies.domain.interactor.base


import com.emmanueldavies.flixbus.domain.interactor.base.BaseReactiveUseCase
import io.reactivex.Single

abstract class SingleUseCase<Results, in Params>
 : BaseReactiveUseCase() {
    abstract fun buildUseCaseSingle(params: Params? = null): Single<Results>
}
