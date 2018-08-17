package com.eugenebrusov.brusovcodetest.ui.repolist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenebrusov.brusovcodetest.R
import kotlinx.android.synthetic.main.fragment_repo_list.view.*

/**
 * Displays repo list
 *
 * I added this fragment to re-use it in different layout configurations
 * as it's recommended on https://developer.android.com/guide/components/fragments
 */
class RepoListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_repo_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = RepoListAdapter()
        recyclerView.adapter = adapter

        return view
    }
}
