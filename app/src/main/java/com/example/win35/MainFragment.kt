package com.example.win35

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainFragment : Fragment() {

    lateinit var nController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        requireActivity().findViewById<AppCompatButton>(R.id.button_start).setOnClickListener {
            nController.navigate(R.id.action_mainFragment_to_quizFragment)
        }
    }
}