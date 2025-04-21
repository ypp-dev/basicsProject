package com.ypp.websocket

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.java_websocket.client.WebSocketClient
import org.java_websocket.enums.ReadyState
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.concurrent.TimeUnit

class WebSocketConnect private constructor(uri: URI) : WebSocketClient(uri) {

    companion object {
        private var instance: WebSocketConnect? = null
        fun getInstance(uri: URI): WebSocketConnect {
            if (instance == null) {
                synchronized(WebSocketConnect::class.java) {
                    if (instance == null) {
                        instance = WebSocketConnect(uri).apply {
                            connectionLostTimeout = 5 * 1000
                            //连接
                            connect()
                        }

                    }
                }
            }
            return instance!!
        }
    }
    val webSocketFlowable= PublishSubject.create<String>()
    //Flow也是一致用法

    fun sendMessage(message:String){
        //考虑使用协程控制发送线程
        if (readyState.equals(ReadyState.OPEN)){
            send(message)

        }

    }
    private var reConnectDisposable: Disposable? = null
    private fun startReConnect() {
        if (reConnectDisposable==null){
            reConnectDisposable = Flowable.interval(0, 5, TimeUnit.SECONDS)
                .doOnNext {
                    if (isClosed) {
                        reconnect()
                    } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({

                }, {
//                    XLog.e("捕获到重连异常" + it.message)
                })
        }else{
//            XLog.e("重连任务已在执行中")
        }
    }


    private fun clearReConnect() {
        if (reConnectDisposable?.isDisposed == true){
            reConnectDisposable?.dispose()
            reConnectDisposable=null

        }

    }

    override fun onOpen(handshakedata: ServerHandshake?) {
        //握手成功
        clearReConnect()

    }

    override fun onMessage(message: String?) {
//        XLog.e("收到的消息$message")
        if (message?.isNotEmpty() == true){
            webSocketFlowable.onNext(message)
        }


    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
//        XLog.e("连接关闭$code 错误描述$reason 是否远端$remote")
        startReConnect()
    }

    override fun onError(ex: Exception?) {
        startReConnect()
//        XLog.e("websocket捕获到异常${ex?.message}")
        ex?.printStackTrace()
    }

    class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Int) :
        Function<Flowable<Throwable>, Flowable<*>> {

        private var retryCount = 0

        init {
            retryCount = 0
        }

        override fun apply(attempts: Flowable<Throwable>): Flowable<*> {
            return attempts.flatMap { throwable ->
                if (++retryCount <= maxRetries) {
                    Flowable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
                } else {
                    Flowable.error<Any>(throwable)
                }
            }
        }
    }

}