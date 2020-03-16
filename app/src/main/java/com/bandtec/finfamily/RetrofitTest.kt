package com.bandtec.finfamily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.model.Posts
import com.bandtec.finfamily.repository.Endpoint
import com.bandtec.finfamily.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_retrofit_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_test)

        getData()
    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object : Callback<List<Posts>> {
            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                response.body()?.forEach {
                    textView.text = textView.text.toString().plus(it.body).plus("ooooooi")
                }
            }
        })

    }
}
