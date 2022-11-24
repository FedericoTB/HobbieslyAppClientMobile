package fstb.hobbiesly.view.login

import fstb.hobbiesly.data.register.RegisterResponse

class RegisterState (
    var isLoading:Boolean = false,
    var data: RegisterResponse? = null,
    var error: String = ""
)