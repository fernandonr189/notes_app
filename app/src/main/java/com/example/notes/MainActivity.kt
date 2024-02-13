package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.notes.activities.MainScreen
import com.example.notes.models.Note
import com.example.notes.models.State
import com.google.android.material.textfield.TextInputEditText
import java.time.Instant
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var userTextField : TextInputEditText
    private lateinit var passwordTextField : TextInputEditText
    private lateinit var loginButton : Button
    private lateinit var exitButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userTextField = findViewById(R.id.user_text_field)
        passwordTextField = findViewById(R.id.password_text_field)

        loginButton = findViewById(R.id.login_button)
        exitButton = findViewById(R.id.exit_button)

        exitButton.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            if(validateForm()){
                Toast.makeText(this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Por favor, llene los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        var sharedPref = this.getSharedPreferences("State", Context.MODE_PRIVATE)
        var StateJson = sharedPref.getString("State", State.toJson())
        State.fromJson(StateJson!!)
        print("Hello")
    }

    private fun validateForm() : Boolean {
        return !(userTextField.text.isNullOrEmpty() || passwordTextField.text.isNullOrEmpty())
    }
}