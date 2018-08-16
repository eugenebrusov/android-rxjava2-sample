package com.eugenebrusov.brusovcodetest.ui.repolist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eugenebrusov.brusovcodetest.R

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
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }


}
