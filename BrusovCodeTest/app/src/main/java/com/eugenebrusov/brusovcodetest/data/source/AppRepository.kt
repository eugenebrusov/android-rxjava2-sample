package com.eugenebrusov.brusovcodetest.data.source

import com.eugenebrusov.brusovcodetest.data.source.github.GithubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
        private val githubService: GithubService
) {
    fun loadPage(page: Int) = githubService.searchRepos()
}