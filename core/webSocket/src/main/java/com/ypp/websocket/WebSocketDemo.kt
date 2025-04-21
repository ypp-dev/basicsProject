package com.ypp.websocket

import io.reactivex.schedulers.Schedulers
import java.net.URI

class WebSocketDemo {
    val webSocketConnect by lazy { WebSocketConnect.getInstance(URI("ws:xxxxxxxx")) }
    fun reception(){
        //接收处
        //按需接收选择线程
        webSocketConnect.webSocketFlowable
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                //如果具体到某个Activity，可以用

            },{

            })
    }
    fun send(message:String){
        webSocketConnect.sendMessage(message)
    }

}