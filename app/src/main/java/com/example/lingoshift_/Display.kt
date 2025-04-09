package com.example.lingoshift_

import FragmentActionListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Display(val recognizedText: String?): Fragment() {

    private lateinit var fragmentActionListener: FragmentActionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentActionListener = context as FragmentActionListener  // Cast the context to the interface
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement FragmentActionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.display, container, false)

        val tvDisplayOutput = view.findViewById<TextView>(R.id.tvDisplayOutput)
        tvDisplayOutput.text = recognizedText
        val btnCloseDisplay = view.findViewById<Button>(R.id.btnCloseDisplay)
        btnCloseDisplay.setOnClickListener {
            fragmentActionListener.onCloseFragment()  // Call the interface method to inform MainActivity
        }


        return view
    }
}