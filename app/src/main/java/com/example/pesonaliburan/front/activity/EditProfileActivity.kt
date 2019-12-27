package com.example.pesonaliburan.front.activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pesonaliburan.R
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponseUserModel
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import kotlinx.android.synthetic.main.edit_profile_layout.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

class EditProfileActivity: AppCompatActivity() {

    lateinit var mediaPath:String
    var mediaColumns = arrayOf(MediaStore.Video.Media._ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_layout)

        val sharedPref = SharedPreference(this)
        val getUserId = sharedPref.getValueInt("user_id").toString()

        btn_takePhoto.setOnClickListener {
            val getFile = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(getFile,0)
        }
        var getNameUser = et_nameUser.setText(sharedPref.getValueString("name_user")).toString()
        var getUsername = et_usernameLogin.setText(sharedPref.getValueString("username")).toString()
        var getAbout = et_about.setText(sharedPref.getValueString("about_user")).toString()
        iv_editProfile.setOnClickListener {
            editProfile(getUserId,getNameUser,getUsername,getAbout)
        }
    }

    fun editProfile(
        getUserId: String,
        getNameUser: String,
        getUsername: String,
        getAbout: String
    ) {

            val file: File = File(mediaPath)
            val requestBody = RequestBody.create(MediaType.parse("*/*"),file)
            val fileToUpload = MultipartBody.Part.createFormData("user_img", file.name, requestBody)

            val userId = RequestBody.create(MediaType.parse("text/plain"), getUserId)
            val nameUser = RequestBody.create(MediaType.parse("text/plain"), getNameUser)
            val username = RequestBody.create(MediaType.parse("text/plain"), getUsername)
            val about = RequestBody.create(MediaType.parse("text/plain"), getAbout)
            val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)

            val apiService = Server.buildService(ApiService::class.java)
            val requestCall = apiService.updateUser(userId,nameUser,username,about,fileToUpload,filename)
            requestCall.enqueue(object : Callback<ResponseUserModel> {
                override fun onResponse(call: Call<ResponseUserModel>, response: Response<ResponseUserModel>
                ) {
                    val responseStatus = response.body()!!.status
                    if (responseStatus == 200){

                        Toast.makeText(applicationContext,"berhasi update data",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"gagal update data",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                    Toast.makeText(applicationContext,"Tidak terhubung Internet",Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data){
                // Get the Image from data
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)!!
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)
                // Set the Image in ImageView for Previewing the Media
                iv_previewProfile.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()
            }
            else {
                Toast.makeText(this, "Anda belum mengunggah gambar", Toast.LENGTH_LONG).show();
            }
        }
        catch (e: Exception){
            Toast.makeText(this, "Maaf ada kesalahan dalam sistem kami", Toast.LENGTH_LONG).show();
            e.printStackTrace()
        }
    }
}