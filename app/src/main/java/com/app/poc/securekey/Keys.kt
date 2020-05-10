package com.app.poc.securekey

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}