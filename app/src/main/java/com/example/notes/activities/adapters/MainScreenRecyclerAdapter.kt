package com.example.notes.activities.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.models.Note

class MainScreenRecyclerAdapter(notes: MutableList<Note>, context: Context) :
    RecyclerView.Adapter<MainScreenRecyclerAdapter.ViewHolder>() {

    private val _notes: MutableList<Note> = notes
    private val _context: Context = context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteTitle = view.findViewById(R.id.note_title) as TextView
        private val noteContent = view.findViewById(R.id.note_content_preview) as TextView

        fun bind(notes : Note, context: Context) {
            noteTitle.text = notes.title
            noteContent.text = if (notes.content.length > 100) {
                "${ notes.content.substring(0, 100)}..."
            } else notes.content
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainScreenRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.main_screen_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainScreenRecyclerAdapter.ViewHolder, position: Int) {
        val item = this._notes[position]
        holder.bind(item, _context)
    }

    override fun getItemCount(): Int {
        return _notes.size
    }

}