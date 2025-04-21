package com.ypp.websocket

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URI

class WebSocketDemo {
    val webSocketConnect by lazy { WebSocketConnect.getInstance(URI("ws:xxxxxxxx")) }
    var disposable: Disposable?=null
    fun reception(){
        //接收处
        //按需接收选择线程
        disposable=webSocketConnect.webSocketFlowable
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                //如果具体到某个Activity，可以用lifecycle判断是否处理

            },{

            })
    }
    fun send(message:String){
        webSocketConnect.sendMessage(message)
    }
    fun onRelease(){
        disposable?.dispose()
    }

}