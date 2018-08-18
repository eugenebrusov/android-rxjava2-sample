package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.eugenebrusov.brusovcodetest.R
import com.eugenebrusov.brusovcodetest.ui.repodetail.EXTRA_REPO
import com.eugenebrusov.brusovcodetest.ui.repodetail.RepoDetailActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
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

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        title = getString(R.string.title_repo_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoListViewModel::class.java)

        // Communication between components is done either through a shared ViewModel
        // as it's recommended here https://developer.android.com/training/basics/fragments/communicating
        disposable.add(viewModel.selectedRepoSubject
                .subscribe { repo ->
                    // Once user selects repo in list RepoListFragment notifies RepoListActivity via PublishSubject
                    val intent = Intent(this@RepoListActivity, RepoDetailActivity::class.java)
                    intent.putExtra(EXTRA_REPO, repo)
                    this@RepoListActivity.startActivity(intent)
                })
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
