package fstb.hobbiesly.view.home

import fstb.hobbiesly.data.updateprofile.UpdateProfileResponse

class UpdateProfileState (
    var isLoading:Boolean = false,
    var data: UpdateProfileResponse? = null,
    var error: String = ""
)