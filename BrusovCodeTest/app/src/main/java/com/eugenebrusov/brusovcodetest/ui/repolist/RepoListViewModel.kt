package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModel
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.error
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.loading
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.success
import com.eugenebrusov.brusovcodetest.data.source.AppRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
        appRepository: AppRepository
) : ViewModel() {

    private val pageSubject: PublishSubject<Int> = PublishSubject.create()

    val reposResourceFlowable = pageSubject
            .startWith(1)
            .toFlowable(BackpressureStrategy.MISSING)
            .switchMap { page ->
                appRepository.loadPage(page)
                        .map { repos ->
                            success(repos)
                        }
                        .onErrorReturn { throwable -> error(throwable) }
                        .startWith(loading())
            }

    fun retry() {
        pageSubject.onNext(1)
    }
}