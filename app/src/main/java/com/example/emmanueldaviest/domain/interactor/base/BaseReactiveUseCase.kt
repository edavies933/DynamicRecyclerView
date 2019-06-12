package com.emmanueldavies.flixbus.domain.interactor.base
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseReactiveUseCase{
    private val disposables = CompositeDisposable()

    open fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(checkNotNull(disposable, { "disposable cannot be null!" }))
    }
}
