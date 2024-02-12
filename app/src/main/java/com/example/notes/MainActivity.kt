package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {



    private lateinit var userTextField : TextInputEditText
    private lateinit var passwordTextField : TextInputEditText
    private lateinit var loginButton : Button
    private lateinit var exitButton: Button

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
            }
            else {
                Toast.makeText(this, "Por favor, llene los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm() : Boolean {
        return !(userTextField.text.isNullOrEmpty() || passwordTextField.text.isNullOrEmpty())
    }
}