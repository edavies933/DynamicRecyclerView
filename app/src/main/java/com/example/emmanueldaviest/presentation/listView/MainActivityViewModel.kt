package com.example.emmanueldaviest.presentation.listView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emmanueldavies.flixbus.domain.interactor.ShowDepartureTripsUseCase
import com.emmanueldavies.flixbus.internal.Utility.INetworkManager
import com.example.emmanueldaviest.domain.model.Teaser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.spacenoodles.makingyourappreactive.viewModel.state.ViewState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModel @Inject constructor(
    private var showDepartureUseCase: ShowDepartureTripsUseCase,
    private val networkManager: INetworkManager,
    application: Application
) :
    AndroidViewModel(application) {
    private var disposables: CompositeDisposable = CompositeDisposable()
    private var state: MutableLiveData<ViewState> = MutableLiveData()
    fun getState(): MutableLiveData<ViewState> = state
    private val _teaser: MutableLiveData<List<Teaser>> = MutableLiveData()
    val teaser: LiveData<List<Teaser>> = _teaser

    init {
        getTeasers()
    }

    private fun getTeasers() {

        state.value = ViewState.loading()

        addSub(
            networkManager.hasInternetConnection().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ internet ->
                    showDepartureUseCase.buildUseCaseSingle(internet).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        {


                            if (it?.count() == 0) {

                                state.postValue(ViewState.noDataFound())

                            } else if  (it?.count()!! > 0){
                                _teaser.value = it
                                state.postValue(ViewState.success())
                            }
                            if (!internet) {
                                state.postValue(ViewState.noInternet())
                            }

                        },
                        {
                            state.postValue(ViewState.error(it))
                        })
                },
                    {
                        state.postValue(ViewState.error(it))

                    })
        )
    }

    @Synchronized
    private fun addSub(disposable: Disposable?) {
        if (disposable == null) return
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) disposables.dispose()
    }
}

