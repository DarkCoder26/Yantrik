
package com.example.Yantrik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Yantrik.Model.User
import com.example.Yantrik.Utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class RegistrationPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: User

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "User do not Exist.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        val click_to_login = findViewById<TextView>(R.id.click_to_login)
        val registerButton = findViewById<Button>(R.id.register_button)
        val editTextUsername = findViewById<EditText>(R.id.username)
        val editTextEmail = findViewById<EditText>(R.id.email)
        val editTextPassword = findViewById<EditText>(R.id.username)

        auth = Firebase.auth
        user = User()

        registerButton.setOnClickListener {

            var username = editTextUsername.text.toString()
            var email = editTextEmail.text.toString()
            var password = editTextPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please Enter All Details!!", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            user.username = username
                            user.email = email
                            user.password = password
                            

                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(
                                        this,
                                        "Account Created.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()

                                }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("FirebaseAuth", "Authentication failed: ${task.exception}")
                            Toast.makeText(
                                baseContext,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }

        click_to_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}