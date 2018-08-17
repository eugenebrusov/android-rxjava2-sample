package com.eugenebrusov.brusovcodetest.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.eugenebrusov.brusovcodetest.App
import dagger.android.AndroidInjection

object AppInjector {
    fun init(app: App) {
        DaggerAppComponent.builder().application(app)
                .build().inject(app)
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AndroidInjection.inject(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
}