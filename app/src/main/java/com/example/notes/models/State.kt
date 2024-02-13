package com.example.notes.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

object State : Serializable{
    var notes : ArrayList<Note> = ArrayList()
    fun addNote(newNote : Note) {
        this.notes.add(newNote)
    }

    fun toJson() : String {
        var jsonStr = Gson().toJson(notes)
        return jsonStr
    }

    fun fromJson(json: String){
        val typeToken = object : TypeToken<List<Note>>() {}.type
        var newNotes = Gson().fromJson<List<Note>>(json, typeToken)
        notes = newNotes.toCollection(ArrayList())
    }
}