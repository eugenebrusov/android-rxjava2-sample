package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenebrusov.brusovcodetest.R
import com.eugenebrusov.brusovcodetest.di.Injectable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*
import javax.inject.Inject

/**
 * Displays repo list
 *
 * Fragments were added to re-use their UI in different layout configurations
 * as it's recommended on https://developer.android.com/guide/components/fragments
 */
class RepoListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RepoListViewModel

    private val disposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        val itemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(RepoListViewModel::class.java)

        val adapter = RepoListAdapter()
        recyclerView.adapter = adapter

        adapter.onPageRequested = { page ->
            viewModel.loadPage(page)
        }

        adapter.onRepoSelected = { repo ->
            viewModel.selectRepo(repo)
        }

        // Subscribes for data updates coming from GitHub API
        disposable.add(viewModel.reposResourceFlowable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resource ->
                    adapter.resource = resource
                }))
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}
