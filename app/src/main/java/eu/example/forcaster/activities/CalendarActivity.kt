package eu.example.forcaster.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.example.forcaster.R
import eu.example.forcaster.adapters.CalendarTaskAdapter
import eu.example.forcaster.databinding.ActivityCalendarBinding
import eu.example.forcaster.dialogs.FavoriteDialog
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.Favorite
import eu.example.forcaster.models.TaskCalendar
import eu.example.forcaster.utils.Constants
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityCalendarBinding? = null

    private lateinit var calendarView: CalendarView
    private lateinit var addTaskButton: Button
    private lateinit var recyclerView: RecyclerView
    private val mFireStore = FirebaseFirestore.getInstance()
    private lateinit var adapter: CalendarTaskAdapter
    private var selectedDate: String = ""
    private val taskList: MutableList<TaskCalendar> = mutableListOf()
    private lateinit var mSharedPreferences: SharedPreferences
    private val stockList : ArrayList<Favorite> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()
        MainActivity().onBackPressedNew()

        binding?.navView?.setNavigationItemSelectedListener(this)

        mSharedPreferences = this.getSharedPreferences(
            Constants.FORECASTER_PREFERENCES, Context.MODE_PRIVATE)

        FireStoreClass().loadUserData(this@CalendarActivity)

        calendarView = findViewById(R.id.calendar_view)
        addTaskButton = findViewById(R.id.btn_add_task_calendar)
        recyclerView = findViewById(R.id.rv_task_list_calendar)

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


    private fun setUpActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_calendar_activity)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar.setNavigationOnClickListener {
           toggleDrawer()
        }
    }

    private fun toggleDrawer(){
        if(binding?.drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            binding?.drawerLayout!!.closeDrawer(GravityCompat.START)
        }else{
            binding?.drawerLayout!!.openDrawer(GravityCompat.START)
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
       val dividerCalender1 = findViewById<View>(R.id.divider_calender_1)
        val dividerCalender2 = findViewById<View>(R.id.divider_calender_2)
        val dividerCalender3 = findViewById<View>(R.id.divider_calender_3)
        val tvTaskCalendar = findViewById<TextView>(R.id.tv_task_calendar)

        if (isTaskListNotEmpty) {
            recyclerView.visibility = View.VISIBLE
            dividerCalender1?.visibility = View.VISIBLE
            dividerCalender2?.visibility = View.VISIBLE
            dividerCalender3?.visibility = View.VISIBLE
            tvTaskCalendar?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            dividerCalender1?.visibility = View.GONE
            dividerCalender2?.visibility = View.GONE
            dividerCalender3?.visibility = View.GONE
            tvTaskCalendar?.visibility = View.GONE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.homePageButton ->{
                startActivity(Intent(this@CalendarActivity,MainActivity::class.java))
            }
            R.id.nav_my_profile ->{
                startActivity(Intent(this@CalendarActivity,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                signOutDialog()
            }
            R.id.nav_favourites ->{
                favoriteDialog()
            }
            R.id.nav_calendar ->{
                startActivity(Intent(this@CalendarActivity,CalendarActivity::class.java))
            }
            R.id.nav_cot ->{
                startActivity(Intent(this@CalendarActivity,COTReportsActivity::class.java))
            }
            R.id.nav_rankings ->{
                startActivity(Intent(this@CalendarActivity,RankingsActivity::class.java))
            }
            R.id.nav_break_even ->{
                startActivity(Intent(this@CalendarActivity,BreakEvenActivity::class.java))
            }
            R.id.nav_quantum_screener ->{
                startActivity(Intent(this@CalendarActivity,QuantumScreenerActivity::class.java))
            }
            R.id.nav_subscription ->{

            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOutDialog(){
        val dialog = Dialog(this@CalendarActivity)

        dialog.setContentView(R.layout.dialog_sign_out)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            mSharedPreferences.edit().clear().apply()

            val intent = Intent(this@CalendarActivity,IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        dialog.show()
    }

    private fun favoriteDialog(){

        val favList:ArrayList<Favorite> = stockList

        val dialog =object : FavoriteDialog(
            this,
            favList,
            "Favorite stocks"
        ){
            override fun onItemSelected(item: Favorite) {
                //ToDo add some new features as a buttons for deleting and intent to stock overview
            }

        }
        dialog.show()
    }
}