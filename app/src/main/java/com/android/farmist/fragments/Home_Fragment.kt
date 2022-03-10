package com.android.farmist.fragments

import android.Manifest

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.farmist.R
import com.android.farmist.RoomDatabase.appDatabase
import com.android.farmist.adapter.*
import com.android.farmist.api.Api_Controller
import com.android.farmist.api.Api_Controller_Location
import com.android.farmist.databinding.FragmentHomeBinding
import com.android.farmist.model.CropPriceResponse.Crop
import com.android.farmist.model.CropPriceResponse.getCropPrice
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.location.Report
import com.android.farmist.model.location.Roomdata
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
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import java.time.LocalDateTime


class Home_Fragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapterNewsAnnouncements by lazy { Adapter_News_Announcements() }
    lateinit var adapterCropPrices: Adapter_Crop_Prices

    var key = "13497f7823f3433ebb161306220202"
    lateinit var addressList: ArrayList<Address>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var appDatabaseObj: appDatabase
    lateinit var appDatabaseObj2: appDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity?.applicationContext!!)
        appDatabaseObj= appDatabase.getAppDBInstance(requireContext())
        getNewsAlert()
        val list =appDatabaseObj.getAppDao().getnews().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapterNewsAnnouncements.setList(it,requireActivity())
            binding.rvnewsannouncment.adapter=adapterNewsAnnouncements
            adapterNewsAnnouncements.notifyDataSetChanged()
        })
        appDatabaseObj = appDatabase.getAppDBInstance(requireContext())
        appDatabaseObj2 = appDatabase.getAppDBInstance(requireContext())
//        val list = appDatabaseObj.getAppDao().getnews()
//            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//                adapterNewsAnnouncements.setList(it, requireActivity())
//                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
//                adapterNewsAnnouncements.notifyDataSetChanged()
//            })
        val list2 = appDatabaseObj2.getAppDao().gelAllPrice()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                adapterCropPrices.setList(it)
                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
                adapterNewsAnnouncements.notifyDataSetChanged()
            })
        val list3 = appDatabaseObj2.getAppDao().getLocation()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                val activity: Activity? = activity
                if (activity != null) {

               if (it != null) {
                   Glide.with(this@Home_Fragment)
                       .load(it.icon)
                       .into(binding.ivweather1)
                   Glide.with(requireActivity())
                       .load(it.icon1)
                       .into(binding.ivday1weather)
                   Glide.with(requireActivity())
                       .load(it.icon2)
                       .into(binding.ivtueweatherday2)
                   Glide.with(requireActivity())
                       .load(it.icon3)
                       .into(binding.ivwedweatherday3)

                   binding.tvDayDate1.setText(it.date)
                   binding.tvDayDate2.setText(it.date1)
                   binding.tvDayDate3.setText(it.date2)
                   binding.tvTodayTemp1.setText(it.tempC)
                   binding.tvTodayTempday1.setText(it.tempC)
                   binding.tvTemp2.setText(it.tempC2)
                   binding.tvTemp3.setText(it.tempC3)
               }}})



        fetchLocation()
        binding.locationbtn.setOnClickListener {
            val manager =
                requireActivity()!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

            if (!manager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps()
            }
        }

        return binding.root

    }

    fun buildAlertMessageNoGps() {


        fetchLocation()
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getGovScheme()
        getPriceCrop()
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
        binding.tvallCropPrice.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_cropPrices)
        }

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

                val saticValue = addressList.get(0).locality.toString()


                var data: Call<Report> =
                    Api_Controller_Location().getInstacne_Location().getTemp(key, saticValue, "3")
                data.enqueue(object : Callback<Report> {
                    override fun onResponse(call: Call<Report>, response: Response<Report>) {
                        val responses = response.body()
                        if (responses != null) {


                            var abc: Roomdata = Roomdata(
                                1,"https://" +responses.forecast!!.forecastday[0].day?.condition?.icon.toString(),
                                "https://" + responses.forecast!!.forecastday[0].day?.condition?.icon.toString(),
                                "https://" + responses.forecast!!.forecastday[1].day?.condition?.icon.toString(),
                                "https://" + responses.forecast!!.forecastday[2].day?.condition?.icon.toString(),
                                responses.forecast!!.forecastday[0].date.toString(),
                                responses.forecast!!.forecastday[1].date.toString(),
                                responses.forecast!!.forecastday[2].date.toString(),
                                responses.current?.tempC.toString() + "°",
                                responses.current?.tempC.toString() + "°",
                                responses.forecast!!.forecastday[1].hour[13].tempC.toString() + "°",
                                responses.forecast!!.forecastday[2].hour[11].tempC.toString() + "°"
                            )

                            appDatabaseObj.getAppDao().deleteLocation()
                            appDatabaseObj.getAppDao().insertLocation(abc)


                            val activity: Activity? = activity
                            if (activity != null) {

                                Glide.with(this@Home_Fragment)
                                    .load(abc.icon)
                                    .into(binding.ivweather1)
                                Glide.with(requireActivity())
                                    .load(abc.icon1)
                                    .into(binding.ivday1weather)
                                Glide.with(requireActivity())
                                    .load(abc.icon2)
                                    .into(binding.ivtueweatherday2)
                                Glide.with(requireActivity())
                                    .load(abc.icon3)
                                    .into(binding.ivwedweatherday3)

                            }

                            binding.tvDayDate1.setText(abc.date)
                            binding.tvDayDate2.setText(abc.date1)
                            binding.tvDayDate3.setText(abc.date2)
                            binding.tvTodayTemp1.setText(abc.tempC)
                            binding.tvTodayTempday1.setText(abc.tempC)
                            binding.tvTemp2.setText(abc.tempC2)
                            binding.tvTemp3.setText(abc.tempC3)



//                                Glide.with(this@Home_Fragment)
//                                    .load("https://" + responses.forecast!!.forecastday[0].day?.condition?.icon)
//                                    .into(binding.ivweather1)
//                                Glide.with(requireActivity())
//                                    .load("https://" + responses.forecast!!.forecastday[0].day?.condition?.icon)
//                                    .into(binding.ivday1weather)
//                                Glide.with(requireActivity())
//                                    .load("https://" + responses.forecast!!.forecastday[1].day?.condition?.icon)
//                                    .into(binding.ivtueweatherday2)
//                                Glide.with(requireActivity())
//                                    .load("https://" + responses.forecast!!.forecastday[2].day?.condition?.icon)
//                                    .into(binding.ivwedweatherday3)
//
//                            }
//
//                            binding.tvDayDate1.setText(responses.forecast!!.forecastday[0].date.toString())
//                            binding.tvDayDate2.setText(responses.forecast!!.forecastday[1].date.toString())
//                            binding.tvDayDate3.setText(responses.forecast!!.forecastday[2].date.toString())
//                            binding.tvTemp2.setText(responses.forecast!!.forecastday[1].hour[12].tempC.toString() + "°")
//                            binding.tvTemp3.setText(responses.forecast!!.forecastday[2].hour[14].tempC.toString() + "°")


                        }

                    }

                    override fun onFailure(call: Call<Report>, t: Throwable) {
                        Toast.makeText(requireActivity(), "no $t", Toast.LENGTH_SHORT).show()
                    }
                })


            } else {
                Log.d("","")

            }

        }

    }
    private fun getNewsAlert() {
        val call: Call<GetNewsAlert>
        call= Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {


                val   adapterAlertsNews=
                    response.body()?.let { activity?.applicationContext?.let { it1 ->
                        Adapter_Alerts_News(
                        )
                        adapterNewsAnnouncements.setList(it.news,it1.applicationContext)
                    } }

                val dataRespo= response.body()?.news
                appDatabaseObj.getAppDao().deleteAllRecords()
                appDatabaseObj.getAppDao().insertAll(dataRespo!!)
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError",t.toString())
            }
        })
    }
    private fun getGovScheme() {
        val call: Call<GetNewsAlert>
        call = Api_Controller().getInstacneAdmin().getNewsAlert()
        call.enqueue(object : Callback<GetNewsAlert> {
            override fun onResponse(call: Call<GetNewsAlert>, response: Response<GetNewsAlert>) {
                val responseList=response.body()?.news


                binding.rvnewsannouncment.adapter = adapterNewsAnnouncements
                if (responseList != null) {
                    val activity: Activity? = activity
                    if (activity != null) {
                        adapterNewsAnnouncements.setList(responseList, requireActivity())
                    }
                }
            }

            override fun onFailure(call: Call<GetNewsAlert>, t: Throwable) {
                Log.d("getNewsError", t.toString())
            }
        })
    }




    private fun getPriceCrop() {

        adapterCropPrices = activity?.let {
            Adapter_Crop_Prices(it.applicationContext, ArrayList<Crop>())
        }!!
        binding.rvcropprices.setHasFixedSize(true)
//        binding.rvcropprices.layoutManager=
//            GridLayoutManager(activity?.applicationContext,2)

        binding.rvcropprices.adapter = adapterCropPrices
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> getObserver(response as getCropPrice) },
                    { t -> onFailure(t) })
        )

    }

    private fun getObservable(): Observable<getCropPrice> {
        return Api_Controller.apiInterface.getCropPrice()
    }

    private fun getObserver(priceData: getCropPrice) {
        if (priceData != null) {
            val CropPriceList = priceData.crops

            adapterCropPrices.setList(CropPriceList)
            appDatabaseObj2.getAppDao().deleteAllPrice()
            appDatabaseObj2.getAppDao().insertPrice(CropPriceList)

        }


    }

    private fun onFailure(t: Throwable) {
        Log.d("Main", "OnFailure: " + t.message)
    }
    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
//        findNavController().navigate(R.id.nav_home)
    }


}