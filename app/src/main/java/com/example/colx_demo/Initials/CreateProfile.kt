package com.example.colx_demo.Initials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.colx_demo.MainActivity
import com.example.colx_demo.R
import com.example.colx_demo.databinding.ActivityCreateProfileBinding
import com.example.colx_demo.databinding.ActivitySplashScreenBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCreateProfileBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val uid = currentUser?.uid

        val year_set= resources.getStringArray(R.array.year)
        val arrayAdapterYear = ArrayAdapter(this,R.layout.dropdown_item_year,year_set)
        binding.autoCompleteYear.setAdapter(arrayAdapterYear)

        val branch_set= resources.getStringArray(R.array.branch)
        val arrayAdapterBranch = ArrayAdapter(this,R.layout.item_branch,branch_set)
        binding.autoCompleteBranch.setAdapter(arrayAdapterBranch)

        val name=findViewById<TextInputEditText>(R.id.name_text)
        val roll =findViewById<TextInputEditText>(R.id.roll_text)
        val year=findViewById<AutoCompleteTextView>(R.id.autoCompleteYear)
        val branch=findViewById<AutoCompleteTextView>(R.id.autoCompleteBranch)
        val phoneNo=findViewById<TextInputEditText>(R.id.phoneno_text)



        name?.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 30){
                name.error ="Just enter your first and last name "
            } else if(text.length<30){
                name.error = null
            }
        }
        roll?.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 30){
                roll.error ="Too long roll no. "
            } else if(text.length<30){
                roll.error = null
            }
        }

        phoneNo?.doOnTextChanged { text, start, before, count ->
            if(text!!.length !=10){
                phoneNo.error ="Invalid mobile no."
            }
        }




        binding.btnSignup.setOnClickListener {



            if (TextUtils.isEmpty(name.getText()?.trim().toString())) {
                name.error = "Field cannot be empty"
                name.requestFocus()
            }else  if (TextUtils.isEmpty(roll.getText()?.trim().toString())) {
                roll.error = "Field cannot be empty"
                roll.requestFocus()
            }
            else  if (TextUtils.isEmpty(phoneNo.getText()?.trim().toString())) {
                phoneNo.error = "Field cannot be empty"
                phoneNo.requestFocus()
            }
            else{



                val data= hashMapOf(
                    "Name" to name.text?.trim().toString(),
                    "Roll No" to roll.text?.trim().toString(),
                    "Year" to year.text?.trim().toString(),
                    "Branch" to branch.text?.trim().toString(),
                    "Phone No" to phoneNo.text?.trim().toString(),
                    "ProfileCreated" to "1"

                )
                db.collection("Users").document("$uid")
                    .set(data)
                    .addOnSuccessListener {docRef ->
                        Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                        Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }

//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
            }
        }

        binding.NextBtn.setOnClickListener {

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }


}