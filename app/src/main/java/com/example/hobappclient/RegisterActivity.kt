package com.example.hobappclient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.utils.showToast
import com.example.hobappclient.databinding.ActivityRegisterBinding
import fstb.hobbiesly.data.register.RegisterRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initcomponents()
    }

    private fun initcomponents() {
        binding.rgBackButton.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.rgRegisterButton.setOnClickListener {
            if(checkValues()){
                val registerRequest = registerRequesterBuild()
                CoroutineScope(Dispatchers.IO).launch {
                    val apiResponse = ihobapi.register(registerRequest)
                    if (apiResponse.isSuccessful && apiResponse.body()!=null){
                       Log.i("register",binding.rgUsernameEditText.text.toString()+" has been registered successfully")
                        var intent = Intent(applicationContext,LoginActivity::class.java)
                        startActivity(intent)
                    }else if(!apiResponse.isSuccessful){
                        runOnUiThread{
                            showToast(applicationContext,
                                "Api fail: "+apiResponse.errorBody().toString())
                        }
                    }
                }
            }
        }
    }

    private fun checkValues(): Boolean {
        if(binding.rgUsernameEditText.text.toString().isNullOrBlank()) {
            showToast(this,"Username is required")
            return false
        } else if (binding.rgPasswordEditText.text.toString().isNullOrBlank()){
            showToast(this,"Password is required")
            return false
        } else if (binding.rgEmailEditText.text.toString().isNullOrBlank()||
            !Patterns.EMAIL_ADDRESS.matcher(binding.rgEmailEditText.text.toString()).matches()){
            showToast(this,"a Valid Email is required")
            return false
        } else if(binding.rgFirsnameEditText.text.toString().isNullOrBlank()){
            showToast(this,"First Name is required")
            return false
        } else if(binding.rgLastnameEditText.text.toString().isNullOrBlank()){
            showToast(this,"Last Name is required")
            return false
        } else if(binding.rgBirthdateEditText.text.toString().isNullOrBlank()||
            !checkDateFormat()
        ){
            showToast(this,"a valid yyyy-MM-dd Birth date is required")
            return false
        }else if(binding.rgAdressEditText.text.toString().isNullOrBlank()){
            showToast(this,"Address is required")
            return false
        }else if(binding.rgCityEditText.text.toString().isNullOrBlank()){
            showToast(this,"City is required")
            return false
        }else if(binding.rgAdressEditText.text.toString().isNullOrBlank()){
            showToast(this,"Country is required")
            return false
        }
        return true
    }

    private fun checkDateFormat():Boolean {
        val parsed =SimpleDateFormat(
            "YYYY-MM-DD",
            Locale.getDefault()
        ).parse(binding.rgBirthdateEditText.text.toString())
        return parsed.before(Date.from(Instant.now()))
    }

    fun registerRequesterBuild(): RegisterRequest {
        var gender: String = ""
        if (binding.rgGenderSpinner.selectedItem.toString() == "Male") {
            gender = "M"
        } else {
            gender = "F"
        }
        return RegisterRequest(
            binding.rgUsernameEditText.text.toString(),
            binding.rgEmailEditText.text.toString(),
            binding.rgFirsnameEditText.text.toString(),
            binding.rgLastnameEditText.text.toString(),
            gender,
            binding.rgBirthdateEditText.text.toString(),
            binding.rgAdressEditText.text.toString(),
            binding.rgCityEditText.text.toString(),
            binding.rgCountryEditText.text.toString(),
            binding.rgPasswordEditText.text.toString(),
            "https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/256x256/user.png"
        )
    }
}


