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

/**
 * Responsible for preparing and managing the data for RepoListActivity and RepoListFragment.
 *
 * It also handles communication between RepoListActivity and RepoListFragment via shared context.
 */
class RepoListViewModel @Inject constructor(
        appRepository: AppRepository
) : ViewModel() {

    // Handles pagination.
    // Once list reaches its end it sends onNext event to this subject
    // for reposResourceFlowable to be initiated with next page data
    private val pageSubject: PublishSubject<Int> = PublishSubject.create()

    // Handles communication with GitHub API via AppRepository.loadRepositories(...) method
    val reposResourceFlowable = pageSubject
            .startWith(1)
            .toFlowable(BackpressureStrategy.MISSING)
            .observeOn(Schedulers.io())
            .switchMap { page ->
                appRepository.loadRepositories(page)
                        .map { repos ->
                            success(repos)
                        }
                        // Instead of terminating all the upstream observables
                        // in case of exception or error occured
                        // app wraps it with Resource.error
                        .onErrorReturn { throwable -> error(throwable) }
                        // While loadRepositories is in process app submits Resource.loading state to UI
                        .startWith(loading())
            }

    // Handles communication between Fragment and its hosted activity
    // It can be done via classic interface and `onAttach()` method,
    // but it's also recommended to use ViewModel's shared context
    // https://developer.android.com/training/basics/fragments/communicating
    val selectedRepoSubject: PublishSubject<Repo> = PublishSubject.create()

    // Loads next page by submitting next event to pageSubject
    fun loadPage(page: Int) {
        pageSubject.onNext(page)
    }

    // Submits selected repo to selectedRepoSubject to be shown on detail page
    fun selectRepo(repo: Repo) {
        selectedRepoSubject.onNext(repo)
    }
}