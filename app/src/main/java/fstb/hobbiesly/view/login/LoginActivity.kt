package fstb.hobbiesly.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import fstb.hobbiesly.R
import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.util.showToast
import fstb.hobbiesly.view.home.MainActivity
import fstb.hobbiesly.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.lg_login_button)

        loginButton.setOnClickListener {
            val usernameText = findViewById<EditText>(R.id.lg_userame_editText).text.toString()
            val passwordText = findViewById<EditText>(R.id.lg_password_editText).text.toString()
            if (usernameText.isEmpty()) {
                showToast(this, "username is required")
            } else if(passwordText.isEmpty()){
                showToast(this, "password is required")
            } else{
                val loginRequest = LoginRequest(usernameText, passwordText)
                authViewModel.login(loginRequest)
                observeLogin()
            }
        }
    }

    private fun observeLogin() {
        authViewModel._loginState.observe(this) { data ->
            when {
                data.isLoading -> {
                    showToast(this, "Loading...")
                }
                data.data != null -> {
                    showToast(this, "Login successful $data")
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else -> {
                    showToast(this, "Login Failure $data")
                }
            }

        }
    }
}