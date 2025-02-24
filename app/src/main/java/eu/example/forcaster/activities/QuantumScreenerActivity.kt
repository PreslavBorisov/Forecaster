package eu.example.forcaster.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import eu.example.forcaster.R
import eu.example.forcaster.adapters.BreakEvenSpinnerCountAdapter
import eu.example.forcaster.adapters.QuantumScreenerRecycleViewStockItemsAdapter
import eu.example.forcaster.adapters.SpinnerQuantumScreenerInstrumentsAdapter
import eu.example.forcaster.adapters.SpinnerTimeFrame
import eu.example.forcaster.databinding.ActivityQuantumScreenerBinding
import eu.example.forcaster.dialogs.FavoriteDialog
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.Favorite
import eu.example.forcaster.models.QuantumScreenerItem
import eu.example.forcaster.utils.Constants
import java.util.Date

@Suppress("DEPRECATION")
class QuantumScreenerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding : ActivityQuantumScreenerBinding? = null

    private lateinit var instrumentsListItems:MutableList<String>
    private lateinit var timeFrameItemList: MutableList<String>
    private lateinit var mSharedPreferences: SharedPreferences
    private var stockItemList: MutableList<QuantumScreenerItem> = getStockItemList()
    private val stockList : ArrayList<Favorite> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuantumScreenerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()

        onBackPressedNew()
        binding?.navView?.setNavigationItemSelectedListener(this)

        mSharedPreferences = this.getSharedPreferences(
            Constants.FORECASTER_PREFERENCES, Context.MODE_PRIVATE)

        FireStoreClass().loadUserData(this@QuantumScreenerActivity)
        setUpSpinnerInstruments()
        setUpSpinnerFrameTime()
        setUpSpinnerPositionTarget()
        setUpSpinnerCountStocks()
        setUpRecycleView()
    }

    private fun getInstrumentsList(): MutableList<String> {
        val instrumentsList: MutableList<String> = mutableListOf()
        instrumentsList.add("All Instruments")
        instrumentsList.add("Comodities")

        return instrumentsList
    }

    private fun getStockItemList(): MutableList<QuantumScreenerItem> {
        return mutableListOf(

        QuantumScreenerItem(
            stockSymbol = "AAPL",
            stockIcon = R.drawable.ic_board_place_holder,
            stockId = "STK001",
            stockCorrelation = 0.89,
            stockYearCorrelation = "[3y]",
            stockWinRate = 0.75,
            stockDirection = "Long",
            stockAvgReturn = 15.5,
            stockOpenDate = Date(2023, 0, 1),
            stockCloseDate = Date(2023, 11, 31)
        ),
        QuantumScreenerItem(
            stockSymbol = "GOOG",
            stockIcon = R.drawable.ic_board_place_holder,
            stockId = "STK002",
            stockCorrelation = -0.45,
            stockYearCorrelation = "[3y]",
            stockWinRate = 0.65,
            stockDirection = "Short",
            stockAvgReturn = -5.3,
            stockOpenDate = Date(2022, 5, 1), // Jun 1, 2022
            stockCloseDate = Date(2023, 2, 28) // Feb 28, 2023
        ),
        QuantumScreenerItem(
            stockSymbol = "TSLA",
            stockIcon = R.drawable.ic_board_place_holder,
            stockId = "STK003",
            stockCorrelation = 0.72,
            stockYearCorrelation = "[3y]",
            stockWinRate = 0.80,
            stockDirection = "Long",
            stockAvgReturn = 20.1,
            stockOpenDate = Date(2021, 3, 15), // Apr 15, 2021
            stockCloseDate = Date(2023, 6, 20) // Jul 20, 2023
        )
        )

    }
    private fun setUpActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_quantum_screener_activity)
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

    private fun onBackPressedNew(){
        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding?.drawerLayout?.isDrawerOpen(GravityCompat.START)==true){
                    binding?.drawerLayout?.closeDrawer(GravityCompat.START)
                }else{
                    doubleBackToExit()
                }
            }
        })
    }

    private fun setUpSpinnerInstruments(){
        val spinnerInstruments = findViewById<Spinner>(R.id.spinner_instruments_screener)

        instrumentsListItems = getInstrumentsList()

        val spinnerAdapterInstruments = SpinnerQuantumScreenerInstrumentsAdapter(this@QuantumScreenerActivity,instrumentsListItems)
        spinnerInstruments?.adapter= spinnerAdapterInstruments

        spinnerInstruments?.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selected = parent?.getItemAtPosition(position).toString()

                Toast.makeText(
                    this@QuantumScreenerActivity,
                    "Item selected: $selected",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getFrameTimeList(): MutableList<String> {
        return mutableListOf(
            "1 Month",
            "3 Months",
            "6 Months",
            "12 Months"
        )
    }
    private fun setUpSpinnerFrameTime(){

        val spinnerTimeFrame = findViewById<Spinner>(R.id.spinner_time_frame_screener)
        timeFrameItemList = getFrameTimeList()
        val spinnerTimeFrameAdapter = SpinnerTimeFrame(this@QuantumScreenerActivity,timeFrameItemList)
        spinnerTimeFrame?.adapter = spinnerTimeFrameAdapter

        spinnerTimeFrame?.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selected = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@QuantumScreenerActivity,"Item selected: $selected",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setUpSpinnerPositionTarget(){
        val spinnerPositionTarget =findViewById<Spinner>(R.id.spinner_long_short_trade_screener)
        val positionList: MutableList<String> = mutableListOf(
            "Long & Short",
            "Long",
            "Short"
        )
        val positionAdapter = SpinnerTimeFrame(this@QuantumScreenerActivity,positionList)
        spinnerPositionTarget?.adapter = positionAdapter
        spinnerPositionTarget?.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@QuantumScreenerActivity,"Item selected: $selected",Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    private fun setUpSpinnerCountStocks(){
        val spinnerCountStocks = findViewById<Spinner>(R.id.spinner_count_screener)
        val listOfCountItems : MutableList<String> = mutableListOf("" +
                "50",
            "All Instruments")
        val spinnerCountStocksAdapter = BreakEvenSpinnerCountAdapter(this@QuantumScreenerActivity,listOfCountItems)
        spinnerCountStocks?.adapter = spinnerCountStocksAdapter
        spinnerCountStocks?.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selected = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@QuantumScreenerActivity,"Item selected: $selected",Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setUpRecycleView(){
        val rvStockItemList : RecyclerView = findViewById(R.id.rv_stock_list_screener)
        rvStockItemList.layoutManager = LinearLayoutManager(this@QuantumScreenerActivity)
        val adapterStockList = QuantumScreenerRecycleViewStockItemsAdapter(this@QuantumScreenerActivity,stockItemList)

        rvStockItemList.adapter = adapterStockList
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.homePageButton ->{
                startActivity(Intent(this@QuantumScreenerActivity,MainActivity::class.java))
            }
            R.id.nav_my_profile ->{
                startActivity(Intent(this@QuantumScreenerActivity,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                signOutDialog()
            }
            R.id.nav_favourites ->{
                favoriteDialog()
            }
            R.id.nav_calendar ->{
                startActivity(Intent(this@QuantumScreenerActivity,CalendarActivity::class.java))
            }
            R.id.nav_cot ->{
                startActivity(Intent(this@QuantumScreenerActivity,COTReportsActivity::class.java))
            }
            R.id.nav_rankings ->{
                startActivity(Intent(this@QuantumScreenerActivity,RankingsActivity::class.java))
            }
            R.id.nav_break_even ->{
                startActivity(Intent(this@QuantumScreenerActivity,BreakEvenActivity::class.java))
            }
            R.id.nav_quantum_screener ->{
                startActivity(Intent(this@QuantumScreenerActivity,QuantumScreenerActivity::class.java))
            }
            R.id.nav_subscription ->{

            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOutDialog(){
        val dialog = Dialog(this@QuantumScreenerActivity)

        dialog.setContentView(R.layout.dialog_sign_out)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            mSharedPreferences.edit().clear().apply()

            val intent = Intent(this@QuantumScreenerActivity,IntroActivity::class.java)
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