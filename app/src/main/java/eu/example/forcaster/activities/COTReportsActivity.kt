package eu.example.forcaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import eu.example.forcaster.R
import eu.example.forcaster.adapters.CotReportsItemsAdapter
import eu.example.forcaster.databinding.ActivityCotreportsBinding
import eu.example.forcaster.models.COTItem

class COTReportsActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCotreportsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setUpActionBar()
        currenciesList = currenciesItemList()
        val rvCurrenciesList = binding?.rvCurrenciesCotreports
        rvCurrenciesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,currenciesList)

        rvCurrenciesList?.adapter = adapterCOTReports


        agricultureList = agricultureItemList()
        val rvAgricultureList = binding?.rvAgricultureCotreports
        rvAgricultureList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,agricultureList)

        rvAgricultureList?.adapter = adapterCOTReports


        metalsAndOtherList = metalsAndOtherItemList()
        val rvMetalsAndOtherList = binding?.rvMetalsandOtherCotreports
        rvMetalsAndOtherList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,metalsAndOtherList)

        rvMetalsAndOtherList?.adapter = adapterCOTReports


        stockIndexesList = stockIndexesItemList()
        val rvStockIndexesList = binding?.rvStockIndexesCotreports
        rvStockIndexesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,stockIndexesList)

        rvStockIndexesList?.adapter = adapterCOTReports


        petroleumProductsList = petroleumProductsItemList()
        val rvPetroleumProductsList = binding?.rvPetroleumProductsCotreports
        rvPetroleumProductsList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,petroleumProductsList)

        rvPetroleumProductsList?.adapter = adapterCOTReports

        treasuriesRatesList = treasuriesRatesItemList()
        val rvTreasuriesRatesList = binding?.rvTreasuriesandRatesCotreports
        rvTreasuriesRatesList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,treasuriesRatesList)

        rvTreasuriesRatesList?.adapter = adapterCOTReports

        naturalGasProductsList = naturalGasProductsItemList()
        val rvNaturalGasProducts = binding?.rvNaturalGasProductsCotreports
        rvNaturalGasProducts?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,naturalGasProductsList)

        rvNaturalGasProducts?.adapter = adapterCOTReports

        syntheticCurrenciesList = syntheticCurrenciesItemList()
        val rvSyntheticCurrencyList = binding?.rvSyntheticCurrenciesCotreports
        rvSyntheticCurrencyList?.layoutManager = LinearLayoutManager(this@COTReportsActivity)
        adapterCOTReports = CotReportsItemsAdapter(this@COTReportsActivity,syntheticCurrenciesList)

        rvSyntheticCurrencyList?.adapter = adapterCOTReports

        binding?.rgReports?.setOnCheckedChangeListener { _, checkedId:Int ->
            if (checkedId == R.id.rbOfficial){
                binding?.llSelectedSynthetic?.visibility = View.GONE
                binding?.civInformationSynthetic?.visibility = View.GONE
                binding?.llOfficialSelectedCurrenciesAgricultire?.visibility = View.VISIBLE
                binding?.llOfficialSelectedNaturalGas?.visibility = View.VISIBLE
                binding?.llOfficialSelectedStockMetals?.visibility=View.VISIBLE
                binding?.llOfficialSelectedPetroleumTreasuries?.visibility =View.VISIBLE
            }else{
                binding?.civInformationSynthetic?.visibility = View.VISIBLE
                binding?.llSelectedSynthetic?.visibility = View.VISIBLE
                binding?.llOfficialSelectedCurrenciesAgricultire?.visibility = View.GONE
                binding?.llOfficialSelectedNaturalGas?.visibility = View.GONE
                binding?.llOfficialSelectedStockMetals?.visibility = View.GONE
                binding?.llOfficialSelectedPetroleumTreasuries?.visibility = View.GONE
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
        setSupportActionBar(binding?.toolbarCotreportsActivity)
        val action = supportActionBar
        if(action!=null){
            action.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            action.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarCotreportsActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
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
}