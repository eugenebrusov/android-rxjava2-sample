package com.eugenebrusov.brusovcodetest.di.modules

import com.eugenebrusov.brusovcodetest.ui.repolist.RepoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): RepoListActivity
}