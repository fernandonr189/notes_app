package com.example.notes.activities.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.activities.AddNoteScreen
import com.example.notes.activities.MainScreen
import com.example.notes.activities.adapters.MainScreenRecyclerAdapter
import com.example.notes.databinding.FragmentNotesBinding
import com.example.notes.models.ContextMenuCallback
import com.example.notes.models.FabClickCallback
import com.example.notes.models.Note
import com.example.notes.models.State

class NotesFragment : Fragment(), FabClickCallback, ContextMenuCallback {

    private var _binding: FragmentNotesBinding? = null
    private lateinit var recycler : RecyclerView
    private lateinit var recyclerAdapter: MainScreenRecyclerAdapter
    private lateinit var parentActivity : MainScreen
    private lateinit var emptyNotice : TextView
    private lateinit var notes : ArrayList<Note>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as MainScreen

        parentActivity.handleFabClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var state = State
        notes = state.notes

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        emptyNotice = _binding!!.emptyNotesNotice
        recycler = _binding!!.mainScreenRecycler
        recyclerAdapter = MainScreenRecyclerAdapter(notes, requireContext(), this)
        recycler.adapter = recyclerAdapter
        recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        if(notes.size > 0) {
            recycler.visibility = View.VISIBLE
            emptyNotice.visibility = View.INVISIBLE
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        recyclerAdapter.notifyDataSetChanged()
        if(notes.size > 0) {
            recycler.visibility = View.VISIBLE
            emptyNotice.visibility = View.INVISIBLE
        }
        super.onResume()
    }

    override fun onFabClick() {
        val intent = Intent(requireContext(), AddNoteScreen::class.java)
        startActivity(intent)
    }

    fun showAlertDialog(position : Int) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Advertencia")
            .setMessage("¿Está seguro de que desea eliminar esta nota?")
            .setNegativeButton("Cancelar") { view, _ ->
                Toast.makeText(requireContext(), "Accion cancelada", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setPositiveButton("Aceptar") { view, _ ->
                deleteNote(position)
                if(notes.size == 0) {
                    recycler.visibility = View.INVISIBLE
                    emptyNotice.visibility = View.VISIBLE
                }
                Toast.makeText(requireContext(), "Eliminado", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun deleteNote(position: Int) {
        State.notes.removeAt(position)
        recyclerAdapter.notifyItemRemoved(position)
        val json = State.toJson()
        val sharedpref = requireActivity().getSharedPreferences("State", Context.MODE_PRIVATE)
        with(sharedpref.edit()) {
            putString("State", json)
            apply()
        }
    }


    override fun onLongClick(menuItem: MenuItem, position : Int) : Boolean {
        when(menuItem.itemId) {
            R.id.edit_menu_item -> {
                val intent = Intent(requireContext(), AddNoteScreen::class.java)
                intent.putExtra("NoteIndex", position)
                startActivity(intent)
            }
            R.id.delete_menu_item -> {
                showAlertDialog(position)
            }
        }
        return true
    }
}