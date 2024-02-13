package com.example.notes.activities.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.activities.AddNoteScreen
import com.example.notes.activities.MainScreen
import com.example.notes.activities.adapters.MainScreenRecyclerAdapter
import com.example.notes.databinding.FragmentNotesBinding
import com.example.notes.models.FabClickCallback
import com.example.notes.models.Note
import com.example.notes.models.State

class NotesFragment : Fragment(), FabClickCallback {

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
        recyclerAdapter = MainScreenRecyclerAdapter(notes, requireContext())
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

    override fun onResume() {
        recyclerAdapter.notifyItemInserted(State.notes.size)
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
}