package com.codeplateau.whatsappclone


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kaopiz.kprogresshud.KProgressHUD
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.android.synthetic.main.activity_phone_verification.*
import kotlinx.android.synthetic.main.customdialog.view.*
import kotlinx.android.synthetic.main.errordialog.view.*
import kotlinx.android.synthetic.main.pincoderecieve.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class PhoneVerification : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var hud: KProgressHUD
    private var countryCodeSelected: String? = ""
    private var countrySelected: String = ""
    private var userPhoneNumber: String = ""


//    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        var userVerify = mAuth!!.currentUser

        if (userVerify == null){
            setContentView(R.layout.activity_phone_verification)
        }else{

            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }




        val toolbar = findViewById(R.id.verify_toolbar) as Toolbar
        setSupportActionBar(toolbar)


//        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork:NetworkInfo? = cm.activeNetworkInfo
//        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true
//
//        Log.i("netwotk", "$activeNetwork")





        hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Creating Post")
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        // handle onchange event of the country picker library
        ccp.setOnCountryChangeListener(object:CountryCodePicker.OnCountryChangeListener {
            override fun onCountrySelected(selectedCountry: Country) {
                countryCodeSelected = selectedCountry.phoneCode
                countrySelected = selectedCountry.name
                tv_selected_code.setText(countryCodeSelected)
                Toast.makeText(this@PhoneVerification, "$countryCodeSelected", Toast.LENGTH_SHORT).show()
            }
        })


        // initialize the country picker with default value
        countryCodeSelected = ccp.selectedCountryCode
        tv_selected_code.setText(countryCodeSelected)
        countrySelected = ccp.selectedCountryNameCode

        // text with links below the next button
//        tv_text1.setText(Html.fromHtml("<p>You must be <a href=\"http://www.facebook.com\">at least 16 years old</a> to register. Learn how WhatsApp works with the <a href=\"http://www.facebook.com\">Facebook Companies.</a></p>", Html.FROM_HTML_MODE_COMPACT))
//        tv_text1.setMovementMethod(LinkMovementMethod.getInstance())

//        auth.setLanguageCode(Locale.getDefault().language)

        // next button onclick listener for initiating code sending
        bt_send_code.setOnClickListener {

//            pincodeReciver()

            var tvNumber = tv_phone_number.text.toString().trim()
            var tvCode = tv_selected_code.text.toString().trim()

            if(tvCode.isEmpty()){
                errorMessage("Phone code is invalid")

            }
            else if(tvNumber.isEmpty()){
                errorMessage("Please enter your phone number")
            }
            else{
                userPhoneNumber = tvNumber

                customDialog()

            }


        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            //runs only when the code recived was auto detected by google play service
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                hud.dismiss()
//                Log.i("value", "onVerificationCompleted:$credential")


                signInWithPhoneAuthCredential(credential)
            }

            // runs when code sending fails
            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.i("value", "onVerificationFailed", e)
                Toast.makeText(this@PhoneVerification, "$e", Toast.LENGTH_LONG).show()
                hud.dismiss()

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            // runs only when the six digit code was sent successfully from firebase
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.i("value", "onCodeSent:$verificationId")
                hud.dismiss()
                pincodeReciver()

                Toast.makeText(this@PhoneVerification, "worked", Toast.LENGTH_LONG).show()

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                // ...
            }
        }

    }

    // inflates the menu for phonevarification activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.verify_menu, menu)

        return true
    }


   fun sendVerificationCode(phoneNumber:String){
       Toast.makeText(this@PhoneVerification, "clicked", Toast.LENGTH_LONG).show()

       hud.show()

       PhoneAuthProvider.getInstance().verifyPhoneNumber(
           phoneNumber, // Phone number to verify
           60, // Timeout duration
           TimeUnit.SECONDS, // Unit of timeout
           this, // Activity (for callback binding)
           callbacks) // OnVerificationStateChangedCallbacks

   }



    // custom dialog for phone number confirmation. Accepts two parameters phonenumber and country code
    fun customDialog(){

            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.customdialog, null)
                mDialogView.tv_phone_number_picked.setText("+$countryCodeSelected$userPhoneNumber")
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            //show dialog
            val  mAlertDialog = mBuilder.show()

            //custom dialog OK button
            mDialogView.bt_ok.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()

                //function for sending the 6 digit code
                sendVerificationCode("+$countryCodeSelected$userPhoneNumber")
            }

            // custom dialog edit button
            mDialogView.bt_edit.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
    }

    // function that handles error messages
    fun errorMessage(errorMessage:String){
            //Inflate the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.errordialog, null)
            mDialogView.tv_error_message.setText("$errorMessage")
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            //show dialog
            val  mAlertDialog = mBuilder.show()

            // button to close the dialog
            mDialogView.bt_close_error_message.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
    }

    // signs in user after phone number is verified using the code recieved
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth = FirebaseAuth.getInstance()

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

//                    val user = task.result?.user



                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()

                } else {
                    // Sign in failed, display a message and update the UI


                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        errorMessage("The code you entered is incorrect. Please try again later")
                    }
                }
            }
    }

    // handles pin reception incase the phone fails to auto retrive code sent from firebase
    fun pincodeReciver(){
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.pincoderecieve, null)

        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this, R.style.DialogTheme)
            .setView(mDialogView)
        //show dialog
        val  mAlertDialog = mBuilder.show()

        mDialogView.et_first_num.addTextChangedListener{
           var pinCodeFinal = mDialogView.et_first_num.text.toString().trim()

            if (pinCodeFinal.length > 5 ){

                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, pinCodeFinal)

                signInWithPhoneAuthCredential(credential)
            }

        }

        var numberFinal = "+$countryCodeSelected$userPhoneNumber"
        mDialogView.tv_code_text_1.setText("Verify $numberFinal")
        mDialogView.tv_phone_number_on_pin_code.setText(Html.fromHtml("waiting to automatically detect an SMS sent to <b>$numberFinal</b>."))
        // custom menu button
        mDialogView.imgbt_menu_code.setOnClickListener {
            //dismiss dialog
            mDialogView.imgbt_menu_code.visibility = View.GONE
            mDialogView.cd_help_menu.visibility = View.VISIBLE
        }
    }
}
