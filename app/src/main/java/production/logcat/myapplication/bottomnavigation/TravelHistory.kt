package production.logcat.myapplication.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import production.logcat.myapplication.R
import production.logcat.myapplication.adapter.HistoryAdapter
import production.logcat.myapplication.models.HistoryModel
import production.logcat.myapplication.models.customerObject

class TravelHistory : Fragment() {

    private val travelHistoryList=ArrayList<HistoryModel>()
    private lateinit var travelHistoryRecyclerView: RecyclerView
    private lateinit var noHistoryLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_travel_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        travelHistoryRecyclerView=view.findViewById(R.id.travelListView)
        noHistoryLayout=view.findViewById(R.id.noDataDisplayLayout_History)
        noHistoryLayout.visibility=View.INVISIBLE
        getHistoryData()
    }

    private fun getHistoryData(){
        if(travelHistoryList.isNotEmpty()){travelHistoryList.clear()}
        customerObject!!.history.keys.forEach{timeStamp->
            if(customerObject!!.history[timeStamp]!!.compareTo("demoJSONString")!=0){
                val historyObject = Gson().fromJson(customerObject!!.history[timeStamp], HistoryModel::class.java)
                travelHistoryList.add(HistoryModel(historyObject.driverName, historyObject.historyDate, historyObject.historyFrom, historyObject.historyTo, historyObject.historyAmount))
            }
        }
        if(travelHistoryList.isEmpty()){
            noHistoryLayout.visibility=View.VISIBLE
        }
        else{
            travelHistoryRecyclerView.layoutManager=
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            val adapter= HistoryAdapter(travelHistoryList)
            travelHistoryRecyclerView.adapter=adapter
        }
    }
}