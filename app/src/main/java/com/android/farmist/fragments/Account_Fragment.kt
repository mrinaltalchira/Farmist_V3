package com.android.farmist.fragments

import android.R.attr
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.farmist.R
import com.android.farmist.activity.LoginActivity
import com.android.farmist.api.Api_Controller
import com.android.farmist.databinding.FragmentAccountBinding
import com.android.farmist.model.profileImgResponse.SetProfileResponse
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import android.R.attr.data
import android.content.IntentFilter
import android.content.SharedPreferences
import android.util.Log
import com.android.farmist.UploadRequestBody
import com.android.farmist.model.profileImgResponse.GetUserImagResponse
import com.android.farmist.model.profileImgResponse.getProfileResoponse
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.adaapter_exp_income_tracker.*
import java.io.FileInputStream
import java.io.FileOutputStream


class Account_Fragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    var imageURi: Uri? = null
    private var selectedImageUri: Uri? = null
    lateinit var userId: String
    var test: Int = 0
    var imageUrl: String = ""
    var nameStr: String = ""


    companion object {
        val IMAGETOKEN = 200
    }


    lateinit var preferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences =
            requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
        userId = preferences.getString("message", "").toString()


        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_account, container, false)

        if (!imageUrl.isBlank()) {
            activity?.let {
                Glide.with(it.applicationContext).load(imageUrl).into(binding.setPhoto)
            }
            binding.AccountUserName.setText(nameStr)


        } else {
            getprofile()

        }






        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.personalinfo.setOnClickListener(View.OnClickListener {

            findNavController().navigate(
                R.id.action_nav_account_to_personal_Information_Fragment,
                null
            )

        })
        binding.tvchooselanguage.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.action_nav_account_to_choose_Language_Fragment, null)

        })
        binding.logOutBtn.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)

            val preferences =
                requireActivity().getSharedPreferences("userMassage", Context.MODE_PRIVATE)
            preferences.edit().remove("check").commit()
            Toast.makeText(requireContext(), "Log out", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()
        }
        binding.setPhoto.setOnClickListener {
            openImageChooser()


        }

    }

    private fun getprofile() {

        val call: Call<GetUserImagResponse>
        call = Api_Controller().getInstacne().getUserProfile(userId)
        call.enqueue(object : Callback<GetUserImagResponse> {
            override fun onResponse(
                call: Call<GetUserImagResponse>,
                response: Response<GetUserImagResponse>
            ) {
//                Toast.makeText(requireContext(), "this${response.body().toString()}", Toast.LENGTH_SHORT).show()


                context?.let {

                    if (response != null) {
                        val data = response.body()
                        if (data != null) {

                            if (data.latestPic.size != 0)

                            {
                                imageUrl = data.latestPic[0].image
                                Glide.with(it.applicationContext).load(imageUrl).into(binding.setPhoto)

                            }
                            nameStr = data.name
                        }

                    }



                }
//                    Glide.with(it.applicationContext).load(response.body()?.latestPic?.get(0)?.image).into(binding.setPhoto) }
                binding.AccountUserName.setText(response.body()?.name)
                test++

            }

            override fun onFailure(call: Call<GetUserImagResponse>, t: Throwable) {
                Log.d("getProfileError", t.toString())
            }
        })

    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, IMAGETOKEN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGETOKEN -> {
                    selectedImageUri = data?.data
                    uploadImage()
                    binding.setPhoto.setImageURI(selectedImageUri)
                }
            }
        }
    }


    private fun uploadImage() {
        if (selectedImageUri == null) {
            Toast.makeText(requireContext(), "Select an Image First", Toast.LENGTH_SHORT).show()

            return
        }

        val parcelFileDescriptor =
            requireContext().contentResolver.openFileDescriptor(selectedImageUri!!, "r", null)
                ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)

        val file = File(requireContext().cacheDir, "akkkkkk")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val userIdRequestBody: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"), userId
        )
        val body = UploadRequestBody(file, "image")
        val call: Call<SetProfileResponse>
        call = Api_Controller().getInstacne().uploadImage(
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
            ),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json"),
            userId
        )

        Toast.makeText(requireContext(), " First", Toast.LENGTH_SHORT).show()
        call.enqueue(object : Callback<SetProfileResponse> {
            override fun onResponse(
                call: Call<SetProfileResponse>,
                response: Response<SetProfileResponse>
            ) {
                val dataresponse = response.body()
                Toast.makeText(
                    requireContext(),
                    "Select an Image First" + dataresponse.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                if (response.isSuccessful) {
                    val setProfileResponse: SetProfileResponse
                    setProfileResponse = response.body()!!
                    Toast.makeText(
                        requireContext(),
                        "image upload successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    getprofile()
                }
            }

            override fun onFailure(call: Call<SetProfileResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Image failed $t", Toast.LENGTH_SHORT).show()
            }
        })

    }


}


