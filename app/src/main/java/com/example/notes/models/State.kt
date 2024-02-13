package com.example.notes.models

object State {
    var notes : ArrayList<Note> = ArrayList()
    fun addNote(newNote : Note) {
        this.notes.add(newNote)
    }
}