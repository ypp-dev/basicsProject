package com.ypp.basicsproject

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    var sIsSafeMode: Boolean = false

    override fun onCreate() {
        Handler(Looper.getMainLooper()).post {
            //主线程异常拦截
            while (true) {
                try {
                    Looper.loop() //主线程的异常会从这里抛出
                } catch (e: Throwable) {
                    Log.e("TAG", "主线程异常$e: ")
                }
            }
        }

        //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            Log.e("TAG", "子线程异常${t.name}$e: ")
            if (t == Looper.getMainLooper().thread) {
//                isChoreographerException(e);
                Log.e("TAG", "主线程异常$e: ")
                while (true) {
                    try {
                        Looper.loop()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        Log.e("TAG", "主线程异常$e: ")
                    }
                }
            }

        }
        /**
         * view measure layout draw时抛出异常会导致Choreographer挂掉
         * <p>
         * 建议直接杀死app。以后的版本会只关闭黑屏的Activity
         *
         * @param e
         */


        super.onCreate()


    }



}