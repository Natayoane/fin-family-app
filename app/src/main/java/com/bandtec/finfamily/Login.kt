package com.bandtec.finfamily

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.model.Users
import com.bandtec.finfamily.repository.UserEndpoint
import com.bandtec.finfamily.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_retrofit_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginUser(v: View){
        getData()

    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("http://ec2-54-82-171-69.compute-1.amazonaws.com/fin-family/")



    }
}
