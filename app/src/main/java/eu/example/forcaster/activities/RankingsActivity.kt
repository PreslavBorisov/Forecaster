package eu.example.forcaster.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import eu.example.forcaster.R
import eu.example.forcaster.adapters.RankingItemAdapter
import eu.example.forcaster.databinding.ActivityRankingsBinding
import eu.example.forcaster.dialogs.FavoriteDialog
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.Favorite
import eu.example.forcaster.models.RankingItem
import eu.example.forcaster.utils.Constants

class RankingsActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityRankingsBinding? = null
    private lateinit var rankingAdapter: RankingItemAdapter

    private var rankingNorthAmericaItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingWorldItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingLatinAmericaItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingEuropeItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingAsiaItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingOceaniaItemList: MutableList<RankingItem> = mutableListOf()
    private lateinit var mSharedPreferences: SharedPreferences
    private val stockList : ArrayList<Favorite> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityRankingsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setUpActionBar()
        MainActivity().onBackPressedNew()
        binding?.navView?.setNavigationItemSelectedListener(this)

        mSharedPreferences = this.getSharedPreferences(
            Constants.FORECASTER_PREFERENCES, Context.MODE_PRIVATE)

        rankingWorldItemList = getItemListWorld()
        val rvWorldItemList = findViewById<RecyclerView>(R.id.rv_world_rankings)
        rvWorldItemList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingWorldItemList)

        rvWorldItemList?.adapter = rankingAdapter

        rankingLatinAmericaItemList = getItemListLatinAmerica()
        val rvLatinAmericaRankingList = findViewById<RecyclerView>(R.id.rv_latinAmerica_rankings)
        rvLatinAmericaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingLatinAmericaItemList)

        rvLatinAmericaRankingList?.adapter = rankingAdapter

        rankingNorthAmericaItemList = getItemListNorthAmerica()
        val rvNorthAmericaRankingList = findViewById<RecyclerView>(R.id.rv_northAmerica_rankings)
        rvNorthAmericaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingNorthAmericaItemList)

        rvNorthAmericaRankingList?.adapter = rankingAdapter


        rankingEuropeItemList = getEuropeItemList()
        val rvEuropeRankingList = findViewById<RecyclerView>(R.id.rv_europe_rankings)
        rvEuropeRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingEuropeItemList)

        rvEuropeRankingList?.adapter = rankingAdapter


        rankingAsiaItemList  = getAsiaItemList()
        val rvAsiaRankingList = findViewById<RecyclerView>(R.id.rv_asia_rankings)
        rvAsiaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingAsiaItemList)

        rvAsiaRankingList?.adapter = rankingAdapter

        rankingOceaniaItemList = getOceaniaItemList()
        val rvOceaniaRankingList = findViewById<RecyclerView>(R.id.rv_oceania_rankings)
        rvOceaniaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingOceaniaItemList)


        rvOceaniaRankingList?.adapter = RankingItemAdapter(this@RankingsActivity,rankingOceaniaItemList)

        FireStoreClass().loadUserData(this@RankingsActivity)
    }

    private fun getItemListWorld(): MutableList<RankingItem> {
        val worldItemList: MutableList<RankingItem> = mutableListOf()

        worldItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "IPC MEXICO","Mexico"))


        return worldItemList
    }

    private fun getOceaniaItemList(): MutableList<RankingItem> {
        val oceaniaItemList: MutableList<RankingItem> = mutableListOf()

        oceaniaItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "IPC MEXICO","Mexico"))

        oceaniaItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "BOVESPA","Brazil"))


        return oceaniaItemList
    }

    private fun getAsiaItemList(): MutableList<RankingItem> {
        val asiaItemList: MutableList<RankingItem> = mutableListOf()

        asiaItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "IPC MEXICO","Mexico"))

        asiaItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "BOVESPA","Brazil"))

        asiaItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "MERVAL","Argentina"))

        return asiaItemList
    }


    private fun setUpActionBar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_rankings)
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


    private fun getItemListNorthAmerica(): MutableList<RankingItem>{
        val getListNorthAmerica: MutableList<RankingItem> = mutableListOf()

        getListNorthAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "S&P 500","United States"))

        getListNorthAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "IPC MEXICO","Mexico"))

        getListNorthAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "BOVESPA","Brazil"))


        return getListNorthAmerica
    }

    private fun getItemListLatinAmerica(): MutableList<RankingItem>{
        val getListLatinAmerica: MutableList<RankingItem> = mutableListOf()

        getListLatinAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "IPC MEXICO","Mexico"))

        getListLatinAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "BOVESPA","Brazil"))

        getListLatinAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "MERVAL","Argentina"))

        return getListLatinAmerica
    }

    private fun getEuropeItemList(): MutableList<RankingItem>{
        val getEuropeItemList: MutableList<RankingItem> = mutableListOf()
        getEuropeItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "STOXX Europe 600","Europe"))

        getEuropeItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "EURO STOXX 50","Eurozone"))

        getEuropeItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "DAX 40","Germany"))

        getEuropeItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "FTSE 100","United Kingdom"))

        getEuropeItemList.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "FTSE MIB","Italy"))



        return getEuropeItemList
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.homePageButton ->{
                startActivity(Intent(this@RankingsActivity,MainActivity::class.java))
            }
            R.id.nav_my_profile ->{
                startActivity(Intent(this@RankingsActivity,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                signOutDialog()
            }
            R.id.nav_favourites ->{
                favoriteDialog()
            }
            R.id.nav_calendar ->{
                startActivity(Intent(this@RankingsActivity,CalendarActivity::class.java))
            }
            R.id.nav_cot ->{
                startActivity(Intent(this@RankingsActivity,COTReportsActivity::class.java))
            }
            R.id.nav_rankings ->{
                startActivity(Intent(this@RankingsActivity,RankingsActivity::class.java))
            }
            R.id.nav_break_even ->{
                startActivity(Intent(this@RankingsActivity,BreakEvenActivity::class.java))
            }
            R.id.nav_quantum_screener ->{
                startActivity(Intent(this@RankingsActivity,QuantumScreenerActivity::class.java))
            }
            R.id.nav_subscription ->{

            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOutDialog(){
        val dialog = Dialog(this@RankingsActivity)

        dialog.setContentView(R.layout.dialog_sign_out)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            mSharedPreferences.edit().clear().apply()

            val intent = Intent(this@RankingsActivity,IntroActivity::class.java)
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