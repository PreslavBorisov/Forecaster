package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.example.forcaster.R
import eu.example.forcaster.models.COTItem

class CotReportsItemsAdapter(
    private val context: Context,
    private val currenciesList: MutableList<COTItem>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener?=null

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle:TextView = itemView.findViewById(R.id.tv_title_cotreports)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.items_cotreports, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currenciesList[position]

        if (holder is MyViewHolder) {
            holder.tvTitle.text = item.title

            holder.itemView.setOnClickListener {
                if (onItemClickListener!=null){
                    onItemClickListener!!.onClick(position,item)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int, model: COTItem)
    }

}