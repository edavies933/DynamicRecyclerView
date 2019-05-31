/*
 * Copyright (C) 2018 StepStone Services Sp. z o.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.emmanueldavies.mensapluse1.domain.interactor


import com.emmanueldavies.mensapluse1.domain.interactor.base.SingleUseCase
import com.example.emmanueldaviest.data.repository.IRepository
import com.example.emmanueldaviest.domain.Article
import com.usecase.reactiveusecasessample.domain.executor.PostExecutionThread
import com.usecase.reactiveusecasessample.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoadArticlesUseCase
@Inject
constructor(  
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val articleRepository: IRepository
) : SingleUseCase<List<Article>, Boolean>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseSingle(hasInternet: Boolean?): Single<List<Article>> {

        if (hasInternet != null && hasInternet){

            return articleRepository.getHeadlines()
        } else {

            return  articleRepository.getHeadlinesDirectFromLocalDatabase().subscribeOn(Schedulers.io())
        }
    }


}


