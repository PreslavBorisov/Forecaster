package eu.example.forcaster.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.example.forcaster.R
import eu.example.forcaster.models.TaskCalendar

class CalendarTaskAdapter(
    private val taskList: MutableList<TaskCalendar>,
    private val onDeleteTask: (String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val taskDescription: TextView = itemView.findViewById(R.id.tv_item_task_description_calendar)
        val taskName: TextView = itemView.findViewById(R.id.tv_item_task_name_calendar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task_calendar,parent,false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = taskList[position]
        if(holder is MyViewHolder){
            holder.taskName.text = task.name
            holder.taskDescription.text = task.description

            holder.itemView.setOnLongClickListener {
                onDeleteTask(task.name)
                true
            }
        }
    }

}