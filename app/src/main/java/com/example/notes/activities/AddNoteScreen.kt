package com.example.notes.activities

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.models.Note
import com.example.notes.models.State
import com.google.android.material.textfield.TextInputEditText
import java.time.Instant
import java.util.Calendar
import java.util.Date

class AddNoteScreen : AppCompatActivity() {
    
    private lateinit var titleText : TextView
    private lateinit var dateText : TextView
    private lateinit var noteTitleEditText : TextInputEditText
    private lateinit var noteBodyTitleEditText: TextInputEditText
    private lateinit var saveButton : Button
    private var noteIndex : Int? = null
    private lateinit var selectDateButton : Button
    private var dateStr : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note_screen)

        noteIndex = intent.getIntExtra("NoteIndex", -1)
        titleText = findViewById(R.id.add_note_screen_title)
        noteTitleEditText = findViewById(R.id.note_title_text_field)
        noteBodyTitleEditText = findViewById(R.id.note_body_text_field)
        saveButton = findViewById(R.id.save_note_button)
        selectDateButton = findViewById(R.id.select_date_button)
        dateText = findViewById(R.id.date_text)

        if(noteIndex != -1){
            fillForm()
        }

        selectDateButton.setOnClickListener {
            showDatePicker()
        }

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

    private fun showDatePicker() {
        val time = Calendar.getInstance()
        val year = time.get(Calendar.YEAR)
        val month = time.get(Calendar.MONTH)
        val day = time.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->  setLabel("$dayOfMonth/$month/$year")}, year, month, day)
        datePicker.show()
    }

    private fun setLabel(date : String) {
        dateStr = date
        dateText.text = dateStr
    }

    private fun fillForm() {
        noteTitleEditText.setText(State.notes[noteIndex!!].title)
        noteBodyTitleEditText.setText(State.notes[noteIndex!!].content)
    }
    
    private fun validateForm() : Boolean {
        return !(noteTitleEditText.text.isNullOrEmpty() || noteBodyTitleEditText.text.isNullOrEmpty())
    }    
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {
        if(noteIndex == -1) {
            State.addNote(Note(noteBodyTitleEditText.text.toString(), noteTitleEditText.text.toString(), Date.from(Instant.now())))
        }
        else {
            State.notes[noteIndex!!].title = noteTitleEditText.text.toString()
            State.notes[noteIndex!!].content = noteBodyTitleEditText.text.toString()
        }

        val json = State.toJson()
        val sharedpref = this.getSharedPreferences("State", Context.MODE_PRIVATE)
        with(sharedpref.edit()) {
            putString("State", json)
            apply()
        }
    }
}