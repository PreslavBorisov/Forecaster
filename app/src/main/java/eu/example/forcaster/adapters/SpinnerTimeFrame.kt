package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import eu.example.forcaster.R

class SpinnerTimeFrame(
    context: Context,
    private val list: MutableList<String>
): ArrayAdapter<String>(context,0,list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position,convertView,parent)
    }


    private fun createCustomView(position:Int, convertView: View?, parent: ViewGroup): View{
        val view = convertView?: LayoutInflater.from(context).inflate(
            R.layout.item_time_frame_screener,parent,false
        )
        val textView = view.findViewById<TextView>(R.id.tv_time_frame_text_screener)
        textView.text = list[position]
        return view
    }
}