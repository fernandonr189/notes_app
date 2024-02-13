package com.example.notes.models

import android.view.MenuItem

interface ContextMenuCallback {
    fun onLongClick(menuItem : MenuItem, position: Int) : Boolean
}