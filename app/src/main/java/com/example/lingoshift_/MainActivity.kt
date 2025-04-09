package com.example.lingoshift_

import FragmentActionListener
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() , FragmentActionListener {

    private lateinit var lingoshiftAdapter: LingoShiftAdapter

    val sharedPreferences by lazy {
        getSharedPreferences("LingoShift_prefs", Context.MODE_PRIVATE)
    }


    fun retrieveSavedItems(): ArrayList<LingoShift> {
        val savedItems = ArrayList<LingoShift>()
        val allKeys = sharedPreferences.all.keys

        for (key in allKeys) {
            if (key!!.startsWith("title_")) {
                val title = key.substringAfter("_")
                val label = sharedPreferences.getString(key, "") ?: ""
                savedItems.add(LingoShift(title, label))
            }
        }
        return savedItems
    }

    fun scrollToNewItem(position: Int){
        val rvTextItems = findViewById<RecyclerView>(R.id.rvTextItems)
        val layoutManager = rvTextItems.layoutManager as LinearLayoutManager  // Assuming LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(position, 0)  // Scroll to start
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        MobileAds.initialize(this) {}

        val AdViewBottom = findViewById<AdView>(R.id.AdViewBottom)
        val adRequest = AdRequest.Builder().build()

        AdViewBottom.loadAd(adRequest)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        val rvTextItems = findViewById<RecyclerView>(R.id.rvTextItems)
        val savedItems = retrieveSavedItems()  // Get saved items (optional)
        lingoshiftAdapter = LingoShiftAdapter(this, savedItems ?: ArrayList(), rvTextItems)
        rvTextItems.adapter = lingoshiftAdapter
        rvTextItems.layoutManager = LinearLayoutManager(this)

        val btnAddTextInput = findViewById<Button>(R.id.btnAddTextInput)
        btnAddTextInput.setOnClickListener {
            val etInputText = findViewById<EditText>(R.id.etInputText)
            val InputText = etInputText.text.toString()
            if (InputText.isNotEmpty()) {
                val OutputText = lingoshiftAdapter.terminal(InputText)
                val item = LingoShift(OutputText)

                val position = lingoshiftAdapter.addText(item)
                etInputText.text.clear()
                scrollToNewItem(position)
            }
        }

        val ibFind = findViewById<ImageButton>(R.id.ibFind)
        ibFind.setOnClickListener {
            val etInputText = findViewById<EditText>(R.id.etInputText)
            val searchText = etInputText.text.toString().trim()  // Get search text

            if (searchText.isEmpty()) {
                // Handle empty search case (optional: show a message)
                Toast.makeText(this, "Enter Label To Search!", Toast.LENGTH_SHORT).show()            }

            val allItems = retrieveSavedItems()  // Get all saved items

            val matchingItem = allItems.find { it.label.lowercase().contains(searchText.lowercase()) }
            // Find item where label (case-insensitive) contains search text

            if (matchingItem != null) {
                val position = allItems.indexOf(matchingItem)  // Get position of matching item
                val layoutManager = rvTextItems.layoutManager as LinearLayoutManager  // Assuming LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(position, 0)  // Scroll to start
            } else {
                // Handle no matching item case (optional: show a message)
                Toast.makeText(this, "Label Not Found!", Toast.LENGTH_SHORT).show()
            }
        }


    }

        override fun onCloseFragment() {
            supportFragmentManager.popBackStack()
            val frameLayout = findViewById<FrameLayout>(R.id.fragmentContainer)
            frameLayout.background = null
        }

    }