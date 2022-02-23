package com.android.farmist.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.UploadRequestBody
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentEditFarmDetailsBinding
import com.android.farmist.model.setFarm.GetFarmEditResponce
import com.android.farmist.model.setFarm.updateDataRespo
import com.android.farmist.util.progressbars
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.personal_information.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Fragment_editFarmDetails : Fragment() {

    var id: String? = ""
    lateinit var name: String
    lateinit var area: String
    lateinit var areaType: String
    lateinit var tehsil: String
    lateinit var surveyNum: String
    var imageURi: Uri? = null
    lateinit var binding: FragmentEditFarmDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_farm_details, container, false)


        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_editFarmDetails_to_myFarm_Fragment)
        }

        id = arguments?.getString("EditFarmID")


        getUserData(id.toString())



        binding.rbGroupUpdateMyFarm.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_Acres_Update_myFarm) {
                areaType = binding.rbAcresUpdateMyFarm.text.toString()

            } else
                if (checkedId == R.id.rb_Guntas_Update_myFarm) {
                    areaType = binding.rbGuntasUpdateMyFarm.text.toString()

                } else
                    if (checkedId == R.id.rb_bigha_Update_myFarm) {
                        areaType = binding.rbBighaUpdateMyFarm.text.toString()
                    } else {
                        return@setOnCheckedChangeListener
                    }

        }

        binding.imgUpdateMyFarm.setOnClickListener { openImageChooser() }
        binding.BtnUpdateMyFarm.setOnClickListener {

            name = binding.etNameUpdateMyFarm.text.toString()
            area = binding.etAreaUpdateMyFarm.text.toString()
            tehsil = binding.etTehsilUpdateMyFarm.text.toString()
            surveyNum = binding.etSurveyUpdateMyFarm.text.toString()

            if (name.isNullOrEmpty()) {

                binding.etNameUpdateMyFarm.error = "fill this field"
                binding.etNameUpdateMyFarm.requestFocus()
                return@setOnClickListener
            }
            if (area.isNullOrEmpty()) {

                binding.etAreaUpdateMyFarm.error = "fill this field"
                binding.etAreaUpdateMyFarm.requestFocus()
                return@setOnClickListener
            }

            if (tehsil.isNullOrEmpty()) {

                binding.etTehsilUpdateMyFarm.error = "fill this field"
                binding.etTehsilUpdateMyFarm.requestFocus()
                return@setOnClickListener
            }
            if (surveyNum.isNullOrEmpty()) {

                binding.etSurveyUpdateMyFarm.error = "fill this field"
                binding.etSurveyUpdateMyFarm.requestFocus()
                return@setOnClickListener
            }
            if (areaType.isNullOrEmpty()) {
                return@setOnClickListener
            }

            updateUserData()
        }


        return binding.root
    }

    override fun onDetach() {

        super.onDetach()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
    }


    private fun updateUserData() {

        if (imageURi == null) {
            Toast.makeText(activity?.applicationContext, "Insert image first", Toast.LENGTH_SHORT)
                .show()
            return
        }

        Toast.makeText(activity?.applicationContext, "enter updateuser ", Toast.LENGTH_SHORT).show()
        var progressBars = progressbars(requireActivity())
        progressBars.showDialog()

        val nameRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), name
        )
        val areaRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), area
        )
        val areaTypeRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), areaType
        )
        val tehshilRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), tehsil
        )
        val surveyNumRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), surveyNum
        )


        val parcelFileDescriptor =
            activity?.applicationContext?.contentResolver?.openFileDescriptor(imageURi!!, "r", null)
                ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        imageURi.toString()
        val file = File(activity?.applicationContext?.cacheDir, "ddd")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file, "image")

        val call: Call<updateDataRespo>
        call = Api_Controller().getInstacne().updateFarm(

            id.toString(),
            MultipartBody.Part.createFormData("image", file.name, body),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json"),
            nameRequestBody,
            areaRequestBody,
            areaTypeRequestBody,
            tehshilRequestBody,
            surveyNumRequestBody

        )

        call.enqueue(object : Callback<updateDataRespo> {
            override fun onResponse(
                call: Call<updateDataRespo>,
                response: Response<updateDataRespo>
            ) {
                val updateData = response.body()
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity?.applicationContext,
                        "update Successful:${updateData.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBars.hidediloag()
                }

            }

            override fun onFailure(call: Call<updateDataRespo>, t: Throwable) {
                Log.d("updateError", t.toString())
                progressBars.hidediloag()
            }
        })

    }


    // -----------------------------------------------------------------------------------


    private fun getUserData(idd: String) {


        val call: Call<GetFarmEditResponce>
        call = Api_Controller().getInstacne().getFarmForEdit(idd)
        call.enqueue(object : Callback<GetFarmEditResponce> {
            override fun onResponse(
                call: Call<GetFarmEditResponce>,
                response: Response<GetFarmEditResponce>
            ) {
                val userData = response.body()
                if (response.isSuccessful) {
                    setUserdata(userData!!)
                }
            }

            override fun onFailure(call: Call<GetFarmEditResponce>, t: Throwable) {
                Log.d("getUserDataError", t.toString())
            }
        })
    }

    private fun setUserdata(uerdata: GetFarmEditResponce) {
        if (uerdata != null) {
            var A = binding.rbAcresUpdateMyFarm.text.toString()
            var B = binding.rbBighaUpdateMyFarm.text.toString()
            var G = binding.rbGuntasUpdateMyFarm.text.toString()

                Glide.with(requireActivity()).load(uerdata.farm.image)
                .into(binding.imgUpdateMyFarm)
            binding.etNameUpdateMyFarm.setText(uerdata.farm.name)
            binding.etAreaUpdateMyFarm.setText(uerdata.farm.area)
            binding.rbGroupUpdateMyFarm.checkedRadioButtonId
            binding.etTehsilUpdateMyFarm.setText(uerdata.farm.tehsil)
            binding.etSurveyUpdateMyFarm.setText(uerdata.farm.surveyNum)

            if (uerdata.farm.areaType == A) {
                binding.rbGroupUpdateMyFarm.check(R.id.rb_Acres_Update_myFarm)
            } else
                if (uerdata.farm.areaType == B) {
                    binding.rbGroupUpdateMyFarm.check(R.id.rb_bigha_Update_myFarm)
                } else
                    if (uerdata.farm.areaType == G) {
                        binding.rbGroupUpdateMyFarm.check(R.id.rb_Guntas_Update_myFarm)
                    }

        }

    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, Account_Fragment.IMAGETOKEN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Account_Fragment.IMAGETOKEN -> {
                    imageURi = data?.data
                    binding.imgUpdateMyFarm.setImageURI(imageURi)
                }
            }
        }
    }


}
