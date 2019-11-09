package com.example.chatclient

import android.util.Log
import kotlinx.serialization.json.Json
import java.net.Socket
import java.io.PrintWriter
import java.lang.Exception
import java.util.*

/**
 * Handles connection to server through socket and client's input/output through sockets input- and outputStream.
 * Socket in opened in one thread and input and output are handled in their subthreads, which run constantly as long as socket is connected to server.
 * This class also acts as Observable to MainActivity so that its recycleView can be updated in real time.
 */

object ServerConnector: Runnable, Observable {

    var storage = mutableSetOf<Observer>()

    override fun run(){

        val t = Thread(Runnable {
            Log.d("Log", "Socket thread started")

            val s = Socket("192.168.100.20",1994)

            val printer = PrintWriter(s.getOutputStream(), true)
            val scanner = Scanner(s.getInputStream())

            if(s.isConnected) {
                try {

                    val receivingThread = Thread(Runnable {
                        Log.d("Log", "Receiving thread started")
                        while(s.isConnected) {
                            MessageStorage.addMessageToLocalView(scanner.nextLine())
                            Log.d("Log", "Message from server received")
                            notifyObservers()
                        }
                    })
                    receivingThread.start()

                    val sendingThread = Thread(Runnable {
                        Log.d("Log","Sending thread started")
                        while(s.isConnected){
                            if (MessageStorage.newMessage) {

                                MessageStorage.newMessage = false

                                try {
                                    val msgAsJson : String = Json.stringify(ChatMessage.serializer(), MessageStorage.userInput)
                                    printer.println(msgAsJson)
                                    Log.d("Log", "User input sent to server")

                                } catch (e: Exception) {
                                    Log.d("Log", "Error_:$e")
                                }
                            }
                        }
                    })
                    sendingThread.start()

                    } catch (e: Exception) {
                        Log.d("Log", "Error_:$e")
                    }
                }
            })
        t.start()
    }

    override fun addConnector(c: Observer) {
        storage.add(c)
    }

    override fun notifyObservers() {
        for(c in storage){
            c.msgUpdate()
        }
    }

    override fun removeConnector(c: Observer) {
        storage.remove(c)
    }
}