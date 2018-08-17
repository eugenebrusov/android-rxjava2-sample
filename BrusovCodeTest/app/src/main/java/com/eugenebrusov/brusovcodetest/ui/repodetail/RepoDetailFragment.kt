package com.eugenebrusov.brusovcodetest.ui.repodetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenebrusov.brusovcodetest.R
import com.eugenebrusov.brusovcodetest.data.model.Repo
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import java.text.SimpleDateFormat
import java.util.*

class RepoDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repo = activity?.intent?.getParcelableExtra<Repo>(EXTRA_REPO)
        titleTextView.text = repo?.fullName
        descriptionTextView.text = repo?.description
        linkTextView.text = repo?.link

        val dateFormatInstance = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val time = dateFormatInstance.parse(repo?.updatedAt).time
        val now = System.currentTimeMillis()
        updatedTextView.text = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

        starsTextView.text = "${repo?.stars}"
    }
}
