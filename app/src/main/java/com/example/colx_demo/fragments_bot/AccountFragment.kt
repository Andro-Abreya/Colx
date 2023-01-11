package com.example.colx_demo.fragments_bot

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.colx_demo.Initials.SignIn
import com.example.colx_demo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match

class AccountFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =inflater.inflate(R.layout.fragment_account, container, false)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        val btn_Signout = view.findViewById<TextView>(R.id.Signout)

        btn_Signout.setOnClickListener {

            mAuth.signOut()
            val intent = Intent(context, SignIn::class.java)
            startActivity(intent)


        }

        return view
    }

    companion object {


                }
}

