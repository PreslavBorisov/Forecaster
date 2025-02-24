package eu.example.forcaster.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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
import eu.example.forcaster.adapters.BreakEvenItemListAdapter
import eu.example.forcaster.adapters.BreakEvenSpinnerCountAdapter
import eu.example.forcaster.adapters.BreakEvenSpinnerCountriesAdapter
import eu.example.forcaster.databinding.ActivityBreakeevenBinding
import eu.example.forcaster.dialogs.FavoriteDialog
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.BreakEvenItem
import eu.example.forcaster.models.Favorite
import eu.example.forcaster.utils.Constants

class BreakEvenActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityBreakeevenBinding? = null

    private var arrayCountryList: MutableList<String> = mutableListOf()

    private var arrayCountList: MutableList<String> = mutableListOf()

    private var arrayStockList: MutableList<BreakEvenItem> = mutableListOf()
    private lateinit var mSharedPreferences: SharedPreferences
    private val stockList : ArrayList<Favorite> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakeevenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()
        onBackPressedNew()
        binding?.navView?.setNavigationItemSelectedListener(this)

        mSharedPreferences = this.getSharedPreferences(
            Constants.FORECASTER_PREFERENCES, Context.MODE_PRIVATE)

        FireStoreClass().loadUserData(this@BreakEvenActivity)

        val dropDownCountryList = findViewById<Spinner>(R.id.spinner_drop_down_list_countries)

        arrayCountryList = getCountryArrayList()

        val adapterCountryList = BreakEvenSpinnerCountriesAdapter(this@BreakEvenActivity,arrayCountryList)

        dropDownCountryList?.adapter = adapterCountryList

        dropDownCountryList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selected = parent?.getItemAtPosition(position).toString()

                Toast.makeText(this@BreakEvenActivity,"Selected $selected",Toast.LENGTH_SHORT).show()
            }



            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        arrayCountList = getCountArrayList()

        val dropDownCountList = findViewById<Spinner>(R.id.spinner_drop_down_list_count)

        val adapterCountList = BreakEvenSpinnerCountAdapter(this@BreakEvenActivity,arrayCountList)

        dropDownCountList?.adapter = adapterCountList

        dropDownCountList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val selectedCount = parent?.getItemAtPosition(position).toString()

                Toast.makeText(this@BreakEvenActivity,"Selected $selectedCount",Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        arrayStockList = getStockList()
        setUpRecycleView()

    }

    private fun getStockList(): MutableList<BreakEvenItem> {
        val stockList: MutableList<BreakEvenItem> = mutableListOf()
        stockList.add(
            BreakEvenItem(R.drawable.ic_user_place_holder,
            "3690.HK","Meinhart",
            R.drawable.ic_board_place_holder,
            "China",
            "Consumer Cyclical",
            "248.69B",
            "30.09.2022"
            )
        )
        return stockList
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

    private fun setUpRecycleView(){
        val rvStockItemList = findViewById<RecyclerView>(R.id.rv_stock_list_break_even_activity)
        rvStockItemList?.layoutManager = LinearLayoutManager(this@BreakEvenActivity)
        val adapterStockList = BreakEvenItemListAdapter(this@BreakEvenActivity,arrayStockList)

        rvStockItemList?.adapter = adapterStockList
    }

    private fun setUpActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_breakEven_activity)
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

    private fun getCountryArrayList():MutableList<String>{
        val getArrayListItem: MutableList<String> = mutableListOf()

        getArrayListItem.add("All Countries")
        getArrayListItem.add("USA")
        getArrayListItem.add("Italy")
        getArrayListItem.add("Bulgaria")
        getArrayListItem.add("Spain")

        return getArrayListItem
    }

    private fun getCountArrayList():MutableList<String>{
        val getArrayListItem: MutableList<String> = mutableListOf()

        getArrayListItem.add("50")
        getArrayListItem.add("All Countries")

        return getArrayListItem
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.homePageButton ->{
                startActivity(Intent(this@BreakEvenActivity,MainActivity::class.java))
            }
            R.id.nav_my_profile ->{
                startActivity(Intent(this@BreakEvenActivity,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                signOutDialog()
            }
            R.id.nav_favourites ->{
                favoriteDialog()
            }
            R.id.nav_calendar ->{
                startActivity(Intent(this@BreakEvenActivity,CalendarActivity::class.java))
            }
            R.id.nav_cot ->{
                startActivity(Intent(this@BreakEvenActivity,COTReportsActivity::class.java))
            }
            R.id.nav_rankings ->{
                startActivity(Intent(this@BreakEvenActivity,RankingsActivity::class.java))
            }
            R.id.nav_break_even ->{
                startActivity(Intent(this@BreakEvenActivity,BreakEvenActivity::class.java))
            }
            R.id.nav_quantum_screener ->{
                startActivity(Intent(this@BreakEvenActivity,QuantumScreenerActivity::class.java))
            }
            R.id.nav_subscription ->{

            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOutDialog(){
        val dialog = Dialog(this@BreakEvenActivity)

        dialog.setContentView(R.layout.dialog_sign_out)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            mSharedPreferences.edit().clear().apply()

            val intent = Intent(this@BreakEvenActivity,IntroActivity::class.java)
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