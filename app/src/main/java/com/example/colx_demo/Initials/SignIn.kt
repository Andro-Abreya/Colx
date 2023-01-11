package com.example.colx_demo.Initials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.colx_demo.MainActivity
import com.example.colx_demo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class SignIn : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 100

    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar!!.hide()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        mAuth = FirebaseAuth.getInstance()

        val sign_in_btn = findViewById<TextView>(R.id.googleSigninbtn)
        sign_in_btn.setOnClickListener {
            signIn()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInClient.signOut();
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception =task.exception
            if(task.isSuccessful){

                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("MainActivity2", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("MainActivity2", "Google sign in failed", e)
                }
            }
            else{
                Log.w("MainActivity2",exception.toString())
            }

        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MainActivity2", "signInWithCredential:success")


                    val user = mAuth.currentUser
                    val email = user?.email
                    val l = email?.length
                    val domain = email?.substring((l?.minus(11)!!))

                    if(domain=="@nitj.ac.in"){



                        val name = user.uid

                        db = FirebaseFirestore.getInstance()
                        db.collection("Users").document("$name")
                            .get()
                            .addOnSuccessListener {

                                //Returns value of corresponding fields
                                val b = it["ProfileCreated"].toString()

                                if (b=="1") {

                                    val dashboardIntent = Intent(this, MainActivity::class.java)
                                    startActivity(dashboardIntent)
                                    finish()

                                }
                                else {

                                    val dashboardIntent = Intent(this,CreateProfile::class.java)
                                    startActivity(dashboardIntent)
                                    finish()
                                }
                            }

//                        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this,MainActivity::class.java)
//                        startActivity(intent)
//                        finish()

                    }else{

                        Toast.makeText(this,"Kindly login using official Email-id",Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                    }


                    //Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()


                }
                else {
                    // If sign in fails, display a message to the user
                    Log.w("MainActivity2", "signInWithCredential:failure", task.exception)
                }
            }

    }
}