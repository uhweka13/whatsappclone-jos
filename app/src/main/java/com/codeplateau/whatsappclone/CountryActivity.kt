package com.codeplateau.whatsappclone

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeplateau.whatsappclone.countries.CountriesRecylerAdapter
import com.codeplateau.whatsappclone.model.Countrys
import com.rilixtech.widget.countrycodepicker.Country

class CountryActivity : AppCompatActivity() {

    private lateinit var allCountriesRecycler: RecyclerView
    private var listView : MutableList<com.codeplateau.whatsappclone.model.Countrys> = ArrayList()
    private lateinit var recyclerAdapter : CountriesRecylerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        // toolbar config
        val toolbar = findViewById(R.id.country_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_arrow)
        supportActionBar!!.setDisplayShowCustomEnabled(true)


//        val actionBar = supportActionBar
//        actionBar!!.title = "Choose a country"
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true)
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true)
//        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
//        actionBar.setNavi




        //data process
        allCountriesRecycler = findViewById(R.id.all_country_recycler)
        recyclerAdapter = CountriesRecylerAdapter(listView, this)

        //val mLayoutManager = LinearLayoutManager(this)
        allCountriesRecycler.layoutManager = LinearLayoutManager(this)

        allCountriesRecycler.setHasFixedSize(true)

        //links your recycler adapter class to the the recyclerview on your xml file
        allCountriesRecycler.adapter =recyclerAdapter

        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("ewewwewew", "555"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))
        listView.add(Countrys("Nigeria", "234"))




    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.phone_number, menu)

        return true
    }


}
