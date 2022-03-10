package com.android.farmist.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.farmist.R
import com.android.farmist.adapter.AdapterUpcommingAction
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentUpcomingActionBinding
import com.android.farmist.model.upcommingAction.UpcomingDates
import com.android.farmist.model.upcommingAction.Upcomingrespo
import kotlinx.android.synthetic.main.activity_add_crop_details.*
import kotlinx.android.synthetic.main.fragment_upcoming_action.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import java.util.*
import kotlin.collections.ArrayList
import com.applandeo.materialcalendarview.EventDay

import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener





class UpcomingAction : Fragment() {

    lateinit var binding: FragmentUpcomingActionBinding
    lateinit var userId: String
    lateinit var a: String
    var finalHarDate: String = "1"
    var finalferDate: String = "1"
    var finalHarmonth: String = "3"
    var finalfermonth: String = "3"
    var finalHarYear: String = "22"
    var finalferYear: String = "22"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_upcoming_action,
            container,
            false
        )


        var preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()
        getItem()

//         val sdf = SimpleDateFormat("dd/M/yyyy")
//         val currentDate = sdf.format(Date())
//         Toast.makeText(requireActivity(), "$currentDate", Toast.LENGTH_SHORT).show()


        getdate()



        return binding.root
    }


    fun event(date: String) {

        val parts = date.split("/").toTypedArray()
        val day = parts[0].toInt()
        val month = parts[1].toInt()
        val year = parts[2].toInt()
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        val milliTime = calendar.timeInMillis
//binding.calendarViewa.setDate(milliTime, true, true)

//    val ev1 = Event(Color.BLACK, 1477040400000L, "Teachers' Professional Day")
//    binding.calendarView.setEvents(ev1)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarExp.markDate(2022,3,14)
//        getdate()

    }



    fun getItem() {

        var call: Call<Upcomingrespo> = Api_Controller().getInstacne().UpcomingFun(userId)
        call.enqueue(object : Callback<Upcomingrespo> {
            override fun onResponse(call: Call<Upcomingrespo>, response: Response<Upcomingrespo>) {

                var respo = response.body()
                if (respo != null) {
                    Log.d("userId", userId)
                    var adap = AdapterUpcommingAction(requireActivity(), respo.cropData)
                    binding.upcomingActionRecyclerView.adapter = adap
                    adap.notifyDataSetChanged()
                    binding.upcomingActionRecyclerView.layoutManager =
                        LinearLayoutManager(requireActivity())


                }
            }

            override fun onFailure(call: Call<Upcomingrespo>, t: Throwable) {
                Log.d("", "")
            }
        })
    }


    fun getdate() {

//        https://myfarmapp1.herokuapp.com/api/v1/upcoming/actions/dates?userId=61ee7d608c7deb5673cbe7f6

        var call: Call<UpcomingDates> = Api_Controller().getInstacne().UpcomingFunDate(userId)
        call.enqueue(object : Callback<UpcomingDates> {
            override fun onResponse(call: Call<UpcomingDates>, response: Response<UpcomingDates>) {

//date
                var hdate = response.body()?.harvestingDates
                var i = 0
                while (hdate != null) {
                    var date = hdate[i]
                    i++
                    if (date!![0].toInt() != 0) {
                        finalHarDate = date[0].toString() + date[1].toString()
                    } else {
                        finalHarDate = date[1].toString()
                    }
                    if (date[3].toInt() != 0) {
                        finalHarmonth = date[3].toString() + date[4].toString()
                    } else {
                        finalHarmonth = date[4].toString()
                    }
                    finalHarYear = "20" + date[8].toString() + date[9].toString()
                    var respo = response.body()
                    if (respo != null) {
                        binding.calendarExp.markDate(
                            finalHarDate.toInt(), finalHarmonth.toInt(), finalHarYear.toInt()
                        )
                        Toast.makeText(
                            requireActivity(),
                            "${
                                finalHarDate.toInt()
                            } / ${finalHarmonth.toInt()} / ${finalHarYear.toInt()}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    var fdate = response.body()?.fertilizeDates
                    var j = 0
                    while (fdate != null) {
                        var date = fdate[j]
                        j++
                        if (date!![0].toInt() != 0) {
                            finalferDate = date[0].toString() + date[1].toString()
                        } else {
                            finalferDate = date[1].toString()
                        }
                        if (date[3].toInt() != 0) {
                            finalfermonth = date[3].toString() + date[4].toString()
                        } else {
                            finalfermonth = date[4].toString()
                        }
                        finalHarYear = "20" + date[8].toString() + date[9].toString()
                        var respo = response.body()
                        if (respo != null) {
                            binding.calendarExp.markDate(
                                finalHarDate.toInt(), finalHarmonth.toInt(), finalHarYear.toInt()
                            )
                            Toast.makeText(
                                requireActivity(),
                                "${
                                    finalHarDate.toInt()
                                } / ${finalHarmonth.toInt()} / ${finalHarYear.toInt()}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }


//                                            var adap = AdapterUpcommingAction(requireActivity(), respo.cropData)
//                    binding.upcomingActionRecyclerView.adapter = adap
//                    adap.notifyDataSetChanged()
//                    binding.upcomingActionRecyclerView.layoutManager =
//                        LinearLayoutManager(requireActivity())
                    }

                }

            }

            override fun onFailure(call: Call<UpcomingDates>, t: Throwable) {
                Log.d("", "")
            }
        })
    }
}