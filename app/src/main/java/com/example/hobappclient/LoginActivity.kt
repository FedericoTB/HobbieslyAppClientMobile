package com.example.hobappclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.utils.showToast
import fstb.hobbiesly.data.login.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val iHobApi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)
    //private val userPreferences : SharedPreferences = getSharedPreferences("access", Context.MODE_PRIVATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initcomponents()
    }

    private fun initcomponents() {
        val usernameET = findViewById<EditText>(R.id.lg_username_editText)
        val passwordET = findViewById<EditText>(R.id.lg_password_editText)
        val loginButton = findViewById<Button>(R.id.lg_login_button)
        val registerButton = findViewById<Button>(R.id.lg_register_button)
        val progressBar = findViewById<ProgressBar>(R.id.lg_progressBar)

        loginButton.setOnClickListener{
            if (!usernameET.text.isNullOrEmpty() && !passwordET.text.isNullOrEmpty()) {
                progressBar.visibility = View.VISIBLE
                val loginRequest = LoginRequest(
                    usernameET.text.toString(),
                    passwordET.text.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                   val loginCall = iHobApi.login(loginRequest)
                    if (loginCall.isSuccessful && loginCall.body()!=null){
                        val bundle = Bundle()
                        bundle.putString("useraccess",loginCall.body()?.access.toString())
                        bundle.putString("userid", loginCall.body()!!.id.toString())
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }else if(!loginCall.isSuccessful){
                       runOnUiThread{
                           showToast(applicationContext,
                               "Api fail: "+loginCall.errorBody().toString())
                       }
                    }
                }
            }else showToast(this,"Username and Password are Required")
            progressBar.visibility = View.GONE
        }

        registerButton.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

