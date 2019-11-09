package com.example.chatclient


/**
 * Singleton that stores list that recycleView reads, user input and clients current username.
 * Has functions to add messages to recycleView and to deliver ChatMessage objects from MainActivity to ServerConnector.
 */

object MessageStorage{

    var messages = ArrayList<String>()
    var username = ""
    var newMessage = false
    var userInput = ChatMessage("","")

    fun newMessage(msg: ChatMessage){
        userInput = msg
        newMessage = true
    }

    fun addMessageToLocalView(msg:String){
        messages.add(msg)

    }

}