package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import eu.example.forcaster.R

class BreakEvenSpinnerCountAdapter(
    context: Context,
    private val itemList: MutableList<String>
): ArrayAdapter< String>(context,0,itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }

    private fun createCustomView(position: Int, convertedView: View?,parent: ViewGroup): View{

        val view = convertedView?: LayoutInflater.from(context).inflate(
            R.layout.item_spinner_count_breakeven,parent,false
        )

        val textView = view.findViewById<TextView>(R.id.spinner_item_count)
        textView.text = itemList[position]

        return view
    }

}