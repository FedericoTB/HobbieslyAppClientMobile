package fstb.hobbiesly.view.login

import fstb.hobbiesly.data.login.LoginResponse

class LoginState  (
    var isLoading:Boolean = false,
    var data: LoginResponse? = null,
    var error: String = ""
)