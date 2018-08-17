package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModel
import com.eugenebrusov.brusovcodetest.data.source.AppRepository
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
        private val appRepository: AppRepository
) : ViewModel() {
    val repoList = appRepository.getRepoList()
}