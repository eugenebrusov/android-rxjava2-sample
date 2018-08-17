package com.eugenebrusov.brusovcodetest.data.model

/**
 * A generic class that holds a value with its loading status.
 *
 * It follows https://developer.android.com/jetpack/docs/guide#addendum
 * @param <T>
 */
data class Resource<out T> (
        val status: Status,
        val data: T?,
        val error: Throwable?) {
    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun error(throwable: Throwable?) = Resource(Status.ERROR, null, throwable)
        fun loading() = Resource(Status.LOADING, null, null)
    }
}