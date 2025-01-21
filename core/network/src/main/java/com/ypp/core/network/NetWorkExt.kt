package com.ypp.core.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun<T> request(
    call:suspend ()->T,
    succeed: (T) -> Unit,
    fail: () -> Unit){
    runCatching {
        call()
    }.onSuccess {
        succeed(it)
    }.onFailure {
        it.printStackTrace()
            fail()
    }
}
fun<T> request(
    call:suspend ()->T):Flow<T>{
    return flow {
        val response=call()
        if (true){
            throw Throwable("xxxxxx")
        }else{
            emit(response)
        }
       }
}

