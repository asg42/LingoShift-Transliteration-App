package com.example.lingoshift_

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.health.connect.datatypes.units.Length
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors.getColor


class LingoShiftAdapter (
    private val mainActivity: MainActivity,
    private val items: ArrayList<LingoShift>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<LingoShiftAdapter.LingoShiftViewHolder>(){
    class LingoShiftViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTextTitle: TextView = itemView.findViewById(R.id.tvTextTitle)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ibDelete)
        val ibCopy: ImageButton = itemView.findViewById(R.id.ibCopy)
        val ibTick: ImageButton = itemView.findViewById(R.id.ibTick)
        val etLabel: EditText = itemView.findViewById(R.id.etLabel)
        val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        val ibEdit: ImageButton = itemView.findViewById(R.id.ibEdit)
        var Labels: MutableList<String> = mutableListOf()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LingoShiftViewHolder {
        return LingoShiftViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_text,
                parent,
                false
            )
        )
    }


    fun addText(item:LingoShift): Int{
        items.add(item)
        notifyItemInserted(items.size - 1)
        return (items.size - 1)
    }

    fun terminal(input:String): String {
            val text = input

            val letters: MutableMap<String, String> = mutableMapOf(
                "अ" to "A",
                "आ" to "AA",
                "इ" to "E",
                "ई" to "I",
                "उ" to "U",
                "ऊ" to "OO",
                "ए" to "E",
                "ऐ" to "AE",
                "ओ" to "OO",
                "औ" to "AU",
                "अं" to "AM",
                "अः" to "A:",
                "ऋ" to "RI",
                "ा" to "A",
                "ि" to "I",
                "ी" to "II",
                "ु" to "U",
                "ू" to "UU",
                "ृ" to "R",
                "ॄ" to "RR",
                "ॅ" to "E",
                "ॆ" to "E",
                "े" to "E",
                "ै" to "AI",
                "ॉ" to "O",
                "ॊ" to "O",
                "ो" to "O",
                "ौ" to "AU",
                "ँ" to "",
                "ं" to "N",
                "्" to "",
                "़" to "",
                "क" to "K",
                "ख" to "KH",
                "ग" to "G",
                "घ" to "GH",
                "ङ" to "NGA",
                "च" to "CH",
                "छ" to "CHH",
                "ज" to "J",
                "झ" to "JH",
                "ञ" to "NYA",
                "ट" to "T",
                "ठ" to "THH",
                "ड" to "D",
                "ढ" to "DH",
                "ण" to "N",
                "त" to "T",
                "थ" to "THH",
                "द" to "D",
                "ध" to "DH",
                "न" to "N",
                "प" to "P",
                "फ" to "F",
                "ब" to "B",
                "भ" to "BH",
                "म" to "M",
                "य" to "Y",
                "र" to "R",
                "ल" to "L",
                "व" to "V",
                "श" to "SH",
                "ष" to "SH",
                "स" to "S",
                "ह" to "H",
                "क्ष" to "KSH",
                "त्र" to "TR",
                "ज्ञ" to "GYA",
                "क़" to "Q",
                "ख़" to "X",
                "ग़" to "Y",
                "ज़" to "Z",
                "ड़" to "R",
                "ढ़" to "RH",
                "फ़" to "F",
                "ऱ" to "R",
                "।" to ".",
                "॥" to ".",
                "०" to "0",
                "१" to "1",
                "२" to "2",
                "३" to "3",
                "४" to "4",
                "५" to "5",
                "६" to "6",
                "७" to "7",
                "८" to "8",
                "९" to "9",

                "ক" to "K",
                "খ" to "KH",
                "গ" to "G",
                "ঘ" to "GH",
                "ঙ" to "N",
                "চ" to "CH",
                "ছ" to "CHH",
                "জ" to "J",
                "ঝ" to "JH",
                "ঞ" to "N",
                "ট" to "T",
                "ঠ" to "TH",
                "ড" to "D",
                "ঢ" to "DH",
                "ণ" to "N",
                "ত" to "T",
                "থ" to "TH",
                "দ" to "D",
                "ধ" to "DH",
                "ন" to "N",
                "প" to "P",
                "ফ" to "PH",
                "ব" to "B",
                "ভ" to "BH",
                "ম" to "M",
                "য" to "Y",
                "র" to "R",
                "ল" to "L",
                "ৡ" to "LL",
                "শ" to "SH",
                "ষ" to "S",
                "স" to "SH",
                "হ" to "H",
                "ড়" to "R",
                "ঢ়" to "RH",
                "অ" to "A",
                "আ" to "AA",
                "ই" to "I",
                "ঈ" to "II",
                "উ" to "U",
                "ঊ" to "OO",
                "ঋ" to "RI",
                "ৠ" to "RRI",
                "এ" to "E",
                "ঐ" to "EY",
                "ও" to "O",
                "ঔ" to "OU",
                "া" to "AA",
                "ি" to "I",
                "ী" to "II",
                "ু" to "U",
                "ূ" to "UU",
                "ৃৃ" to "RI",
                "ৃ" to "RI",
                "◌ৄ" to "RRI",
                "ে" to "E",
                "ৈ" to "EY",
                "ো" to "O",
                "ৌ" to "OU",
                "ৎ" to "T",
                "ড়" to "RR",
                "ঢ়" to "RH",
                "য়" to "YY",
                "," to ",",
                "।" to ".",
                "॥" to "",
                "্" to "",
                "ৗ" to "",
                "৺" to "",
                "ঀ" to "",
                "◌ঁ" to "",
                "ঁ" to "",
                "ং" to "",
                "ঃ" to "H",
                "ঽ" to "YY",
                "়" to "",

                " " to " "
            )
            val hindi_add_a: List<String> = listOf("क", "ख", "ग", "घ", "ङ", "च", "छ", "ज", "झ", "ञ", "ट", "ठ", "ड", "ढ", "ण", "त", "थ", "द", "ध", "न", "प", "फ", "ब", "भ", "म", "य", "र", "ल", "व", "श", "ष", "स", "ह", "क्ष", "त्र", "ज्ञ", "क़", "ख़", "ग़", "ज़", "ड़", "ढ़", "फ़", "ऱ")
            val bangla_add_o: List<String> = listOf("ক","খ", "গ", "ঘ", "ঙ", "চ", "ছ", "জ", "ঝ", "ঞ", "ট", "ঠ", "ড", "ঢ", "ণ", "ত", "থ", "দ", "ধ", "ন", "প", "ফ", "ব", "ভ", "ম", "য", "র", "ল", "শ", "ষ", "স", "হ", "ড়", "ঢ়")
            val bangla_vowels: List<String> = listOf("অ", "আ", "ই", "ঈ", "উ", "ঊ", "ঋ", "ৠ", "এ", "ঐ", "ও", "ঔ", "া", "ি", "ী", "ু", "ূ", "ৄ", "◌ৄ", "ে", "ৈ", "ো", "ৌ")
             val bangla_not_o: List<String> = listOf("कণ", "कৱ", "চণ", "চৱ", "টণ", "টৱ", "তণ", "তৱ", "পণ", "ষ্ণ", "ষ্ৱ", "ঞণ", "ঞৱ", "জস", "বস", "ু", "ূ", "ৄ", "◌ৄ", "ে", "ৈ", "ো", "ৌ", "্")
            var output: String = ""
            var lastletter: String = ""
            var i = 0
            for (char: Char in text) {
                if ((""+char) in letters.keys) {
                    if (lastletter in hindi_add_a && ""+char in hindi_add_a){
                        output += "A"
                    }
                    if (((i>2 && text[i-2]+"" !in bangla_vowels) && lastletter in bangla_add_o && (char in listOf<Char>(' ', '।', ',', ':', ';', '!', '?')) )|| (char+"" in bangla_add_o && lastletter in bangla_add_o)){
                        output += "O"
                    }
                    output += letters[""+char]
                }
                else{
                    output += ""+char
                }
                lastletter = ""+char
                i+=1
            }
        return output


    }


    override fun onBindViewHolder(holder: LingoShiftViewHolder, position: Int) {
        val curItems = items[position]
        holder.itemView.apply {
            val tvTextTitle = findViewById<TextView>(R.id.tvTextTitle)  // Find by ID
            tvTextTitle.text = curItems.title

            val labelText = curItems.label
            holder.tvLabel.text = if (labelText.isEmpty()) "Add Label" else labelText  // Display empty string if no label

            val editor = mainActivity.sharedPreferences.edit()
            editor.putString("title_${curItems.title}", curItems.label)  // Use title as key prefix
            editor.apply()  // Commit changes asynchronously


            // Set click listener for copy button
            holder.ibCopy.setOnClickListener {
                val textToCopy = holder.tvTextTitle.text.toString()
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.text = textToCopy  // Set the text directly to the clipboard
                Toast.makeText(context, "Text copied!", Toast.LENGTH_SHORT).show()
            }

            // Set click listener for delete button
            holder.ibDelete.setOnClickListener {
                val position = holder.adapterPosition  // Use adapterPosition for accurate position

                val key = "title_${items[position].title}"  // Construct key using title
                val editor = mainActivity.sharedPreferences.edit()
                editor.remove(key)  // Remove item using key
                editor.apply()  // Commit changes asynchronously

                val itemToDelete = items[position]
                // Remove item from data list
                items.removeAt(position)

                // Notify adapter about data change
                notifyItemRemoved(position)
            }

            // Set click listener for tick button
            holder.ibTick.setOnClickListener {
                var text = holder.etLabel.text.toString()
                text = terminal(text)
                holder.Labels.add(text)
                val currentItem = items[position]
                currentItem.label = text

                notifyItemChanged(position)  // Notify adapter about data change
                if (text.isNotEmpty()) {  // Check if text is entered
                    holder.tvLabel.text = text
                    holder.etLabel.visibility = INVISIBLE  // Hide EditText
                    holder.tvLabel.visibility = VISIBLE  // Show TextView
                    holder.ibTick.visibility = INVISIBLE // Hide tick button
                    holder.ibEdit.visibility = VISIBLE
                    holder.etLabel.text.clear()
                } else {
                    Toast.makeText(context, "Please enter a label!", Toast.LENGTH_SHORT).show()
                }
            }

            // Set click listener for edit button
            holder.ibEdit.setOnClickListener {
                    holder.etLabel.visibility = VISIBLE  // Show EditText
                    holder.tvLabel.visibility = INVISIBLE  // Hide TextView
                    holder.ibTick.visibility = VISIBLE // Show tick button
                    holder.ibEdit.visibility = INVISIBLE // Hide Edit Button
                    holder.etLabel.requestFocus()
            }


        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
