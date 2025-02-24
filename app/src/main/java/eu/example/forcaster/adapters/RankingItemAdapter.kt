package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import eu.example.forcaster.R
import eu.example.forcaster.models.RankingItem

class RankingItemAdapter(
    private val context: Context,
    private val rankingsItemList: MutableList<RankingItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickItemRankingListener: OnClickItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return MyViewHolder(LayoutInflater.from(context)
          .inflate(R.layout.item_rankings_activity, parent,false))
    }

    override fun getItemCount(): Int {
        return rankingsItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = rankingsItemList[position]
        if(holder is MyViewHolder){

            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(holder.civRankingsImage)



            holder.titleRankings.text = item.title
            holder.tagRankings.text = item.tag

            holder.itemView.setOnClickListener {
                if(onClickItemRankingListener!=null){
                    this.onClickItemRankingListener!!.onClick(position,item)
                }
            }
        }
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val titleRankings:TextView = itemView.findViewById(R.id.title_item_rankings)
        val tagRankings:TextView= itemView.findViewById(R.id.tag_item_rankings)
        val civRankingsImage: CircleImageView = itemView.findViewById(R.id.civ_rankings_item_image)
    }

    interface OnClickItemListener{
        fun onClick(position: Int,model: RankingItem)
    }
}