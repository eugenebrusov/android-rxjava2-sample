package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.eugenebrusov.brusovcodetest.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Displays repo list
 */
class RepoListActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RepoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoListViewModel::class.java)

        viewModel.repoList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonResults ->
                    Log.e("repoList", "jsonResults $jsonResults")
                }, { throwable ->
                    Log.e("repoList", "throwable $throwable")
                })
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
