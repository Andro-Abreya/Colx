package com.example.colx_demo.add_product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.colx_demo.MainActivity
import com.example.colx_demo.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddProduct : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val uid = currentUser?.uid

        val category = intent.getStringExtra("Category")
        var subCategory = intent.getStringExtra("Sub Category")
        if(subCategory == null)
        {
            subCategory = "Books"
        }

        val name = findViewById<TextInputEditText>(R.id.titleEdt)
        val price = findViewById<TextInputEditText>(R.id.priceEdt)
        val upiId = findViewById<TextInputEditText>(R.id.upiIdEdt)
        val description = findViewById<TextInputEditText>(R.id.descriptionEdt)
        val saveBtn = findViewById<TextView>(R.id.button)

        saveBtn.setOnClickListener {

            if (TextUtils.isEmpty(name.text?.trim().toString())) {
                name.error = "Field cannot be empty"
                name.requestFocus()
            }else  if (TextUtils.isEmpty(price.text?.trim().toString())) {
                price.error = "Field cannot be empty"
                price.requestFocus()
            }
            else  if (TextUtils.isEmpty(upiId.text?.trim().toString())) {
                upiId.error = "Field cannot be empty"
                upiId.requestFocus()
            }
            else{

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                val formatted = current.format(formatter)

                val data= hashMapOf(
                    "Name" to name.text?.trim().toString(),
                    "Price" to price.text?.trim().toString(),
                    "Upi Id" to upiId.text?.trim().toString(),
                    "Description" to description.text?.trim().toString(),
                    "Date Posted" to formatted.toString()
                )
                db.collection("Products").document("$category").collection("$subCategory").document("$uid+$formatted")
                    .set(data)
                    .addOnSuccessListener {docRef ->
                        Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                        Toast.makeText(this,"Product Posted", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        }
    }