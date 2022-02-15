package com.android.farmist.api

import com.android.farmist.model.CropPriceResponse.getCropPrice
import com.android.farmist.model.ExpensesIncomeTrackerResponse.*
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetExpensesTracker
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetPiChartResponse
import com.android.farmist.model.adapterGetFarm.AdeptDataResponce
import com.android.farmist.model.addCropResponse.AddCropResponse
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.alertsResponse.New
import com.android.farmist.model.archive.SetArchiveResponse
import com.android.farmist.model.getFarm.GetFarmForSpinner
import com.android.farmist.model.getFarmForSpinnner.FarmsSpinner
import com.android.farmist.model.getFarms
import com.android.farmist.model.getSowedCrop.GetSowedCrop
import com.android.farmist.model.getUserInfo.getUserModel
import com.android.farmist.model.profileImgResponse.GetUserImagResponse
import com.android.farmist.model.profileImgResponse.SetProfileResponse
import com.android.farmist.model.profileImgResponse.getProfileResoponse
import com.android.farmist.model.selectCategoryResponse.GetFruitsList
import com.android.farmist.model.selectCategoryResponse.GetVagList
import com.android.farmist.model.setFarm.setFarm
import com.android.farmist.model.signUp.signUpModel
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import okhttp3.RequestBody

import retrofit2.http.POST

import retrofit2.http.Multipart


interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    fun UserRegister(
        @Field("name") userName: String,
        @Field("phone") userPhoneNo: String,
        @Field("pincode") userPincode: String,
        @Field("village") uservillage: String,
        @Field("tehsil") userTehsil: String,
        @Field("district") userDistrict: String,
        @Field("numOfAcers") userNoOfAcers: String,
        @Field("seedBrand") userSeedBrand: String,
        @Field("chemicalCompany") userChemicalCompany: String
    ): Call<signUpModel>

//
//    @GET("me/:61ee75518c7deb5673cbe7da")
//    fun getUserData(
////        @Path("_id")UserId:String
//    ):Call<getUserModel>

    @GET("me/{id}")
    fun getUser(
        @Path("id") UserId: String
    ): Call<getUserModel>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("phone") PhoneNumber: String
    ): Call<signUpModel>

    //upadate user profile
    @FormUrlEncoded
    @PUT("me/update/{id}")
    fun updateUserData(
        @Path("id") UserId: String,
        @Field("name") userName: String,
        @Field("phone") userPhoneNo: String,
        @Field("pincode") userPincode: String,
        @Field("village") uservillage: String,
        @Field("tehsil") userTehsil: String,
        @Field("district") userDistrict: String,
        @Field("numOfAcers") userNoOfAcers: String
    ): Call<signUpModel>

    @Multipart

    @POST("farm")
    fun addNewFarmInter(
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,
        @Part("name") name: RequestBody,
        @Part("area") area: RequestBody,
        @Part("areaType") areaType: RequestBody,
        @Part("tehsil") tehsil: RequestBody,
        @Part("surveyNum") surveyNum: RequestBody
    ): Call<setFarm>


    @GET("farms")
    fun getFarm(
        @Query("userId") UserId: String
    ): Call<getFarms>

    //get all farm in spinner
    @GET("allfarms")
    fun getSpinnerFarm(
        @Query("userId") userId: String
    ): Call<FarmsSpinner>


    //Get user profile and data
    @GET("myprofilepic")
    fun getUserProfile(
        @Query("userId") userId: String
    ): Call<GetUserImagResponse>


    //update Profile Image user
    @Multipart
    @POST("profile")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,
        @Query("userId") userId: String
    ): Call<SetProfileResponse>

    //Add crop

    @Multipart
    @POST("newcrop")
    fun addCrop(
        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,

        @Part("userId") userId: RequestBody,
        @Part("farmName") farmName: RequestBody,
        @Part("sowedArea") sowedArea: RequestBody,
        @Part("areaType") areaType: RequestBody,
        @Part("sowedDate") sowedDate: RequestBody,

    ): Call<AddCropResponse>

    //get sowed crop
    @GET("crops")
    fun getSwoedCrop(@Query("userId") userId: String): Call<GetSowedCrop>

    //get news alert
    @GET("alerts")
    fun getNewsAlert(): Call<GetNewsAlert>

    //get govtScheme alert
    @GET("schemes")
    fun getGovtscheme():Call<GetGovtScheme>

    //Get  crop price
    @GET("admin/get/crops")
    fun getCropPrice():Observable<getCropPrice>

    //get select category friuts
    @GET("admin/fruits/list")
    fun getcategoryFriuts():Observable<GetFruitsList>

    //get select category friuts
    @GET("admin/vegs/list")
    fun getcategoryVeg():Observable<GetVagList>

   //get Expenses Income tracker data
    @GET("income/tracker")
    fun getExpensesIncomeTracker(
        @Query("userId") userId: String
    ):Observable<GetExpensesIncomeTracker>

    //Add expenese in crop
    @FormUrlEncoded
    @POST("expense")
    fun addExpenses(
        @Query("userId")userId: String,
        @Query("cropId")cropId:String,
        @Field("expenseType")expensesType:String,
        @Field("date")date:String,
        @Field("amount")amount:String,
    ):Observable<AddExpesesResponse>
//add subsidy
    @FormUrlEncoded
    @POST("subsidy")
    fun addsubsidy(
        @Query("userId")userId: String,
        @Query("cropId")cropId:String,
        @Field("name")name:String,
        @Field("date")date:String,
        @Field("amount")amount:String,
    ):Observable<addSubsidy>

//  add income
@FormUrlEncoded
@POST("income/create")
fun addincome(
    @Query("userId")userId: String,
    @Query("cropId")cropId:String,
    @Field("quantity")Quntity:String,
    @Field("date")date:String,
    @Field("income")income:String,
    @Field("quantityType")radiovalue:String,
):Observable<addSubsidy>

// get expenses tracker

    @GET("expense/expense-tracker")
    fun getExpensesTracker(
        @Query("cropId") cropId: String
    ):Observable<GetExpensesTracker>

// get chart data
    @GET("expense/chart")
    fun getchartData(
    @Query("cropId")cropId: String
    ):Observable<GetPiChartResponse>



// get crop expense data in add Expenses Fragment
    @GET("expenses/details/total-date-type")
    fun getExpenseData(
    @Query("cropId")cropId: String
    ):Observable<ExpenesData>


    ///add in archive

    @PUT("crop/archieve/{cropId}")
    fun setArchieve(
        @Path("cropId")cropId: String
    ):Call<SetArchiveResponse>




}
