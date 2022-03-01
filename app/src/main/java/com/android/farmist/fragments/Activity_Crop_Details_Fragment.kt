package com.android.farmist.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.farmist.R
import com.android.farmist.UploadRequestBody
import com.android.farmist.activity.MainActivity
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.ActivityAddCropDetailsBinding
import com.android.farmist.model.addCropResponse.AddCropResponse
import com.android.farmist.model.getFarmForSpinnner.FarmsSpinner
import com.android.farmist.model.profileImgResponse.SetProfileResponse
import com.android.farmist.util.progressbars
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


class Activity_Crop_Details_Fragment : Fragment() {

    private lateinit var binding: ActivityAddCropDetailsBinding
    var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var imageURi: Uri? = null
    lateinit var cropName: String
    private var selectedImageUri: Uri? = null
    lateinit var userId: String
    lateinit var FarmsNames: List<String>
    lateinit var selectFarm: String
    lateinit var progressBarDailog: progressbars
    lateinit var farmName:String



    companion object {
        val IMAGETOKEN = 200
    }

    lateinit var date: String
    var radioValue: String = "Acers"
    var num = 0
    lateinit var preferences: SharedPreferences

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate(
            layoutInflater,
            R.layout.activity_add_crop_details,
            container,
            false
        )
        cropName= arguments?.getString("cropName").toString()
        binding.cropName.setText(cropName)

        progressBarDailog = progressbars(requireContext())
        preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        getSpinnerData()



        binding.backbtn.setOnClickListener {

            findNavController().navigate(R.id.action_activity_Crop_Details_Fragment_to_selectCropCategory)
        }

        binding.btnSaveAddCrop.setOnClickListener {



            addCrop()


        }
// counter button

        binding.decriment.setOnClickListener {

            val strValue = binding.etCounterValueAddCrop.text.toString()
            if (!strValue.isBlank()) {
                num = strValue.toInt()

            }

            if (num >= 2) {

                num--
                binding.etCounterValueAddCrop.setText(num.toString())
            } else {
                Toast.makeText(requireContext(), "Area = 1", Toast.LENGTH_SHORT).show()
            }
        }
        binding.incriment.setOnClickListener {
            val strValue = binding.etCounterValueAddCrop.text.toString()
            if (!strValue.isBlank()) {
                num = strValue.toInt()

            }
            num++
            binding.etCounterValueAddCrop.setText(num.toString())
        }

        binding.rgRadiogroupAddCrop.setOnCheckedChangeListener { group, i ->

            var radioButton: RadioButton? = view?.findViewById(i)

            when (radioButton) {
                binding.rBAcersAddCrop -> radioValue = radioButton.text.toString()
                binding.rBBhigasAddCrop -> radioValue = radioButton.text.toString()
                binding.rBGuntasAddCrop -> radioValue = radioButton.text.toString()
            }
        }

        binding.etDateAddCrop.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog =
                DatePickerDialog(requireContext(), onDateSetListener, year, month, day)
            datePickerDialog.show()

        }
        onDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

                val monthOfYear = month + 1
                date = "$dayOfMonth-$monthOfYear-$year"
                binding.etDateAddCrop.setText(date)
            }

        binding.imgAddCrop.setOnClickListener {

        }
        binding.addphoto.setOnClickListener {
            openImageChooser()

        }


        return binding.root
    }

    private fun addCrop() {
        val showedArea = binding.etCounterValueAddCrop.text.toString()
        val showedDate = binding.etDateAddCrop.text.toString()


//      image Opetation
        if (selectedImageUri == null) {
            Toast.makeText(requireContext(), "Select an Image First", Toast.LENGTH_SHORT).show()

            return
        }
        progressBarDailog.showDialog()

        val parcelFileDescriptor =
            requireContext().contentResolver.openFileDescriptor(selectedImageUri!!, "r", null)
                ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)

        val file = File(requireContext().cacheDir, "akkkkkkk")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        val body = UploadRequestBody(file, "image")
        val userIdRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), userId
        )
        val CropnameRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), cropName
        )
        val FarmnameRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), selectFarm
        )
        val SowedareaRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), showedArea
        )
        val areaTypeRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), radioValue
        )
        val SowedRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), showedDate
        )

        val call: Call<AddCropResponse>
        call = Api_Controller().getInstacne().addCrop(
            CropnameRequestBody,
            MultipartBody.Part.createFormData("image", file.name, body),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json"),
            userIdRequestBody,
            FarmnameRequestBody,
            SowedareaRequestBody,
            areaTypeRequestBody,
            SowedRequestBody


        )
        call.enqueue(object : Callback<AddCropResponse> {
            override fun onResponse(
                call: Call<AddCropResponse>,
                response: Response<AddCropResponse>
            ) {
                val responseData = response.body()

                progressBarDailog.hidediloag()
                createCropSuccess()
             }

            override fun onFailure(call: Call<AddCropResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed:${t.toString()}", Toast.LENGTH_SHORT)
                    .show()
                progressBarDailog.hidediloag()
            }
        })

    }


    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

//        cropName = binding.cropName.text.toString()
        Toast.makeText(requireActivity(), "crop name: $cropName", Toast.LENGTH_SHORT).show()

        binding.tvaddnewfarm.setOnClickListener {
            findNavController().navigate(
                R.id.action_activity_Crop_Details_Fragment_to_addFarmDetails, null
            )
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
                    selectedImageUri = data?.data
                    binding.imgAddCrop.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun getSpinnerData() {
      //  Toast.makeText(requireContext(), "IdData:$userId", Toast.LENGTH_SHORT).show()
        val call: Call<FarmsSpinner>
        call = Api_Controller().getInstacne().getSpinnerFarm(userId)
        call.enqueue(object : Callback<FarmsSpinner> {
            override fun onResponse(call: Call<FarmsSpinner>, response: Response<FarmsSpinner>) {

                FarmsNames = response.body()?.names!!


                binding.tvSpinner.adapter = activity?.let {
                    ArrayAdapter(
                        it.applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        FarmsNames
                    )
                }

                binding.tvSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectFarm = FarmsNames[p2]
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }


            }

            override fun onFailure(call: Call<FarmsSpinner>, t: Throwable) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("getspinner", t.toString())


            }
        })

    }

    fun createCropSuccess()
    {
        val sweetAlertDialog=SweetAlertDialog(requireContext(),SweetAlertDialog.SUCCESS_TYPE)
        sweetAlertDialog.confirmButtonBackgroundColor=Color.parseColor("#46A908")
        sweetAlertDialog.setConfirmClickListener {

            findNavController().navigate(R.id.action_activity_Crop_Details_Fragment_to_crops_Fragment)
        sweetAlertDialog.dismiss()
        }
        sweetAlertDialog.show()
    }

}


