package eu.example.forcaster.activities

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import eu.example.forcaster.R
import eu.example.forcaster.adapters.CalendarTaskAdapter
import eu.example.forcaster.databinding.ActivityCalendarBinding
import eu.example.forcaster.models.TaskCalendar
import eu.example.forcaster.utils.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private var binding: ActivityCalendarBinding? = null

    private lateinit var calendarView: CalendarView
    private lateinit var addTaskButton: Button
    private lateinit var recyclerView: RecyclerView
    private val mFireStore = FirebaseFirestore.getInstance()
    private lateinit var adapter: CalendarTaskAdapter
    private var selectedDate: String = ""
    private val taskList: MutableList<TaskCalendar> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUpActionBar()

        calendarView = binding!!.calendarView
        addTaskButton = binding!!.btnAddTaskCalendar
        recyclerView = binding!!.rvTaskListCalendar

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CalendarTaskAdapter(taskList){
            onDeleteTask()
        }

        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val sdf = SimpleDateFormat("yyyy-MM.dd",Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.set(year,month,dayOfMonth)
            selectedDate = sdf.format(calendar.time)
            loadTasks(selectedDate)
            taskList.clear()
        }
        addTaskButton.setOnClickListener {
            showDialog()
        }

    }


    private fun setUpActionBar(){
        setSupportActionBar(binding?.toolbarCalendarActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
        }
        binding?.toolbarCalendarActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

     private fun showDialog(){
         val dialog = Dialog(this)

         dialog.setContentView(R.layout.calendar_add_task_dialog)

         val etName = dialog.findViewById<AppCompatEditText>(R.id.et_task_name_dialog_calendar)
         val etDescription = dialog.findViewById<AppCompatEditText>(R.id.et_task_description_dialog_calendar)
         val btnAddTaskDialog = dialog.findViewById<Button>(R.id.btn_add_task_dialog_calendar)
         val btnCancelTaskDialog = dialog.findViewById<Button>(R.id.btn_cancel_task_dialog_calendar)

         dialog.setCanceledOnTouchOutside(false)
         dialog.setCancelable(false)

         btnAddTaskDialog.setOnClickListener {

             addTask(selectedDate,etName.text.toString(),etDescription.text.toString())
             dialog.dismiss()
         }
         btnCancelTaskDialog.setOnClickListener {
             dialog.dismiss()
         }
         dialog.show()

     }

    private fun addTask(date: String, taskName:String, taskDescription: String) {
        val task = TaskCalendar(taskName,taskDescription)
        mFireStore
            .collection(Constants.CALENDAR)
            .document(date).collection(Constants.USERTASKS)
            .document()
            .set(task)
            .addOnSuccessListener {
                loadTasks(date)
            }.addOnFailureListener {

            }
    }

    private fun loadTasks(date: String) {
        mFireStore.collection(Constants.CALENDAR).document(date)
            .collection(Constants.USERTASKS).get()
            .addOnSuccessListener { snapshot ->
                taskList.clear() // Clear previous tasks
                for (document in snapshot.documents) {
                    val task = document.toObject(TaskCalendar::class.java)!!

                        // Log each task for debugging
                        Log.d("FirestoreSuccess", "Task fetched: ${task.description}")

                        // Add the task to the list
                        taskList.add(task) // Ensure document ID is added
                        updateRecyclerViewVisibility()


                }
                Log.i("Data is loaded","$taskList")
                updateRecyclerViewVisibility()
                adapter.notifyDataSetChanged()
            }
    }


        private fun onDeleteTask() {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete") { _, _ ->

                   mFireStore
                       .collection(Constants.CALENDAR)
                       .document(selectedDate)
                       .collection(Constants.USERTASKS)
                       .document()
                       .delete()
                       .addOnSuccessListener {
                           Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                           loadTasks(selectedDate)
                       }.addOnFailureListener {e->
                           Toast.makeText(this, "Failed to delete task: ${e.message}", Toast.LENGTH_SHORT).show()
                       }

                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    private fun updateRecyclerViewVisibility(isTaskListNotEmpty: Boolean = taskList.isNotEmpty()) {
        if (isTaskListNotEmpty) {
            recyclerView.visibility = View.VISIBLE
            binding?.dividerCalender1?.visibility = View.VISIBLE
            binding?.dividerCalender2?.visibility = View.VISIBLE
            binding?.dividerCalender3?.visibility = View.VISIBLE
            binding?.tvTaskCalendar?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            binding?.dividerCalender1?.visibility = View.GONE
            binding?.dividerCalender2?.visibility = View.GONE
            binding?.dividerCalender3?.visibility = View.GONE
            binding?.tvTaskCalendar?.visibility = View.GONE
        }
    }

}