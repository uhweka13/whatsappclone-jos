package com.codeplateau.whatsappclone

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : AppCompatActivity() {

    private lateinit var allUsersRecycler: RecyclerView
    private lateinit var listContacts : ArrayList<ContactModel>
    private lateinit var recyclerAdapter : ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val toolbar = findViewById(R.id.message_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true)

        allUsersRecycler = all_contact_recycler
        listContacts = ArrayList()
        recyclerAdapter = ContactAdapter(listContacts, this)

        //val mLayoutManager = LinearLayoutManager(this)
        allUsersRecycler.layoutManager = LinearLayoutManager(this)

        allUsersRecycler.setHasFixedSize(true)


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    Companion.MY_PERMISSIONS_REQUEST_READ_CONTACTS
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }





        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val contactModel = ContactModel()
            contactModel.setNames(name)
            contactModel.setNumbers(phoneNumber)
            listContacts!!.add(contactModel)

            var contactsCount = listContacts.size
            tv_contact_count.setText("$contactsCount contacts")
            Log.d("name>>", name + "  " + phoneNumber)
        }
        phones.close()

        recyclerAdapter = ContactAdapter(listContacts!!, this)
        allUsersRecycler!!.adapter = recyclerAdapter

    }




    // inflates the menu for MessageActivity activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.contacts_menu, menu)

        return true
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10
    }
}
