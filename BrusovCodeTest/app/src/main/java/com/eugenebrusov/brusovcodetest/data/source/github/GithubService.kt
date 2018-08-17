package com.eugenebrusov.brusovcodetest.data.source.github

import com.eugenebrusov.brusovcodetest.data.model.Repos
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * GitHub API access points
 */
interface GithubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String,
                    @Query("page") page: Int,
                    @Query("per_page") pageSize: Int,
                    @Query("sort") sort:String = "stars"): Flowable<Repos>
}