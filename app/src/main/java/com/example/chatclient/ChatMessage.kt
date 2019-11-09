package com.example.chatclient

import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

/**
 * Contains message which user wishes to send to chat, along with its senders username and timestamp of when the message was sent.
 */

@Serializable
open class ChatMessage( val msg: String, val user: String, val time : String = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))) {

    override fun toString():String{
        return "$time $user: $msg"
    }
}