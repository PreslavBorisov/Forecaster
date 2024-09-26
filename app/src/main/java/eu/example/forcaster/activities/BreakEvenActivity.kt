package eu.example.forcaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import eu.example.forcaster.databinding.ActivityBreakeevenBinding

class BreakEvenActivity : AppCompatActivity() {

    private var binding: ActivityBreakeevenBinding? = null

    private var arrayList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakeevenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val dropDownList = binding?.spinnerDropDownListCountries

        val adapterList = ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayList)

        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dropDownList?.adapter = adapterList

        dropDownList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selected = parent?.getItemAtPosition(position).toString()

                Toast.makeText(this@BreakEvenActivity,"Selected $selected",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
}