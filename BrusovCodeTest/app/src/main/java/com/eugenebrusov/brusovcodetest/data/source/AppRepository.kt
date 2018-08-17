package com.eugenebrusov.brusovcodetest.data.source

import com.eugenebrusov.brusovcodetest.DEFAULT_PAGE_SIZE
import com.eugenebrusov.brusovcodetest.data.source.github.GithubServicer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
        private val githubServicer: GithubServicer
) {
    fun loadRepositories(page: Int) =
        githubServicer.searchRepos("android+language:kotlin", page, DEFAULT_PAGE_SIZE)
}