package com.eugenebrusov.brusovcodetest.ui.repolist

import android.arch.lifecycle.ViewModel
import com.eugenebrusov.brusovcodetest.data.source.AppRepository
import javax.inject.Inject

class RepoListViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel()