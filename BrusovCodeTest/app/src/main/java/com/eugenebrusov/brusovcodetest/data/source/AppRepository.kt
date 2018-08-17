package com.eugenebrusov.brusovcodetest.data.source

import com.eugenebrusov.brusovcodetest.data.source.github.GitHubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
        private val gitHubService: GitHubService
)