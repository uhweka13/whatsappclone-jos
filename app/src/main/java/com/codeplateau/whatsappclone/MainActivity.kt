package com.codeplateau.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        var userVerify = mAuth!!.currentUser

        if (userVerify == null){
            setContentView(R.layout.activity_main)
        }else{

            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }


        tv_welcome_links.setText(Html.fromHtml("<p>Read our <a href=\"http://www.facebook.com\">Privacy Policy</a>. Tap Agree and continue to accept the <a href=\"http://www.facebook.com\">Terms of Service</a>.</p>"))
        tv_welcome_links.setMovementMethod(LinkMovementMethod.getInstance())

        tv_welcome_btn.setOnClickListener {
            val intent = Intent(this, PhoneVerification::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }
    }
}
