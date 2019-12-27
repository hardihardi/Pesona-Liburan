package com.example.pesonaliburan.front.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.pesonaliburan.R
import kotlinx.android.synthetic.main.add_post_activity.*
import android.graphics.BitmapFactory
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.pesonaliburan.front.fragment.content_menu.PostFragment
import com.example.pesonaliburan.retrofit.Server
import com.example.pesonaliburan.retrofit.api_link.ApiService
import com.example.pesonaliburan.retrofit.response.ResponsePostModel
import com.example.pesonaliburan.sharedpreferences.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddPostActivity: AppCompatActivity() {

    lateinit var mediaPath:String
    var mediaColumns = arrayOf(MediaStore.Video.Media._ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_post_activity)

        val sharedPref = SharedPreference(this)
        val getUserId = sharedPref.getValueInt("user_id").toString()
        val alertDialogBuilder = AlertDialog.Builder(this)
        btn_takeFile.setOnClickListener {
            val getFile = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(getFile,0)
        }
        iv_uploadPost.isVisible=false

        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            val getTitleInput = et_postTitle.text.toString()
            val getDescPost = et_descPost.text.toString()
            uploadPost(getUserId,getTitleInput,getDescPost)
        }

        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(this, "batal", Toast.LENGTH_SHORT).show()
        }


        iv_uploadPost.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("Pesona Liburan Alert")
                setMessage("Tambah Post?")
                setPositiveButton("Tambah", DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, negativeButtonClick)
                show()
            }


        }

    }

    private fun uploadPost(getUserId:String, getTitleInput: String, getDescPost: String) {
        if (getTitleInput.isEmpty()){
            Toast.makeText(applicationContext,"Judul harap diisi",Toast.LENGTH_SHORT).show()
        }
        else if (getDescPost.isEmpty()){
            Toast.makeText(applicationContext,"Deksripsi harap diisi",Toast.LENGTH_SHORT).show()
        }
        else{
            val file:File = File(mediaPath)
            val requestBody = RequestBody.create(MediaType.parse("*/*"),file)
            val fileToUpload = MultipartBody.Part.createFormData("post_img", file.name, requestBody)

            val userId = RequestBody.create(MediaType.parse("a"), getUserId)
            val titleInput = RequestBody.create(MediaType.parse("a"), getTitleInput)
            val descPost = RequestBody.create(MediaType.parse("text/plain"), getDescPost)
            val filename = RequestBody.create(MediaType.parse("text/plain"), file.name)

            val apiService = Server.buildService(ApiService::class.java)
            val requestCall = apiService.uploadPostApi(userId,titleInput,descPost,fileToUpload,filename)
            requestCall.enqueue(object :Callback<ResponsePostModel>{
                override fun onResponse(call: Call<ResponsePostModel>, response: Response<ResponsePostModel>
                ) {
                    val responseStatus = response.body()!!.status
                    if (responseStatus == 200){
                        et_postTitle.text.clear()
                        et_descPost.text.clear()
                        Toast.makeText(applicationContext,"berhasil upload",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"gagal upload",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponsePostModel>, t: Throwable) {
                    Toast.makeText(applicationContext,"Maaf ada kesalahan dalam system kami",Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }
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
                iv_uploadImgPreview.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()
                iv_uploadPost.isVisible=true
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