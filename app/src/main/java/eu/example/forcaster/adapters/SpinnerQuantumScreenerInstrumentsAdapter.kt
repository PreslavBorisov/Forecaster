package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import eu.example.forcaster.R

class SpinnerQuantumScreenerInstrumentsAdapter(
    context: Context,
    private val itemList: MutableList<String>
):ArrayAdapter<String>(context,0,itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }

    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup) : View{

        val view = convertView?: LayoutInflater.from(context).inflate(
            R.layout.item_quantum_screener_spinner_item_instruments,parent,false
        )

        val textView = view.findViewById<TextView>(R.id.tv_item_spinner_screener)
        textView.text = itemList[position]

        return view
    }

}