package com.android.farmist.api

import com.android.farmist.model.CropPriceResponse.GetMyCropPrices
import com.android.farmist.model.CropPriceResponse.getCropPrice
import com.android.farmist.model.ExpensesIncomeTrackerResponse.AddExpesesResponse
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpenesData
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetExpensesTracker
import com.android.farmist.model.ExpensesIncomeTrackerResponse.ExpensesTrackerResponse.GetPiChartResponse
import com.android.farmist.model.ExpensesIncomeTrackerResponse.GetExpensesIncomeTracker
import com.android.farmist.model.ExpensesIncomeTrackerResponse.addSubsidy
import com.android.farmist.model.FullExpenseLog.Pie
import com.android.farmist.model.FullExpenseLog.Root
import com.android.farmist.model.MarketPriceResponse.marketPriceResponse
import com.android.farmist.model.OTP.CheckOTPRespo
import com.android.farmist.model.OTP.SignupOTPRespo
import com.android.farmist.model.adapterGetFarm.AdeptDataResponce
import com.android.farmist.model.addCropResponse.AddCropResponse
import com.android.farmist.model.alertsResponse.GetGovtScheme
import com.android.farmist.model.alertsResponse.GetNewsAlert
import com.android.farmist.model.archive.RemoveFromAchiv
import com.android.farmist.model.archive.SetArchiveResponse
import com.android.farmist.model.cropDetailsFragment.CropName
import com.android.farmist.model.farmstats.StatsData
import com.android.farmist.model.getFarm.Farmcroprespo
import com.android.farmist.model.getFarmForSpinnner.FarmsSpinner
import com.android.farmist.model.getFarms
import com.android.farmist.model.getSowedCrop.GetSowedCrop
import com.android.farmist.model.getSowedCrop.ProgressTracker
import com.android.farmist.model.getUserInfo.getUserModel
import com.android.farmist.model.harvested.GetHarvestedCrop
import com.android.farmist.model.harvested.MakeHarvest
import com.android.farmist.model.harvested.profitloss.ProfitLoss
import com.android.farmist.model.location.Report
import com.android.farmist.model.profileImgResponse.GetUserImagResponse
import com.android.farmist.model.profileImgResponse.SetProfileResponse
import com.android.farmist.model.selectCategoryResponse.GetFruitsList
import com.android.farmist.model.selectCategoryResponse.GetVagList
import com.android.farmist.model.setFarm.DeleteFarmRespo
import com.android.farmist.model.setFarm.GetFarmEditResponce
import com.android.farmist.model.setFarm.setFarm
import com.android.farmist.model.setFarm.updateDataRespo
import com.android.farmist.model.signUp.signUpModel
import com.android.farmist.model.upcommingAction.UpcomingDates
import com.android.farmist.model.upcommingAction.Upcomingrespo
import com.android.farmist.model.userExistOrNot.ExistOrNot
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    /*  Register  */
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

    /* Get user Profile and User name */
    @GET("me/{id}")
    fun getUser(
        @Path("id") UserId: String
    ): Call<getUserModel>

    /* Api for login  */
    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("phone") PhoneNumber: String
    ): Call<signUpModel>

    /*  upadate user profile */
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

    /*   Add new Farm  */
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


    /* Get Farm  */
    @GET("farms")
    fun getFarm(
        @Query("userId") UserId: String
    ): Call<getFarms>

    /* get all farm in spinner */
    @GET("allfarms")
    fun getSpinnerFarm(
        @Query("userId") userId: String
    ): Call<FarmsSpinner>

    /*Get user profile and data*/
    @GET("myprofilepic")
    fun getUserProfile(
        @Query("userId") userId: String
    ): Call<GetUserImagResponse>

    /* update Profile Image user */
    @Multipart
    @POST("profile")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,
        @Query("userId") userId: String
    ): Call<SetProfileResponse>

    /* Add new crop */
    @Multipart
    @POST("newcrop")
    fun addCrop(
        @Part("cropName") cropName: RequestBody,

        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,

        @Part("userId") userId: RequestBody,
        @Part("farmName") farmName: RequestBody,
        @Part("sowedArea") sowedArea: RequestBody,
        @Part("areaType") areaType: RequestBody,
        @Part("sowedDate") sowedDate: RequestBody,
    ): Call<AddCropResponse>

    /* Get Farm List*/
    @GET("farms")
    fun getFarmsForAdaper(@Query("userId") userId: String): Call<AdeptDataResponce>

    /* APi For Delete Farm */
    @DELETE("farm/delete/{id}")
    fun deleteFarm(@Path("id") id: String): Call<DeleteFarmRespo>

    /* Update Farm */
    @Multipart
    @PUT("farm/update/{id}")
    fun updateFarm(
        @Path("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("image") desc: RequestBody,
        @Part("name") name: RequestBody,
        @Part("area") area: RequestBody,
        @Part("areaType") areaType: RequestBody,
        @Part("tehsil") tehsil: RequestBody,
        @Part("surveyNum") surveyNum: RequestBody
    ): Call<updateDataRespo>

    /*Get Farm info for update  */
    @GET("farm/details/{id}")
    fun getFarmForEdit(
        @Path("id") userId: String
    ): Call<GetFarmEditResponce>

    /* Weather forecast api */
    @GET("forecast.json")
    fun getTemp(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String
    ): Call<Report>

    /* Get Expenses Log*/
    @GET("expense/get/full-log")
    fun fullExpenceLogData(
        @Query("cropId") cropId: String
    ): Call<Root>

    /* Get crop Pie chart Data */
    @GET("expense/chart")
    fun getPie(
        @Query("cropId") cropId: String
    ): Call<Pie>

    /* Get sowed crop */
    @GET("crops")
    fun getSwoedCrop(@Query("userId") userId: String): Call<GetSowedCrop>

    /* Get news alert*/
    @GET("alerts")
    fun getNewsAlert(): Call<GetNewsAlert>

    /* Get govtScheme alert */
    @GET("schemes")
    fun getGovtscheme(): Call<GetGovtScheme>

    /* Get  crop price */
    @GET("admin/get/crops")
    fun getCropPrice(): Observable<getCropPrice>

    /* Get select category friuts */
    @GET("admin/fruits/list")
    fun getcategoryFriuts(): Observable<GetFruitsList>

    /* Get select category friuts */
    @GET("admin/vegs/list")
    fun getcategoryVeg(): Observable<GetVagList>

    /* get Expenses Income tracker data */
    @GET("income/tracker")
    fun getExpensesIncomeTracker(
        @Query("userId") userId: String
    ): Observable<GetExpensesIncomeTracker>

    /* Add expenese in crop */
    @FormUrlEncoded
    @POST("expense")
    fun addExpenses(
        @Query("userId") userId: String,
        @Query("cropId") cropId: String,
        @Field("expenseType") expensesType: String,
        @Field("date") date: String,
        @Field("amount") amount: String,
    ): Observable<AddExpesesResponse>

    /* Add subsidy */
    @FormUrlEncoded
    @POST("subsidy")
    fun addsubsidy(
        @Query("userId") userId: String,
        @Query("cropId") cropId: String,
        @Field("name") name: String,
        @Field("date") date: String,
        @Field("amount") amount: String,
    ): Observable<addSubsidy>

    /* Add income */
    @FormUrlEncoded
    @POST("income/create")
    fun addincome(
        @Query("userId") userId: String,
        @Query("cropId") cropId: String,
        @Field("quantity") Quntity: String,
        @Field("date") date: String,
        @Field("income") income: String,
        @Field("quantityType") radiovalue: String,
    ): Observable<addSubsidy>

    /* Get expenses tracker */
    @GET("expense/expense-tracker")
    fun getExpensesTracker(
        @Query("cropId") cropId: String
    ): Observable<GetExpensesTracker>

    /* Get chart data  */
    @GET("expense/chart")
    fun getchartData(
        @Query("cropId") cropId: String
    ): Observable<GetPiChartResponse>

    /* Get crop expense data in add Expenses Fragment */
    @GET("expenses/details/total-date-type")
    fun getExpenseData(
        @Query("cropId") cropId: String
    ): Observable<ExpenesData>

    /* Add in archive */
    @PUT("crop/archieve/{cropId}")
    fun setArchieve(
        @Path("cropId") cropId: String
    ): Call<SetArchiveResponse>

    /* Delete crop Expenses */
    @DELETE("crop/delete/{id}")
    fun deleteCropExpence(@Path("id") id: String): Call<DeleteFarmRespo>

    /* Get Archive crop */
    @GET("crops/get/archieved")
    fun getArchiveCrop(
        @Query("userId") userId: String
    ): Observable<GetExpensesIncomeTracker>

    /* Get Crop Details */
    @GET("crop/")
    fun getCropDetails(@Query("id") id: String): Call<CropName>

    /* Crop remove from archive  */
    @PUT("crop/remove/archieve/{id}")
    fun removeIt(@Path("id") id: String): Call<RemoveFromAchiv>

    /* Make crop harvvested */
    @PUT("crop/harvest/{id}")
    fun makeHarvested(@Path("id") id: String): Call<MakeHarvest>

    /* Get Harvested crop */
    @GET("crops/get/harvested/")
    fun getHarvested(@Query("userId") userId: String): Call<GetHarvestedCrop>

    /* Get Crop market prices */
    @GET("admin/get/graph/price-month")
    fun getCropMarketPrices(@Query("id") CropPriceId: String): Call<marketPriceResponse>

    /* Harvested profit loss */
    @GET("expense/expense-tracker")
    fun getHarvestedProfit(
        @Query("cropId") cropId: String
    ): Call<ProfitLoss>

    /* Get sowed crop progress */
    @GET("crop")
    fun getProgress(@Query("id") id: String): Call<ProgressTracker>

    /* Get Farm stats */
    @GET("farms/stats")
    fun getFarmstats(@Query("userId") userId: String): Call<StatsData>

    /* Get My crops Prices*/
    @GET("my/crops/price")
    fun getMycropPrices(
        @Query("userId") cropId: String
    ): Call<GetMyCropPrices>

    /* Get Crop expenses */
    @GET("expense/expense-tracker")
    fun getExpenses(
        @Query("cropId") cropId: String
    ): Call<GetExpensesTracker>

    /* Get Farm info */
    @GET("crop/name-image")
    fun farmscroop(@Query("userId") userId: String, @Query("id") id: String): Call<Farmcroprespo>

    /* Get Upcoming Action */
    @GET("upcoming/actions")
    fun UpcomingFun(@Query("userId") userId: String): Call<Upcomingrespo>

    // Upcoming action calender dates
    @GET("upcoming/actions/dates")
    fun UpcomingFunDate(@Query("userId") userId: String): Call<UpcomingDates>

    // OTP get
    @GET("{api_key}/SMS/{users_phone_no}/AUTOGEN")
    fun GetOTP(
        @Path("api_key") api_key: String,
        @Path("users_phone_no") users_phone_no: String
    ): Call<SignupOTPRespo>

    // OTP check
    @POST("{api_key}/SMS/VERIFY/{session_id}/{otp_entered_by_user}")
    fun checkOTP(
        @Path("api_key") api_key: String,
        @Path("session_id") session_id: String,
        @Path("otp_entered_by_user") otp_entered_by_user: String
    ): Call<CheckOTPRespo>

    // check user is exist or not
    @FormUrlEncoded
    @POST("verify/user")
    fun verifyFirst(
        @Field("phone") phone: String,
    ): Call<ExistOrNot>

}