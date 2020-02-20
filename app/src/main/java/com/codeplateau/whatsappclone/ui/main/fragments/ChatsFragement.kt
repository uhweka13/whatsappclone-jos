package com.codeplateau.whatsappclone.ui.main.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codeplateau.whatsappclone.ContactActivity
import com.codeplateau.whatsappclone.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ChatsFragement : Fragment() {

    lateinit var chats_view:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chats_view = inflater.inflate(R.layout.fragment_chats_fragement, container, false)

        val fab: FloatingActionButton = chats_view.findViewById(R.id.fab)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            val intent = Intent(this.context, ContactActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


        }

        return chats_view
    }


}
