package com.example.notes.models

import java.io.Serializable
import java.util.Date

class Note(var content: String, var title : String, var date : Date) : Serializable {

}