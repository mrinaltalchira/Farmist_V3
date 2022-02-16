package com.android.farmist.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.farmist.R
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.*
import com.android.farmist.api.Api_Controller
import com.android.farmist.api.Api_Controller_Location
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.model.location.Report
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class Home_Fragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val adapterNewsAnnouncements by lazy { Adapter_News_Announcements() }
    private val adapterCropPrices by lazy { Adapter_Crop_Prices() }
    private var createGroupList : ArrayList<String> = ArrayList()
    private var croppricelist : ArrayList<String> = ArrayList()
    var key = "13497f7823f3433ebb161306220202"
    lateinit var addressList: ArrayList<Address>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home,container,false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity?.applicationContext!!)

        fetchLocation()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bindUIViews()
        getGovScheme()
        binding.expIncometracker.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_expensess_Income_tracker,null)
        })

        binding.crop.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_crops_Fragment,null)
        })


        binding.myfarms.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.myFarm_Fragment,null)
        })

        binding.upcomingaction.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_upcomingAction, null)
        })

        binding.loansubsidies.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_loan_Subsidies_Fragment,null)
        })


        binding.farmstates.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_fragment_Farm_States,null)
        })

        binding.tvallnewsannouncement.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_news_Announcement_all_Fragment,null)
        })

    }

    private fun fetchLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                activity?.applicationContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    activity?.applicationContext!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                val lat = it.latitude.toString()
                val lon = it.longitude.toString()

                var geocoder = Geocoder(requireActivity(), Locale.getDefault())

                addressList = geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1
                ) as ArrayList<Address>
                Log.d("ddddd", addressList.get(0).getAddressLine(0).toString())
                val saticValue = addressList.get(0).locality.toString()


                var data: Call<Report> =
                    Api_Controller_Location().getInstacne_Location().getTemp(key, saticValue, "3")
                data.enqueue(object : Callback<Report> {
                    override fun onResponse(call: Call<Report>, response: Response<Report>) {
                        val responses = response.body()
                        if (responses != null) {

                            binding.tvTodayTemp1.setText(responses.current?.tempC.toString() + "°")
                            binding.tvTodayTempday1.setText(responses.current?.tempC.toString() + "°")

                            Glide.with(activity!!.applicationContext)
                                .load("https://" + responses.forecast!!.forecastday[0].day?.condition?.icon)
                                .into(binding.ivweather1)
                            binding.tvDayDate1.setText(responses.forecast!!.forecastday[0].date.toString())
                            binding.tvDayDate2.setText(responses.forecast!!.forecastday[1].date.toString())
                            binding.tvDayDate3.setText(responses.forecast!!.forecastday[2].date.toString())
                            binding.tvTemp2.setText(responses.forecast!!.forecastday[1].hour[11].tempC.toString() + "°")
                            binding.tvTemp3.setText(responses.forecast!!.forecastday[2].hour[11].tempC.toString() + "°")
                            Glide.with(requireActivity())
                                .load("https://" + responses.forecast!!.forecastday[0].day?.condition?.icon)
                                .into(binding.ivday1weather)
                            Glide.with(requireActivity())
                                .load("https://" + responses.forecast!!.forecastday[1].day?.condition?.icon)
                                .into(binding.ivtueweatherday2)
                            Glide.with(requireActivity())
                                .load("https://" + responses.forecast!!.forecastday[2].day?.condition?.icon)
                                .into(binding.ivwedweatherday3)


                        }

                    }

                    override fun onFailure(call: Call<Report>, t: Throwable) {
                        Toast.makeText(requireActivity(), "no $t", Toast.LENGTH_SHORT).show()
                    }
                })


            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Please Grant Location Permission",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }

    private fun getGovScheme() {
        val call: Call<GetNewsAlert>
        call = Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {
                val responseList=response.body()?.news


                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
                if (responseList != null) {
                    adapterNewsAnnouncements.setList(responseList,requireContext())
                }
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }



}