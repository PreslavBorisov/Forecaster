package eu.example.forcaster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.example.forcaster.R
import eu.example.forcaster.models.Favorite

class FavoriteListAdapter(
    private val context: Context,
    private var list: ArrayList<Favorite>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return MyViewHolder(
           LayoutInflater.from(context)
               .inflate(R.layout.item_favorite,parent,false)
       )
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        if (holder is MyViewHolder){

            Glide
                .with(context)
                .load(item.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(holder.itemView.findViewById(R.id.civ_favorite_image))


            holder.itemView.findViewById<TextView>(R.id.tv_name_favorite).text = item.name
            holder.itemView.findViewById<TextView>(R.id.tv_tag_favorite).text = item.tag

            holder.itemView.setOnClickListener {
                if(onClickListener !=null){
                    onClickListener!!.onClick(position,item)
                }
            }


        }
    }

    private class MyViewHolder(view: View):RecyclerView.ViewHolder(view)

    interface OnClickListener{
        fun onClick(position: Int,model: Favorite)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

}