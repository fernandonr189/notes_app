package com.example.notes

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.activities.MainScreen
import com.example.notes.models.Settings
import com.example.notes.models.State
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var userTextField : TextInputEditText
    private lateinit var passwordTextField : TextInputEditText
    private lateinit var loginButton : Button
    private lateinit var exitButton: Button
    private lateinit var rememberMeCheckbox : CheckBox

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userTextField = findViewById(R.id.user_text_field)
        passwordTextField = findViewById(R.id.password_text_field)

        loginButton = findViewById(R.id.login_button)
        exitButton = findViewById(R.id.exit_button)

        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox)

        exitButton.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            if(validateForm()){
                Toast.makeText(this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                if(rememberMeCheckbox.isChecked) {
                    saveLoginData()
                }
                else {
                    removeLoginData()
                }
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

        var sharedPrefSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        var SettingsJson = sharedPrefSettings.getString("Settings", Settings.toJson())
        Settings.fromJson(SettingsJson!!)
        print(Settings.params.fontSize)

        var sharedPrefLogin = this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        var username = sharedPrefLogin.getString("User", "")
        var password = sharedPrefLogin.getString("Password", "")

        userTextField.setText(username)
        passwordTextField.setText(password)
    }

    private fun removeLoginData() {
        val sharedPref = this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("User", "")
            putString("Password", "")
            apply()
        }
    }

    private fun saveLoginData() {
        val sharedPref = this.getSharedPreferences("Login", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("User", userTextField.text.toString())
            putString("Password", passwordTextField.text.toString())
            apply()
        }
    }

    private fun validateForm() : Boolean {
        return !(userTextField.text.isNullOrEmpty() || passwordTextField.text.isNullOrEmpty())
    }
}