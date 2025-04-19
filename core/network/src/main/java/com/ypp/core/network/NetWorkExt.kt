package com.ypp.core.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

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
suspend fun <T> request(
    call: suspend () -> T,
    sync: suspend (T)->Unit
):Result<T> = runCatching {
    call()
} .onSuccess { sync(it) }
    .onFailure {
        //此处处理网络失败的相关异常
        if (it is CancellationException ){
            throw it
        }else{
            it.printStackTrace()
        }
    }


fun<T> request(
    call:suspend ()->T):Flow<T>{
    return flow {
        val response=call()
        emit(response)

       }
}

