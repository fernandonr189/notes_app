package com.example.notes.activities.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notes.activities.MainScreen
import com.example.notes.databinding.FragmentGalleryBinding
import com.example.notes.models.FabClickCallback
import com.example.notes.models.Settings

class SettingsFragment : Fragment(), FabClickCallback {

    private var _binding: FragmentGalleryBinding? = null
    private lateinit var parentActivity : MainScreen
    private lateinit var gridSwitch : Switch

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var fontSize : Float = if(Settings.params.fontSize == 0F) 12F else Settings.params.fontSize
    private val isGrid : Boolean = Settings.params.isGridActive

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as MainScreen

        parentActivity.handleFabClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.fontSizeSpinner
        gridSwitch = binding.gridSwitch

        gridSwitch.isChecked = isGrid

        gridSwitch.setOnClickListener {
            if(gridSwitch.isChecked) {
                changeGridState(true)
            }
            else {
                changeGridState(false)
            }
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("12", "13", "14", "15", "16", "17", "18")
        )
        spinner.adapter = adapter

        var selection = 0
        when(fontSize) {
            12F -> selection = 0
            13F -> selection = 1
            14F -> selection = 2
            15F -> selection = 3
            16F -> selection = 4
            17F -> selection = 5
            18F -> selection = 6
        }

        spinner.setSelection(selection)

        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> fontSize = 12F
                    1 -> fontSize = 13F
                    2 -> fontSize = 14F
                    3 -> fontSize = 15F
                    4 -> fontSize = 16F
                    5 -> fontSize = 17F
                    6 -> fontSize = 18F
                }
                saveFontSizeSettings(fontSize)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Selecciona una opcion", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun changeGridState(state: Boolean) {
        Settings.params.isGridActive = state
        val json = Settings.toJson()
        val sharedpref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        with(sharedpref.edit()) {
            putString("Settings", json)
            apply()
        }
    }

    private fun saveFontSizeSettings(size : Float) {
        Settings.params.fontSize = size
        val json = Settings.toJson()
        val sharedpref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        with(sharedpref.edit()) {
            putString("Settings", json)
            apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFabClick() {
        Toast.makeText(requireContext(), "Gallery", Toast.LENGTH_SHORT).show()
    }
}