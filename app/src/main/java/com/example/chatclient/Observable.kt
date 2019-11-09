package com.example.chatclient

interface Observable {

    fun addConnector(c:Observer)
    fun removeConnector(c:Observer)
    fun notifyObservers()
}