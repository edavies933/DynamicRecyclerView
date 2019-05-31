package com.example.emmanueldaviest.presentation.listView

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.emmanueldavies.mensapluse1.domain.interactor.LoadArticlesUseCase
import com.emmanueldavies.mensapluse1.internal.Utility.INetworkManager
import com.example.emmanueldaviest.domain.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.spacenoodles.makingyourappreactive.viewModel.state.ViewState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModel @Inject constructor(
    private var loadArticleUseCase: LoadArticlesUseCase,
    private val networkManager: INetworkManager,
    application: Application
) :
    AndroidViewModel(application) {

    private var disposables: CompositeDisposable = CompositeDisposable()
    var state: MutableLiveData<ViewState> = MutableLiveData()

    var articles: MutableLiveData<List<Article>> = MutableLiveData()

    fun getHeadlines() {

        addSub(

            networkManager.hasInternetConnection().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ internet ->
                    if (!internet) {
                        state.postValue(ViewState.noInternet())

                    }
                    loadArticleUseCase.buildUseCaseSingle(false).observeOn(AndroidSchedulers.mainThread()).subscribe(

                        {
                            articles.value = it
                            state.postValue(ViewState.success())

                            if (it.count() == 0) {

                                state.postValue(ViewState.noDataFound())

                            }

                        },
                        {

                            state.postValue(ViewState.error(it))

                            Log.d("rx Error ", it.message)
                        })
                },
                    {
                        Log.d("network manager Error", it.message)
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

