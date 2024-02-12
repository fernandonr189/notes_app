package com.example.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.notes.R
import com.google.android.material.textfield.TextInputEditText

class AddNoteScreen : AppCompatActivity() {
    
    private lateinit var titleText : TextView
    private lateinit var noteTitleEditText : TextInputEditText
    private lateinit var noteBodyTitleEditText: TextInputEditText
    private lateinit var saveButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note_screen)
        
        titleText = findViewById(R.id.add_note_screen_title)
        noteTitleEditText = findViewById(R.id.note_title_text_field)
        noteBodyTitleEditText = findViewById(R.id.note_body_text_field)
        saveButton = findViewById(R.id.save_note_button)
        
        saveButton.setOnClickListener {
            if(validateForm()) {
                saveNote()
            }
            else {
                Toast.makeText(this, "Por favor escriba algo!", Toast.LENGTH_SHORT).show()
            }
        }
        
    }
    
    private fun validateForm() : Boolean {
        if(noteTitleEditText.text.isNullOrEmpty() || noteBodyTitleEditText.text.isNullOrEmpty()) {
            return false
        }
        return true
    }    
    private fun saveNote() {

    }
}