package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import eu.example.forcaster.R
import eu.example.forcaster.models.BreakEvenItem

class BreakEvenItemListAdapter(
    private val context: Context,
    private val itemList: MutableList<BreakEvenItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_HEADER){
            val view = LayoutInflater.from(context).inflate(R.layout.row_header_item,parent,false)
            HeaderViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.row_stock_item_breakeven,parent,false)
            StockViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StockViewHolder){

            val stockItem = itemList[position-1]
            holder.civStock.setImageResource(stockItem.stockIcon)
            holder.tvStockId.text = stockItem.stockID
            holder.tvStockName.text = stockItem.stockName
            holder.civCountryFlag.setImageResource(stockItem.countryFlag)
            holder.tvCountryName.text = stockItem.countryName
            holder.tvSector.text = stockItem.sector
            holder.tvMarketCap.text = stockItem.marketCap
            holder.tvBreakEvenQuarter.text = stockItem.breakEvenQuarter

            holder.llBreakEvenMainContent.setOnClickListener {
                val isVisible = holder.llExpandableInformationBreakEven.visibility == View.VISIBLE
                if(isVisible){
                    holder.llExpandableInformationBreakEven.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .withEndAction{holder.llExpandableInformationBreakEven.visibility = View.GONE}
                }else{
                    holder.llExpandableInformationBreakEven.alpha = 0f
                    holder.llExpandableInformationBreakEven.visibility = View.VISIBLE
                    holder.llExpandableInformationBreakEven.animate()
                        .alpha(1f)
                        .setDuration(200)
                }
            }

        }
    }

    private class StockViewHolder(view: View): RecyclerView.ViewHolder(view){
        val civStock:CircleImageView = itemView.findViewById(R.id.ivStockIcon)
        val tvStockId: TextView = itemView.findViewById(R.id.tvStockID)
        val tvStockName :TextView= itemView.findViewById(R.id.tvStockName)
        val civCountryFlag:CircleImageView = itemView.findViewById(R.id.ivCountryFlag)
        val tvCountryName:TextView = itemView.findViewById(R.id.tvCountryName)
        val tvSector : TextView = itemView.findViewById(R.id.tvSector)
        val tvMarketCap: TextView = itemView.findViewById(R.id.tvMarketCap)
        val tvBreakEvenQuarter: TextView = itemView.findViewById(R.id.tvBreakevenQuarter)

        val llBreakEvenMainContent: LinearLayout = itemView.findViewById(R.id.ll_break_even_main_content)
        val llExpandableInformationBreakEven: LinearLayout = itemView.findViewById(R.id.ll_expandable_information_break_even)
    }

    private class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view)

}