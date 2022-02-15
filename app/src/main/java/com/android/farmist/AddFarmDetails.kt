package com.android.farmist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.AddFarmDetailBinding
import com.android.farmist.model.setFarm.setFarm
import com.android.farmist.util.progressbars
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class AddFarmDetails : AppCompatActivity() {
    lateinit var userId: String
    lateinit var name: String
    lateinit var area: String
    lateinit var areaType: String
    lateinit var tehsil: String
    lateinit var surveyNum: String
    lateinit var preferences: SharedPreferences
    var imageURi: Uri? = null
    lateinit var progress: progressbars

    lateinit var binding: AddFarmDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = AddFarmDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progress = progressbars(this)
        preferences = getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()

        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show()
        binding.rgRadiogroupAddFarm.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.r_b_Acers_addFarm) {
                areaType = binding.rBAcersAddFarm.text.toString()
                Toast.makeText(this, areaType, Toast.LENGTH_SHORT).show()

            } else
                if (checkedId == R.id.r_b_Guntas_addFarm) {
                    areaType = binding.rBGuntasAddFarm.text.toString()

                    Toast.makeText(
                        this,
                        binding.rBGuntasAddFarm.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    if (checkedId == R.id.r_b_Bhigas_addFarm) {
                        areaType = binding.rBBhigasAddFarm.text.toString()

                        Toast.makeText(this, areaType, Toast.LENGTH_SHORT).show()
                    }
        }


        binding.imgAddFarm.setOnClickListener {
            getImage()
        }


        binding.btnSaveAddFarm.setOnClickListener {


            name = binding.tvNameAddFarm.text.toString()
            area = binding.tvAreaAddFarm.text.toString()
            tehsil = binding.tvTehsilAddFarm.text.toString()
            surveyNum = binding.tvSurveyNumAddFarm.text.toString()



            if (name.isNullOrEmpty()) {

                binding.tvNameAddFarm.error = "fill this field"
                binding.tvNameAddFarm.requestFocus()
                return@setOnClickListener
            }
            if (area.isNullOrEmpty()) {

                binding.tvAreaAddFarm.error = "fill this field"
                binding.tvAreaAddFarm.requestFocus()
                return@setOnClickListener
            }
            if (tehsil.isNullOrEmpty()) {

                binding.tvTehsilAddFarm.error = "fill this field"
                binding.tvTehsilAddFarm.requestFocus()
                return@setOnClickListener
            }
            if (surveyNum.isNullOrEmpty()) {

                binding.tvSurveyNumAddFarm.error = "fill this field"
                binding.tvSurveyNumAddFarm.requestFocus()
                return@setOnClickListener
            }
            if (userId.isNullOrEmpty() && areaType.isNullOrEmpty() && binding.imgAddFarm != null) {
                return@setOnClickListener
            }

            addFarmDetails()

        }

    }

    fun addFarmDetails() {

        if (imageURi == null) {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_SHORT).show()

            return
        }
        progress.showDialog()
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(imageURi!!, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        imageURi.toString()
        val file = File(this.cacheDir, "dddd")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val userIdRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), userId
        )
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
        val body = UploadRequestBody(file, "image")
        val call: Call<setFarm> = Api_Controller().getInstacne().addNewFarmInter(
            userIdRequestBody,
            MultipartBody.Part.createFormData("image", file.name, body),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json"),
            nameRequestBody,
            areaRequestBody,
            areaTypeRequestBody,
            tehshilRequestBody,
            surveyNumRequestBody
        )
        call.enqueue(object : Callback<setFarm> {
            override fun onResponse(call: Call<setFarm>, response: Response<setFarm>) {

                val setProfileResponse = response.body()
                Toast.makeText(
                    this@AddFarmDetails,
                    "${setProfileResponse.toString()} image upload successfully",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("addfarmdata", setProfileResponse.toString())
                binding.tvNameAddFarm.text.clear()
                binding.tvAreaAddFarm.text.clear()
                binding.rgRadiogroupAddFarm.clearCheck()
                binding.tvTehsilAddFarm.text.clear()
                binding.tvSurveyNumAddFarm.text.clear()
                imageURi = null
                binding.imgAddFarm.setImageResource(R.drawable.cow)
                progress.hidediloag()

            }

            override fun onFailure(call: Call<setFarm>, t: Throwable) {
                Toast.makeText(this@AddFarmDetails, "$t", Toast.LENGTH_SHORT).show()
                progress.hidediloag()
            }
        })

    }

    fun getImage() {

        let {
            CropImage.activity(imageURi).setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Set Profile")
                .setCropShape(CropImageView.CropShape.OVAL)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonTitle("Done")
                .start(this)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val result = CropImage.getActivityResult(data)
            imageURi = CropImage.getPickImageResultUri(this, data)
            imageURi = result.uri
            binding.imgAddFarm.setImageURI(imageURi)
        } else {
            Toast.makeText(this, "request canceled", Toast.LENGTH_SHORT).show()
        }
    }
}