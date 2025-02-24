package eu.example.forcaster.adapters

import android.animation.LayoutTransition
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import eu.example.forcaster.R
import eu.example.forcaster.models.QuantumScreenerItem

class QuantumScreenerRecycleViewStockItemsAdapter(
    private val context: Context,
    private val itemList: MutableList<QuantumScreenerItem>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if( viewType == VIEW_TYPE_HEADER){
            val view=LayoutInflater.from(context).inflate(R.layout.quantum_screener_item_header,parent,false)
            HeaderViewHolder(view)
        }else{
            val view  = LayoutInflater.from(context).inflate(R.layout.quantum_screener_item_content_row,parent,false)
            StockViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }
    override fun getItemCount(): Int {
        return itemList.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is StockViewHolder){
            val stockItem = itemList[position-1]

            holder.tvStockSymbol.text = stockItem.stockSymbol
            holder.civStockIcon.setImageResource(stockItem.stockIcon)
            holder.tvStockId.text = stockItem.stockId
            holder.tvStockPercentageCorrelation.text = stockItem.stockCorrelation.toString()
            holder.tvStockYearCorrelation.text = stockItem.stockYearCorrelation
            holder.tvStockWinRate.text = stockItem.stockWinRate.toString()
            holder.tvStockDirection.text = stockItem.stockDirection
            holder.tvStockOpenDate.text = stockItem.stockOpenDate.toString()
            holder.tvStockCloseDate.text = stockItem.stockCloseDate.toString()

            holder.cvExpandableQuantum.setOnClickListener{
                val isVisible = holder.llExpandAdditionInformation.visibility == View.VISIBLE
                if (isVisible) {
                    holder.llExpandAdditionInformation.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .withEndAction { holder.llExpandAdditionInformation.visibility = View.GONE }
                } else {
                    holder.llExpandAdditionInformation.alpha = 0f
                    holder.llExpandAdditionInformation.visibility = View.VISIBLE
                    holder.llExpandAdditionInformation.animate()
                        .alpha(1f)
                        .setDuration(200)
                }
            }
        }
    }

    private class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view)

    private class StockViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvStockSymbol:TextView = itemView.findViewById(R.id.tv_stock_symbol_quantum_screener_content)
        val civStockIcon: CircleImageView = itemView.findViewById(R.id.civ_stock_icon_quantum_screener)
        val tvStockId: TextView = itemView.findViewById(R.id.tv_stock_id_quantum_screener)
        val tvStockPercentageCorrelation: TextView = itemView.findViewById(R.id.tv_stock_percentage_correlation)
        val tvStockYearCorrelation: TextView = itemView.findViewById(R.id.tv_stock_year_correlation)
        val tvStockWinRate : TextView = itemView.findViewById(R.id.tv_stock_win_rate_percentage)
        val tvStockDirection:TextView = itemView.findViewById(R.id.tv_stock_direction_quantum_screener_content)
        val tvStockOpenDate:TextView = itemView.findViewById(R.id.tv_stock_open_date_quantum_screener_content)
        val tvStockCloseDate:TextView = itemView.findViewById(R.id.tv_stock_close_date_quantum_screener_content)

        val cvExpandableQuantum: CardView  = itemView.findViewById(R.id.cv_quantum_screener_expandable_item)
        val llExpandAdditionInformation : LinearLayout = itemView.findViewById(R.id.ll_expandable_additional_information)



    }

}