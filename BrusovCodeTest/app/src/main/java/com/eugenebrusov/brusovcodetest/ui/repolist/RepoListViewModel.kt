package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModel
import com.eugenebrusov.brusovcodetest.data.model.Repo
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.error
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.loading
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.success
import com.eugenebrusov.brusovcodetest.data.source.AppRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
        appRepository: AppRepository
) : ViewModel() {

    private val pageSubject: PublishSubject<Int> = PublishSubject.create()

    val reposResourceFlowable = pageSubject
            .startWith(1)
            .toFlowable(BackpressureStrategy.MISSING)
            .observeOn(Schedulers.io())
            .switchMap { page ->
                appRepository.loadRepositories(page)
                        .map { repos ->
                            success(repos)
                        }
                        .onErrorReturn { throwable -> error(throwable) }
                        .startWith(loading())
            }

    val selectedRepoSubject: PublishSubject<Repo> = PublishSubject.create()

    fun loadPage(page: Int) {
        pageSubject.onNext(page)
    }

    fun selectRepo(repo: Repo) {
        selectedRepoSubject.onNext(repo)
    }
}