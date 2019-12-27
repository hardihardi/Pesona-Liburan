package com.example.pesonaliburan.retrofit.api_link

import com.example.pesonaliburan.retrofit.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //==============City===============
    @GET("/getallcity/{area_id}")
    fun get_city(
        @Path("area_id") area_Id:Int
    ):Call<ResponseCityModel>

    //==============Count City===============
    @GET("/getcountcity/{area_id}")
    fun get_count_city(
        @Path("area_id") area_Id:Int
    ):Call<ResponseCountModel>
    //==============Count City===============
    @GET("/getcountdestination/{area_id}")
    fun get_count_destination(
        @Path("area_id") area_Id:Int
    ):Call<ResponseCountModel>

    //==========Destination============
    @GET("/getdestination/{city_id}")
    fun get_destination(
        @Path("city_id") city_id:Int
    ):Call<ResponseDestinationModel>

    //Showmore
    @GET("/showmoredestination/{city_id}")
    fun get_show_more_destination(
        @Path("city_id") city_id:Int
    ):Call<ResponseDestinationModel>

    //Detail IMG Sub Destination
    @GET("/getimgsubdes/{destination_id}")
    fun get_img_Subdes(
        @Path("destination_id") destination_id:Int
    ):Call<ResponseImgSubDestinationModel>

    //Search Destination
    @FormUrlEncoded
    @POST("/searchdestination/{city_id}")
    fun search_destination(
        @Path("city_id") city_id:Int,
        @Field("destinationSearch") destinationSearch:String
    ):Call<ResponseDestinationModel>



    //Search
    @FormUrlEncoded
    @POST("/searchcity")
    fun search_city(
        @Field("citySearch") citySearch:String
    ):Call<ResponseCityModel>

    //================Post===============
    @GET("/getallpost")
    fun get_all_post():Call<ResponsePostModel>

    @GET("/getpostuser/{user_id}")
    fun get_post_user(
        @Path("user_id") user_id: Int
    ):Call<ResponsePostModel>

    @GET("/getdetailpost/{user_id}")
    fun get_detail_post(
        @Path("user_id") user_id: Int
    ):Call<ResponsePostModel>

    @GET("/deletepost/{post_id}")
    fun deletePost(
        @Path("post_id") post_id: Int
    ):Call<ResponsePostModel>

    @Multipart
    @POST("/uploadpost")
    fun uploadPostApi(
        @Part("user_id") user_id:RequestBody,
       @Part("post_title") post_title:RequestBody,
       @Part("post") post:RequestBody,
       @Part file:MultipartBody.Part,
       @Part("post_img") post_img:RequestBody
    ):Call<ResponsePostModel>

    //================request destinaiton===============
    @POST("/addreqdes")
    @FormUrlEncoded
    fun addReqDes(
        @Field("name_city") name_city:String,
        @Field("name_destination") name_destination:String,
        @Field("desc_destination") desc_destination:String
    ):Call<ResponseReqDestinationModel>

    //================Profile===============
    @GET("/getprofile/{user_id}")
    fun get_profile(
        @Path("user_id") user_id:Int
    ):Call<ResponseUserModel>

    //================Auth===============
    @FormUrlEncoded
    @POST("/registeruser")
    fun register_user(
        @Field("name_user") name_user:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("about_user") about_user:String
    ):Call<ResponseUserModel>

    @FormUrlEncoded
    @POST("/loginuser")
    fun login_user(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<ResponseUserModel>

    //Logout
    @POST("/logout/{user_id}")
    fun logout(
        @Path("user_id") user_id:Int
    ):Call<ResponseLogoutUser>

    @Multipart
    @POST("/updateprofile/{user_id}")
    fun updateUser(
        @Part("user_id") user_id:RequestBody,
        @Part("name_user") post_title:RequestBody,
        @Part("username") username:RequestBody,
        @Part("about_user") about_user:RequestBody,
        @Part file:MultipartBody.Part,
        @Part("user_img") user_img:RequestBody
    ):Call<ResponseUserModel>
}