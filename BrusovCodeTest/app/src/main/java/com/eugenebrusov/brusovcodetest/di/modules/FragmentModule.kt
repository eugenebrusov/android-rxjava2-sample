package com.eugenebrusov.brusovcodetest.di.modules

import com.eugenebrusov.brusovcodetest.ui.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoListFragment(): RepoListFragment
}