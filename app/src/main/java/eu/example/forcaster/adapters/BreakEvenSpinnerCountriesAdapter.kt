package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import eu.example.forcaster.R

class BreakEvenSpinnerCountriesAdapter(
    context: Context,
    private val itemList: MutableList<String>
): ArrayAdapter<String>(context,0,itemList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Customize the main spinner view (the selected item)
        return createCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Customize the dropdown view (items in the dropdown list)
        return createCustomView(position, convertView, parent)
    }


    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_spinner_breakeeven_activity, parent, false
        )

        val textView = view.findViewById<TextView>(R.id.spinner_item_text)
        textView.text = itemList[position]

        // You can further customize this view (e.g., set icons, colors, etc.)
        return view
    }
}