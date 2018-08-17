package com.eugenebrusov.brusovcodetest.di

import android.app.Application
import com.eugenebrusov.brusovcodetest.App
import com.eugenebrusov.brusovcodetest.di.modules.ActivityModule
import com.eugenebrusov.brusovcodetest.di.modules.ViewModelModule
import com.eugenebrusov.brusovcodetest.di.modules.AppModule
import com.eugenebrusov.brusovcodetest.di.modules.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            ViewModelModule::class,
            ActivityModule::class,
            FragmentModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}