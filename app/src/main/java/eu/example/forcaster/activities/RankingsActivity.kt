package eu.example.forcaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.example.forcaster.R
import eu.example.forcaster.adapters.RankingItemAdapter
import eu.example.forcaster.databinding.ActivityRankingsBinding
import eu.example.forcaster.models.RankingItem

class RankingsActivity : AppCompatActivity() {

    private var binding: ActivityRankingsBinding? = null

    private var rankingNorthAmericaItemList: MutableList<RankingItem> = mutableListOf()
    private var rankingLatinAmericaItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingEuropeItemList: MutableList<RankingItem> = mutableListOf()
    private var rankingAsiaItemList: MutableList<RankingItem> = mutableListOf()

    private var rankingOceaniaItemLsit: MutableList<RankingItem> = mutableListOf()

    private lateinit var rankingAdapter: RankingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()


        rankingLatinAmericaItemList = getItemListLatinAmerica()
        val rvLatinAmericaRankingList = binding?.rvNorthAmericaRankings
        rvLatinAmericaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingNorthAmericaItemList)

        rvLatinAmericaRankingList?.adapter = rankingAdapter

        rankingNorthAmericaItemList = getItemListNorthAmerica()
        val rvNorthAmericaRankingList = binding?.rvLatinAmericaRankings
        rvNorthAmericaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingNorthAmericaItemList)

        rvNorthAmericaRankingList?.adapter = rankingAdapter


        rankingEuropeItemList = getEuropeItemList()
        val rvEuropeRankingList = binding?.rvEuropeRankings
        rvEuropeRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingEuropeItemList)

        rvEuropeRankingList?.adapter = rankingAdapter


        rankingAsiaItemList  = getAsiaItemList()
        val rvAsiaRankingList = binding?.rvAsiaRankings
        rvAsiaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingAsiaItemList)

        rvAsiaRankingList?.adapter = rankingAdapter

        rankingOceaniaItemLsit = getOceaniaItemList()
        val rvOceaniaRankingList = binding?.rvOceaniaRankings
        rvOceaniaRankingList?.layoutManager = LinearLayoutManager(this@RankingsActivity)
        rankingAdapter = RankingItemAdapter(this@RankingsActivity,rankingOceaniaItemLsit)

        rvOceaniaRankingList?.adapter = RankingItemAdapter(this@RankingsActivity,rankingOceaniaItemLsit)
    }

    private fun setUpActionBar(){
        setSupportActionBar(binding?.toolbarRankingActivity)
        val actionBar = supportActionBar
        if(actionBar!= null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_back)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarRankingActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun getItemListNorthAmerica(): MutableList<RankingItem>{
        val getListNorthAmerica: MutableList<RankingItem> = mutableListOf()
        getListNorthAmerica.add(RankingItem(R.drawable.ic_board_place_holder.toString(),
            "S&P 500","United States"))

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

    private fun getAsiaItemList(): MutableList<RankingItem>{
        val getAsiaItemList : MutableList<RankingItem> = mutableListOf()

        return getAsiaItemList
    }

    private fun getOceaniaItemList(): MutableList<RankingItem>{
        val getOceaniaItemList: MutableList<RankingItem> = mutableListOf()

        return  getOceaniaItemList
    }
}