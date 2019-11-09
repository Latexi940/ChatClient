package com.example.chatclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Creates main layout and handles the user input to MessageStorage-singleton
 * Also sets clients username as first message that is inputted by user.
 * Acts as Observer to ServerConnector and has method to update recycleView every time there is a new message.
 */


class MainActivity : AppCompatActivity(), Observer{

    var myAdapter = MyAdapter(MessageStorage.messages, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {
            Log.d("Log", "Error_:$e")
        }

        ServerConnector.addConnector(this)

        setContentView(R.layout.activity_main)

        messagesView.adapter = myAdapter
        messagesView.layoutManager = LinearLayoutManager(this)

        ServerConnector.run()

        var usernameSet = false

        button.setOnClickListener{

            if(!usernameSet){
                Log.d("Log", "Send button clicked")

                usernameSet = true

                var usernameTest = editText.text.toString()
                editText.text.clear()

                MessageStorage.newMessage(ChatMessage(usernameTest, MessageStorage.username))

                MessageStorage.username = usernameTest
                Log.d("Log", "Username set")
            }else{

                Log.d("Log", "Send button clicked")
                var msg = editText.text.toString()
                editText.text.clear()

                MessageStorage.newMessage(ChatMessage(msg, MessageStorage.username))
            }
        }
    }

    override fun msgUpdate() {

        runOnUiThread{myAdapter.notifyDataSetChanged()}
        Log.d("Log", "Recycelview updated")
    }

}
