package com.android.farmist.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentFarmInfoBinding
import com.android.farmist.model.setFarm.DeleteFarmRespo
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.customedialoug.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_Farm_Info : Fragment() {

    var farmID: String? = ""
    var farmName: String? = ""
    var farmArea: String? = ""
    var farmAreaType: String? = ""
    var farmTehsil: String? = ""
    var farmSurvey: String? = ""
    var farmImage: String? = ""


    lateinit var progress: progressbars

    lateinit var binding: FragmentFarmInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment__farm__info, container, false)

        farmID = arguments?.getString("FarmID", "")
        farmName = arguments?.getString("FarmName", "")
        farmArea = arguments?.getString("FarmArea", "")
        farmAreaType = arguments?.getString("FarmAreaType", "")
        farmTehsil = arguments?.getString("FarmTehsil", "")
        farmSurvey = arguments?.getString("FarmSurvey", "")
        farmImage = arguments?.getString("FarmImg", "")

        progress = progressbars(requireActivity())

        setData()

        binding.ivDeleteFarmInfo.setOnClickListener { delete(farmID.toString()) }
        binding.ivEditFarmInfo.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragment_Farm_Info_to_fragment_editFarmDetails,
                null
            )
        }


        return binding.root
    }

    private fun setData() {
        binding.tvNameFarmInfo.setText(farmName)
        binding.tvAreaFarmInfo.setText(farmArea)
        binding.tvAreaTypeFarmInfo.setText(farmArea)
        binding.tvTehsilFarmInfo.setText(farmTehsil)
        binding.tvSurveyFarmInfo.setText(farmSurvey)
        Glide.with(activity?.applicationContext!!).load(farmImage).into(binding.imgFarmInfo)
    }

    fun delete(idd: String) {
        val view = View.inflate(context, R.layout.customedialoug, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.color.transe)

        view.yes.setOnClickListener {

            dialog.dismiss()

            progress.showDialog()
            Api_Controller().getInstacne().deleteFarm(idd)
                .enqueue(object : Callback<DeleteFarmRespo> {
                    override fun onResponse(
                        call: Call<DeleteFarmRespo>,
                        response: Response<DeleteFarmRespo>
                    ) {
                        var respon = response.body()
                        Toast.makeText(context, "${idd}", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_fragment_Farm_Info_to_myFarm_Fragment,
                            null
                        )
                        findNavController().restoreState(null)
                        progress.hidediloag()
                    }

                    override fun onFailure(
                        call: Call<DeleteFarmRespo>,
                        t: Throwable
                    ) {
                        Toast.makeText(context, "not success $t", Toast.LENGTH_SHORT).show()

                        progress.hidediloag()
                    }
                })


        }
        view.no.setOnClickListener {

            dialog.dismiss()

            Toast.makeText(context, "cancel request", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPause() {

        super.onPause()
    }

}
