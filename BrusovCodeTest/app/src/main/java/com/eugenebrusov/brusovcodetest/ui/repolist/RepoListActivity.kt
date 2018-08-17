package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.eugenebrusov.brusovcodetest.R
import javax.inject.Inject

/**
 * Displays repo list
 */
class RepoListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RepoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RepoListViewModel::class.java)
    }
}
