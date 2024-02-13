package com.example.notes.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.notes.R
import com.example.notes.models.Note
import com.example.notes.models.State
import com.google.android.material.textfield.TextInputEditText
import java.time.Instant
import java.util.Date

class AddNoteScreen : AppCompatActivity() {
    
    private lateinit var titleText : TextView
    private lateinit var noteTitleEditText : TextInputEditText
    private lateinit var noteBodyTitleEditText: TextInputEditText
    private lateinit var saveButton : Button
    @RequiresApi(Build.VERSION_CODES.O)
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
                finish()
            }
            else {
                Toast.makeText(this, "Por favor escriba algo!", Toast.LENGTH_SHORT).show()
            }
        }
        
    }
    
    private fun validateForm() : Boolean {
        return !(noteTitleEditText.text.isNullOrEmpty() || noteBodyTitleEditText.text.isNullOrEmpty())
    }    
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {
        State.notes.add(Note(noteBodyTitleEditText.text.toString(), noteTitleEditText.text.toString(), Date.from(Instant.now())))
    }
}