package eu.example.forcaster.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import eu.example.forcaster.R
import eu.example.forcaster.adapters.CotReportsItemsAdapter
import eu.example.forcaster.databinding.ActivityCotreportsBinding
import eu.example.forcaster.dialogs.FavoriteDialog
import eu.example.forcaster.firebase.FireStoreClass
import eu.example.forcaster.models.COTItem
import eu.example.forcaster.models.Favorite
import eu.example.forcaster.utils.Constants

class COTReportsActivity :  BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityCotreportsBinding? = null
    private var currenciesList: MutableList<COTItem> = mutableListOf()
    private lateinit var adapterCOTReports: CotReportsItemsAdapter

    private var agricultureList: MutableList<COTItem> = mutableListOf()

    private var metalsAndOtherList: MutableList<COTItem> = mutableListOf()

    private var stockIndexesList: MutableList<COTItem> = mutableListOf()

    private var petroleumProductsList: MutableList<COTItem> = mutableListOf()

    private var treasuriesRatesList: MutableList<COTItem> = mutableListOf()

    private var naturalGasProductsList: MutableList<COTItem> = mutableListOf()

    private var syntheticCurrenciesList: MutableList<COTItem> = mutableListOf()
    private lateinit var mSharedPreferences: SharedPreferences
    private val stockList : ArrayList<Favorite> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCotreportsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setUpActionBar()
        onBackPressedNew()
        binding?.navViewCotReports?.setNavigationItemSelectedListener(this)

        mSharedPreferences = this.getSharedPreferences(
            Constants.FORECASTER_PREFERENCES, Context.MODE_PRIVATE)

        FireStoreClass().loadUserData(this@COTReportsActivity)
        currenciesList = currenciesItemList()
        val rvCurrenciesList = findViewById<RecyclerView>(R.id.rv_currencies_cotreports)
        rvCurrenciesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,currenciesList)

        rvCurrenciesList?.adapter = adapterCOTReports


        agricultureList = agricultureItemList()
        val rvAgricultureList = findViewById<RecyclerView>(R.id.rv_agriculture_cotreports)
        rvAgricultureList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,agricultureList)

        rvAgricultureList?.adapter = adapterCOTReports


        metalsAndOtherList = metalsAndOtherItemList()
        val rvMetalsAndOtherList = findViewById<RecyclerView>(R.id.rv_MetalsandOther_cotreports)
        rvMetalsAndOtherList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,metalsAndOtherList)

        rvMetalsAndOtherList?.adapter = adapterCOTReports


        stockIndexesList = stockIndexesItemList()
        val rvStockIndexesList = findViewById<RecyclerView>(R.id.rv_StockIndexes_cotreports)

        rvStockIndexesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,stockIndexesList)

        rvStockIndexesList?.adapter = adapterCOTReports


        petroleumProductsList = petroleumProductsItemList()
        val rvPetroleumProductsList = findViewById<RecyclerView>(R.id.rv_PetroleumProducts_cotreports)

        rvPetroleumProductsList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,petroleumProductsList)

        rvPetroleumProductsList?.adapter = adapterCOTReports

        treasuriesRatesList = treasuriesRatesItemList()
        val rvTreasuriesRatesList = findViewById<RecyclerView>(R.id.rv_TreasuriesandRates_cotreports)

        rvTreasuriesRatesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,treasuriesRatesList)

        rvTreasuriesRatesList?.adapter = adapterCOTReports

        naturalGasProductsList = naturalGasProductsItemList()
        val rvNaturalGasProducts =findViewById<RecyclerView>(R.id.rv_NaturalGasProducts_cotreports)

        rvNaturalGasProducts?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,naturalGasProductsList)

        rvNaturalGasProducts?.adapter = adapterCOTReports

        syntheticCurrenciesList = syntheticCurrenciesItemList()
        val rvSyntheticCurrencyList = findViewById<RecyclerView>(R.id.rv_synthetic_currencies_cotreports)

        rvSyntheticCurrencyList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,syntheticCurrenciesList)

        rvSyntheticCurrencyList?.adapter = adapterCOTReports

        val rgReports = findViewById<RadioGroup>(R.id.rg_reports)

        val llSelectedSynthetic = findViewById<LinearLayout>(R.id.ll_selected_synthetic)
        val llOfficialSelectedCurrenciesAgriculture = findViewById<LinearLayout>(R.id.ll_official_selected_currencies_agricultire)
        val llOfficialSelectedNaturalGas = findViewById<LinearLayout>(R.id.ll_official_selected_natural_gas)
        val llOfficialSelectedStockMetals = findViewById<LinearLayout>(R.id.ll_official_selected_Stock_Metals)
        val llOfficialSelectedPetroleumTreasuries = findViewById<LinearLayout>(R.id.ll_official_selected_petroleum_treasuries)
        val civInformationSynthetic = findViewById<CardView>(R.id.civ_information_synthetic)

        rgReports.setOnCheckedChangeListener { _, checkedId:Int ->
            if (checkedId == R.id.rbOfficial){
                llSelectedSynthetic.visibility = View.GONE
                civInformationSynthetic?.visibility = View.GONE
                llOfficialSelectedCurrenciesAgriculture?.visibility = View.VISIBLE
                llOfficialSelectedNaturalGas?.visibility = View.VISIBLE
                llOfficialSelectedStockMetals?.visibility=View.VISIBLE
               llOfficialSelectedPetroleumTreasuries?.visibility =View.VISIBLE
            }else{
                civInformationSynthetic?.visibility = View.VISIBLE
                llSelectedSynthetic?.visibility = View.VISIBLE
                llOfficialSelectedCurrenciesAgriculture?.visibility = View.GONE
                llOfficialSelectedNaturalGas?.visibility = View.GONE
                llOfficialSelectedStockMetals?.visibility = View.GONE
                llOfficialSelectedPetroleumTreasuries?.visibility = View.GONE
            }

        }

        adapterCOTReports.onItemClickListener =
            object :CotReportsItemsAdapter.OnItemClickListener{
                override fun onClick(position: Int, model: COTItem) {
                    // ToDo intent to activity with analyses
                }

            }
    }

    private fun setUpActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_cotreports_activity)
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

    //This information has to be loaded from api data. This hardcoded lists are for testing purposes
    private fun currenciesItemList(): MutableList<COTItem>{
        val itemCurrenciesList: MutableList<COTItem> = mutableListOf()
        itemCurrenciesList.add(COTItem("AUSTRALIAN DOLLAR (CME)"))
        itemCurrenciesList.add(COTItem("BITCOIN (CME)"))
        itemCurrenciesList.add(COTItem("BRITISH POUND (CME)"))
        itemCurrenciesList.add(COTItem("CANADIAN DOLLAR (CME)"))
        itemCurrenciesList.add(COTItem("ETHER CASH SETTLED (CME)"))
        itemCurrenciesList.add(COTItem("EURO FX (CME)"))
        itemCurrenciesList.add(COTItem("JAPANESE YEN (CME)"))
        itemCurrenciesList.add(COTItem("MEXICAN PESO (CME)"))
        itemCurrenciesList.add(COTItem("NEW ZEALAND DOLLAR (CME)"))
        itemCurrenciesList.add(COTItem("SWISS FRANC (CME)"))
        itemCurrenciesList.add(COTItem("USD INDEX (ICUS)"))
        return itemCurrenciesList
    }

    private fun agricultureItemList(): MutableList<COTItem> {
        val itemAgricultureList: MutableList<COTItem> = mutableListOf()
        itemAgricultureList.add(COTItem("COCOA (ICUS)"))
        itemAgricultureList.add(COTItem("COFFEE (ICUS)"))
        itemAgricultureList.add(COTItem("CORN (CBT)"))
        itemAgricultureList.add(COTItem("COTTON (ICUS)"))
        itemAgricultureList.add(COTItem("FEEDER CATTLE (CME)"))
        itemAgricultureList.add(COTItem("LEAN HOGS (CME)"))
        itemAgricultureList.add(COTItem("LIVE CATTLE (CME)"))
        itemAgricultureList.add(COTItem("MILK, CLASS ||| (CME)"))
        itemAgricultureList.add(COTItem("ORANGE JUICE (ICUS)"))
        itemAgricultureList.add(COTItem("ROUGH RICE (CBT)"))
        itemAgricultureList.add(COTItem("SOYBEANS (CBT)"))
        itemAgricultureList.add(COTItem("SUGAR NO. 11 (ICUS)"))
        itemAgricultureList.add(COTItem("WHEAT-SRW (CBT)"))
        return itemAgricultureList
    }

    private fun metalsAndOtherItemList(): MutableList<COTItem>{
        val metalsAndOtherList: MutableList<COTItem> = mutableListOf()
        metalsAndOtherList.add(COTItem("COPPER (CMX)"))
        metalsAndOtherList.add(COTItem("GOLD (CMX)"))
        metalsAndOtherList.add(COTItem("LUMBER (CME)"))
        metalsAndOtherList.add(COTItem("PALLADIUM (NYME)"))
        metalsAndOtherList.add(COTItem("PLATINUM (NYME)"))
        metalsAndOtherList.add(COTItem("SILVER (CMX)"))
        return metalsAndOtherList
    }

    private fun stockIndexesItemList(): MutableList<COTItem>{
        val stockIndexesList: MutableList<COTItem> = mutableListOf()
        stockIndexesList.add(COTItem("DJIA Consolidated (CBT)"))
        stockIndexesList.add(COTItem("NASDAQ 100 Consolidated (CME)"))
        stockIndexesList.add(COTItem("RUSEEL 2000 MINI (CME)"))
        stockIndexesList.add(COTItem("S&P 500 Consolidated (CME)"))
        stockIndexesList.add(COTItem("VIX (E)"))
        return  stockIndexesList
    }

    private fun petroleumProductsItemList(): MutableList<COTItem>{
        val petroleumProducts: MutableList<COTItem> = mutableListOf()
        petroleumProducts.add(COTItem("BRENT LAST DAY (NYME)"))
        petroleumProducts.add(COTItem("CRUDE OIL, SWEET LIGHT (ICEU)"))
        petroleumProducts.add(COTItem("GASOLINE RBOB (NYME)"))
        petroleumProducts.add(COTItem("WTI-PHYSICAL (NYME)"))
        return petroleumProducts
    }

    private fun treasuriesRatesItemList(): MutableList<COTItem>{
        val treasuriesRatesList: MutableList<COTItem> = mutableListOf()
        treasuriesRatesList.add(COTItem("FED FUNDS (CBT)"))
        treasuriesRatesList.add(COTItem("US TREASURY 10Y NOTE (CBT)"))
        treasuriesRatesList.add(COTItem("US TREASURY 5Y NOTE  (CBT)"))
        treasuriesRatesList.add(COTItem("US TREASURY BOND (CBT)"))
        return treasuriesRatesList
    }

    private fun naturalGasProductsItemList(): MutableList<COTItem>{
        val naturalGasProducts: MutableList<COTItem> = mutableListOf()
        naturalGasProducts.add(COTItem("NATURAL GAS (NYME)"))
        return naturalGasProducts
    }

    private fun syntheticCurrenciesItemList(): MutableList<COTItem>{
        val syntheticCurrenciesList: MutableList<COTItem> = mutableListOf()

        syntheticCurrenciesList.add(COTItem("AUSTRALIAN DOLLAR toCAD"))
        syntheticCurrenciesList.add(COTItem("AUSTRALIAN DOLLAR toCAD"))
        syntheticCurrenciesList.add(COTItem("AUSTRALIAN DOLLAR toCAD"))
        syntheticCurrenciesList.add(COTItem("AUSTRALIAN DOLLAR toCAD"))

        return syntheticCurrenciesList
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.homePageButton ->{
                startActivity(Intent(this@COTReportsActivity,MainActivity::class.java))
            }
            R.id.nav_my_profile ->{
                startActivity(Intent(this@COTReportsActivity,MyProfileActivity::class.java))
            }
            R.id.nav_sign_out ->{
                signOutDialog()
            }
            R.id.nav_favourites ->{
                favoriteDialog()
            }
            R.id.nav_calendar ->{
                startActivity(Intent(this@COTReportsActivity,CalendarActivity::class.java))
            }
            R.id.nav_cot ->{
                startActivity(Intent(this@COTReportsActivity,COTReportsActivity::class.java))
            }
            R.id.nav_rankings ->{
                startActivity(Intent(this@COTReportsActivity,RankingsActivity::class.java))
            }
            R.id.nav_break_even ->{
                startActivity(Intent(this@COTReportsActivity,BreakEvenActivity::class.java))
            }
            R.id.nav_quantum_screener ->{
                startActivity(Intent(this@COTReportsActivity,QuantumScreenerActivity::class.java))
            }
            R.id.nav_subscription ->{

            }
        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOutDialog(){
        val dialog = Dialog(this@COTReportsActivity)

        dialog.setContentView(R.layout.dialog_sign_out)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            mSharedPreferences.edit().clear().apply()

            val intent = Intent(this@COTReportsActivity,IntroActivity::class.java)
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