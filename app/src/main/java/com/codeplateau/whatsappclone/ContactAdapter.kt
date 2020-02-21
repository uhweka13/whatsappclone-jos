package com.codeplateau.whatsappclone

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val listContacts:ArrayList<ContactModel>, internal var context: Context) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_cards, parent, false)

        return ContactViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listContacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.textName.text = listContacts[position].name
        holder.textEmail.text = listContacts[position].number

        holder.itemView.setOnClickListener(View.OnClickListener {

            val i = Intent(context, MessageActivity::class.java)

            //pass the details of the user to the next activity

            i.putExtra("name", listContacts[position].name)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        })



    }


    inner class ContactViewHolder(view: View):RecyclerView.ViewHolder(view) {

        var textName: TextView
        var textEmail: TextView



        init {
            textName = view.findViewById<View>(R.id.tv_name_number) as TextView
            textEmail = view.findViewById<View>(R.id.tv_status_contacts) as TextView

        }


    }

}