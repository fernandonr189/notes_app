package com.example.notes.activities.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.activities.adapters.MainScreenRecyclerAdapter
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.models.Note
import kotlinx.coroutines.NonDisposableHandle.parent
import java.time.Instant
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recycler : RecyclerView
    private lateinit var recyclerAdapter: MainScreenRecyclerAdapter
    private var notes : MutableList<Note> = ArrayList()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notes.add(Note("lorem ipsum sjpdofijaspdofm soidjfposid sadfhpsoai oij", "Note 1", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))
        notes.add(Note("Hell0", "GHeeehosiufhaposidf", Date.from(Instant.now())))



        recycler = _binding!!.mainScreenRecycler
        recyclerAdapter = MainScreenRecyclerAdapter(notes, requireContext())
        recycler.adapter = recyclerAdapter
        recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}