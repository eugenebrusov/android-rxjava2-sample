package com.eugenebrusov.brusovcodetest.ui.repolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenebrusov.brusovcodetest.DEFAULT_PAGE_SIZE
import com.eugenebrusov.brusovcodetest.R
import com.eugenebrusov.brusovcodetest.data.model.Repo
import com.eugenebrusov.brusovcodetest.data.model.Repos
import com.eugenebrusov.brusovcodetest.data.model.Resource
import com.eugenebrusov.brusovcodetest.data.model.Resource.Companion.loading
import com.eugenebrusov.brusovcodetest.data.model.Status.*
import kotlinx.android.synthetic.main.item_repo_list_data.view.*
import kotlinx.android.synthetic.main.item_repo_list_error.view.*
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    // Holds list of repositories
    private val repoList = mutableListOf<Repo>()

    // This lambda is invoked once list reaches its end
    var onPageRequested: ((page: Int) -> Unit)? = null

    // This lambda is invoked once user taps on item in repo list
    var onRepoSelected: ((repo: Repo) -> Unit)? = null

    // Holds Resource<Repos> which exposes current loading state with data or error
    var resource: Resource<Repos> = loading()
        set(value) {
            if (SUCCESS == value.status && value.data != null) {
                repoList.addAll(value.data.items)
            }
            field = value
            notifyDataSetChanged()
        }

    // Creates respective ViewHolder by given viewType
    // viewType is represented by layout resId
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            when (viewType) {
                R.layout.item_repo_list_data -> DataViewHolder(parent)
                R.layout.item_repo_list_error -> ErrorViewHolder(parent, View.OnClickListener {
                    val nextPage = repoList.size / DEFAULT_PAGE_SIZE + 1
                    onPageRequested?.invoke(nextPage)
                })
                R.layout.item_repo_list_loading -> LoadingViewHolder(parent)
                else -> throw IllegalArgumentException("Invalid viewType: $viewType")
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            val repo = repoList[position]
            holder.data = repo
            holder.itemView.setOnClickListener {
                onRepoSelected?.invoke(repo)
            }
        } else if (holder is ErrorViewHolder) {
            holder.error = resource.error
        }

        // If current nothing is currently loading and list scrolled to its last position
        // then calcualte nextPage number then initiate next page request via onPageRequested lambda
        if (SUCCESS == resource.status && position >= itemCount - 1) {
            val nextPage = repoList.size / DEFAULT_PAGE_SIZE + 1
            onPageRequested?.invoke(nextPage)
        }
    }

    override fun getItemCount() =
            when (resource.status) {
                SUCCESS -> repoList.size
                ERROR -> repoList.size + 1
                LOADING -> repoList.size + 1
            }

    override fun getItemViewType(position: Int) =
            when (resource.status) {
                SUCCESS -> R.layout.item_repo_list_data
                ERROR -> {
                    if (position == itemCount - 1)
                        R.layout.item_repo_list_error else R.layout.item_repo_list_data
                }
                LOADING -> {
                    if (position == itemCount - 1)
                        R.layout.item_repo_list_loading else R.layout.item_repo_list_data
                }
            }

    open class ViewHolder(parent: ViewGroup?, resId: Int)
        : RecyclerView.ViewHolder(
            LayoutInflater.from(parent?.context).inflate(resId, parent, false))

    // Holds view that represents repo data
    class DataViewHolder(
            val parent: ViewGroup
    ) : ViewHolder(parent, R.layout.item_repo_list_data) {
        var data: Repo? = null
            set(value) {
                field = value
                itemView.fullNameTextView.text = value?.fullName
                itemView.descriptionTextView.text = value?.description

                val time = DataViewHolder.dateFormatInstance.parse(value?.updatedAt).time
                val now = System.currentTimeMillis()

                itemView.updatedTextView.text = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                itemView.starsTextView.text = "${value?.stars}"
            }

        companion object {
            val dateFormatInstance = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        }
    }

    // Holds view that represents error from GitHub API
    class ErrorViewHolder(
            val parent: ViewGroup, val onRetryListener: View.OnClickListener
    ) : ViewHolder(parent, R.layout.item_repo_list_error) {
        var error: Throwable? = null
            set(value) {
                field = value
                itemView.errorTextView.text = value?.localizedMessage
                itemView.retryButton.setOnClickListener(onRetryListener)
            }
    }

    // Holds view that shows loading spinner while API request is in process
    class LoadingViewHolder(
            val parent: ViewGroup
    ) : ViewHolder(parent, R.layout.item_repo_list_loading)
}