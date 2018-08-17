package com.eugenebrusov.brusovcodetest.ui.repodetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eugenebrusov.brusovcodetest.R

const val EXTRA_REPO = "extra_repo"

class RepoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        title = getString(R.string.title_repo_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
