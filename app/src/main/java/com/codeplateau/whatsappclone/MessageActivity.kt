package com.codeplateau.whatsappclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val toolbar = findViewById(R.id.message_toolbar) as Toolbar
        setSupportActionBar(toolbar)


        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)

        imbtn_voice.setOnClickListener{
            Log.i("wewe", "ewewew")
        }

        tv_message.addTextChangedListener{
            var textInput = tv_message.text.toString().trim()
            if(textInput.length < 1){
                imgCam_message.visibility = View.VISIBLE
                imbtn_send_chat.visibility = View.GONE
                imbtn_voice.visibility = View.VISIBLE
            }else{
                imgCam_message.visibility = View.GONE
                imbtn_send_chat.visibility = View.VISIBLE
                imbtn_voice.visibility = View.GONE
            }

        }

        val name = intent.getStringExtra("name")
        tv_chats_name.setText("$name")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // inflates the menu for MessageActivity activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.message_menu, menu)

        return true
    }
}
