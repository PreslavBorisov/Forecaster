package eu.example.forcaster.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.example.forcaster.R
import eu.example.forcaster.adapters.FavoriteListAdapter
import eu.example.forcaster.models.Favorite

abstract class FavoriteDialog(
    context: Context,
    private var list:ArrayList<Favorite>,
    private var title: String = ""
):Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view  = LayoutInflater.from(context).inflate(R.layout.dialog_favorite,null)

        setCancelable(true)
        setCanceledOnTouchOutside(true)
        setContentView(view)
        setUpRecycleView(view)
    }

    private fun setUpRecycleView(view: View){

        val rvList = view.findViewById<RecyclerView>(R.id.rv_list_favorite)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title_dialog_favorite)

        tvTitle.text= title
        if(list.size >0){

            rvList.visibility = View.VISIBLE

            rvList.layoutManager = GridLayoutManager(context,3)

            val adapter = FavoriteListAdapter(context,list)
            rvList.adapter = adapter

            adapter.setOnClickListener(
                object : FavoriteListAdapter.OnClickListener{
                    override fun onClick(position: Int, model: Favorite) {
                        dismiss()
                        onItemSelected(model)
                    }
                }
            )
            findViewById<ImageView>(R.id.iv_selected_item_favorite).setOnClickListener {
                dismiss()
                //ToDo delete the selected item
            }

        }else{
            rvList.visibility = View.GONE
        }

    }
    protected abstract fun onItemSelected(item:Favorite)
}