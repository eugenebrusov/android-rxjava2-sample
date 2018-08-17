package com.eugenebrusov.brusovcodetest.data.source.github

import com.eugenebrusov.brusovcodetest.data.model.Repos
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * GitHub API access points
 */
interface GithubService {
    @GET("search/repositories?q=android+language:kotlin&sort=stars&page=1&per_page=20")
    fun searchRepos(): Flowable<Repos>
}