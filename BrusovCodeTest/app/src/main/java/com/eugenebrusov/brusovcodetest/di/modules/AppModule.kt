package com.eugenebrusov.brusovcodetest.di.modules

import com.eugenebrusov.brusovcodetest.data.source.github.GitHubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGitHubService() = GitHubService()
}