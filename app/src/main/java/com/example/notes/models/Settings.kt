package com.example.notes.models

import com.google.gson.Gson
import java.io.Serializable

object Settings : Serializable{
    var params : Params = Params(0F)

    fun toJson(): String {
        return Gson().toJson(params)
    }

    fun fromJson(json: String) {
        var newValue = Gson().fromJson<Params>(json, Params::class.java)
        this.params = newValue
    }
}