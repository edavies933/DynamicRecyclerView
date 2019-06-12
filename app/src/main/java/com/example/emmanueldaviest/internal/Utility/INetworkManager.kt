package com.emmanueldavies.flixbus.internal.Utility

import io.reactivex.Single

interface INetworkManager {

    fun hasInternetConnection(): Single<Boolean>
}