package com.example.android.user_list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.android.user_list.Adapter.UserAdapter
import com.example.android.user_list.DataClass.MySingleton
import com.example.android.user_list.DataClass.UserData
import com.example.android.user_list.databinding.ActivityMainBinding
import org.json.JSONArray
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var page:Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        binding.pageTV.text = page.toString()
        callData()
        binding.leftBtn.setOnClickListener(){
            if(page>1){
                page--
                binding.pageTV.text = page.toString()
                callData()

            }
        }
        binding.rightBtn.setOnClickListener(){
            if(page<=3){
                page++
                binding.pageTV.text = page.toString()
                callData()

            }
        }











}
    fun callData(){
        val adapter = UserAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.rv.adapter = adapter

        //https://dummyapi.io/data/api/user?page=1&limit=10
        val baseUrl = "https://dummyapi.io/data/api/user?page="
        var url = baseUrl + page.toString() + "&limit=25"
        binding.loader.visibility = View.VISIBLE
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener{ response ->

                val usersJsonArray = response.getJSONArray("data")
                var productArray = ArrayList<UserData>()
                Log.d("error2","${usersJsonArray}")

                for(i in 0 until usersJsonArray.length()){
                    var userJSONobject = usersJsonArray.getJSONObject(i)
                    Log.d("error1","${userJSONobject}")
                    var user = UserData(
                        userJSONobject.getString("firstName"),
                        userJSONobject.getString("email"),
                        userJSONobject.getString("picture"))
                    productArray.add(user)
                    Log.d("User","the name of user is ${user.name}")
                }

                adapter.updateUsers(productArray)
                binding.loader.visibility = View.GONE
            },
            Response.ErrorListener { error ->
                Log.d("error","${error.toString()}")
                binding.loader.visibility = View.GONE
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["app-id"] = "611119ff2fcb787ca99e95c5"
                return headers
            }

        }

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
}