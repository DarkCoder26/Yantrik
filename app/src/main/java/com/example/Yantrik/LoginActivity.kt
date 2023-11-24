package com.example.Yantrik

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Yantrik.Model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val click_to_login = findViewById<TextView>(R.id.click_to_register)
        val loginButton = findViewById<Button>(R.id.login_button)
        val editTextEmail = findViewById<EditText>(R.id.email)
        val editTextPassword = findViewById<EditText>(R.id.password)

        loginButton.setOnClickListener {

            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(this, "Please Enter All Details!!", Toast.LENGTH_SHORT).show()
            }
            else{
                val user= User(email, password)
//
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this,
                            it.exception?.localizedMessage,
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        click_to_login.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}