package com.codeplateau.whatsappclone.countries

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codeplateau.whatsappclone.PhoneVerification
import com.codeplateau.whatsappclone.model.Countrys
import android.R



class CountriesRecylerAdapter(private val listcountries: List<Countrys>, internal var context: Context) : RecyclerView.Adapter<CountriesRecylerAdapter.CountriesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        //inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(com.codeplateau.whatsappclone.R.layout.countries_card, parent, false)

        return CountriesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listcountries.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.textName.text = listcountries[position].name
        holder.textCode.text = listcountries[position].code



//        set onclick listener on a user's data
        holder.itemView.setOnClickListener(View.OnClickListener {

            val i = Intent(context, PhoneVerification::class.java)

            //pass the details of the user to the next activity

            i.putExtra("name", listcountries[position].name)
            i.putExtra("code",listcountries[position].code)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
            (context as Activity).finish()





        })
    }

    class CountriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textName: TextView
        var textCode: TextView

        init {
            textName = view.findViewById<View>(com.codeplateau.whatsappclone.R.id.tv_country_name) as TextView
            textCode = view.findViewById<View>(com.codeplateau.whatsappclone.R.id.tv_country_code) as TextView
        }

    }

}