package com.elaj.patient.apiHandlers

import com.elaj.patient.models.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiInterface {
    /* ------------------------- POST Handle ------------------------- */
    @POST("v1/login")
    fun loginHandle(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<MemberModel?>?>?

    @POST("v1/signup")
    fun registerHandle(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<MemberModel?>?>?

    @POST("v1/contact-us")
    fun sendSupport(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<Any>?>?

    @POST("user/forgot-password")
    fun forgetPassword(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<*>?>?

    @POST("user/reset-password")
    fun resetPassword(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<*>?>?

    @POST("v1/mobile/confirm")
    fun confirmRegister(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<MemberModel>?>?

    @POST("v1/me")
    fun editProfile(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<MemberModel?>?>?

    @POST("v1/me")
    fun changePassword(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String, Any?>?
    ): Call<ResultAPIModel<Any?>>?


    @POST("v2/mobile/sendConfimration")
    fun sendConfirmRegister(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<*>?>?

    @POST("v1/logout")
    fun logOut(@HeaderMap headerParams: Map<String, Any?>?): Call<ResultAPIModel<Any>?>?

    @POST("v1/me/profile/upload")
    fun uploadAvatar(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<MemberModel?>?>?


    @POST("v1/orders")
    fun sendNewRequest(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String, Any?>?
    ): Call<ResultAPIModel<PayModel?>?>?

    @POST("v1/me/favorites/{id}")
    fun addProviderToFavorite(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") providerId: Int
    ): Call<ResultAPIModel<Any?>?>?

    @POST("v1/salons/review")
    fun addReview(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<*>?>?

    @POST("v1/me/profile/upload")
    fun uploadGalleryPhoto(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<GalleryModel>?>?

    @POST("v1/me")
    fun editProviderProfile(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<Any?>?>?

    @POST("v1/salons/profile/services")
    fun updateProfileServices(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<Any?>?>?

    @POST("v1/salons/profile/shifts-hours")
    fun updateProfileWorkHours(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<Any?>?>?

    @POST("v1/salons/wallet/withdraw")
    fun withdrawBalance(
        @HeaderMap headerParams: Map<String, Any?>?
    ): Call<ResultAPIModel<Any?>>?

    @POST("v1/notification/send")
    fun sendChatNotification(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: Map<String?, Any?>?
    ): Call<ResultAPIModel<Any>?>?

    @POST("v1/orders/review")
    fun reviewProvider(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body params: MutableMap<String, Any?>?
    ): Call<ResultAPIModel<Any?>?>?

    /* ------------------------- GET Handle ------------------------- */

    @GET("v1/search")
    fun searchServiceProviders(
        @HeaderMap headerParams: Map<String, Any?>?,
        @QueryMap queryParams: MutableMap<String, Any?>?
    ): Call<ResultAPIModel<AllProvidersModel?>>?


//    @GET("v1/services/{id}/salons-list")
//    fun getServiceProviders(
//        @HeaderMap headerParams: Map<String?, Any?>?, @Path("id") serviceId: Int, @Query(
//            "page"
//        ) page: Int
//    ): Call<ResultAPIModel<AllProvidersModel?>>?

    @GET("v1/me/favorites")
    fun getFavoriteProviders(
        @HeaderMap headerParams: Map<String, Any?>?, @Query(
            "page"
        ) page: Int
    ): Call<ResultAPIModel<AllProvidersModel?>>?

    @GET("v1/orders")
    fun getRequests(
        @HeaderMap headerParams: Map<String, Any?>?, @QueryMap queryParams: Map<String, Any?>
    ): Call<ResultAPIModel<AllRequestsModel?>>?

    @GET("v1/chats")
    fun getChats(
        @HeaderMap headerParams: Map<String, Any?>?, @Query(
            "page"
        ) page: Int
    ): Call<ResultAPIModel<AllChatsModel?>>?

    @GET("v1/salons/{id}/show")
    fun getProviderDetails(
        @HeaderMap headerParams: Map<String, Any?>?, @Path("id") id: Int
    ): Call<ResultAPIModel<ProviderModel?>>?

    @GET("v1/orders/{id}/show")
    fun getRequestDetails(
        @HeaderMap headerParams: Map<String, Any?>?, @Path("id") id: Int
    ): Call<ResultAPIModel<RequestModel?>>?

    @GET("v1/salons/{id}/reviews-list")
    fun getProviderReviews(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") providerId: Int,
        @Query("page") page: Int
    ): Call<ResultAPIModel<AllReviewsModel?>>?

    @GET("v1/dayTimes")
    fun getDayTimes(
        @HeaderMap headerParams: Map<String, Any?>?, @Query("day") day: String
    ): Call<ResultAPIModel<MutableList<TimeModel>?>>?

    @GET("v1/lists")
    fun getLists(
        @HeaderMap headerParams: Map<String, Any?>?
    ): Call<ResultAPIModel<Any?>>?

    @GET("v1/salons/wallet")
    fun getWalletDetails(
        @HeaderMap headerParams: Map<String, Any?>?
    ): Call<ResultAPIModel<WalletModel?>>?


//    @GET("v1/salons/show/me")
//    fun getMySalonDetails(
//        @HeaderMap headerParams: Map<String?, Any?>?
//    ): Call<ResultAPIModel<ProviderModel?>>?


    @GET("v1/me")
    fun getMyProfile(@HeaderMap headerParams: Map<String, Any?>?): Call<ResultAPIModel<MemberModel?>?>?


    @GET("v1/home-page")
    fun getCategories(@HeaderMap headerParams: Map<String, Any?>?): Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?

    @GET("v1/{id}/sub_categories")
    fun getSubCategories(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") id: Int
    ): Call<ResultAPIModel<MutableList<ServiceModel?>?>?>?

    @GET("v1/country/{id}")
    fun getCountryCities(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") id: Int
    ): Call<ResultAPIModel<MutableList<CityModel>?>?>?

    @GET("v1/checkAppVersion")
    fun checkAppVersion(@HeaderMap headerParams: Map<String, Any?>?): Call<ResultAPIModel<AppVersionModel?>>?

    @Streaming
    @GET
    fun downloadBookPdf(@Url fileUrl: String?): Call<ResponseBody?>?

    /* ------------------------- PUT Handle ------------------------- */ //    @PUT("v1/profile/me")
//    Call<ResultAPIModel<MemberModel>> updateProfilePut(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);
/* ------------------------- PATCH Handle ------------------------- */
    @PATCH("v1/me")
    fun updateProfilePatch(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Body requestBody: RequestBody?
    ): Call<ResultAPIModel<MemberModel?>?>?

    @PATCH("v1/orders/{id}/update")
    fun setRequestStatus(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") requestId: Int,
        @Body params: MutableMap<String, Any?>?
    ): Call<ResultAPIModel<Any?>?>?

    /* ------------------------- DELETE Handle ------------------------- */ //

    @DELETE("v1/me/favorites/{id}")
    fun deleteProviderFromFavorite(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") providerId: Int
    ): Call<ResultAPIModel<Any?>?>?

    @DELETE("v1/salons/gallery/{id}/delete")
    fun deleteGalleryPhoto(
        @HeaderMap headerParams: Map<String, Any?>?,
        @Path("id") id: Int
    ): Call<ResultAPIModel<Any?>?>?

}