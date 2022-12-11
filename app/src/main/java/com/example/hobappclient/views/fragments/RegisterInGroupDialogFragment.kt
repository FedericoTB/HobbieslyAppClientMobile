package com.example.hobappclient.views.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.hobappclient.R
import com.example.hobappclient.data.memberships.MembershipRequest
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterInGroupDialogFragment(): DialogFragment() {
    private var param1:String = ""
    private var param2:String = ""
    private var param3:String = ""
    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            param1 = it.getString("useraccess").toString()
            param2 = it.getString("userid").toString()
            param3 = it.getString("group").toString()
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            builder.setMessage("You are not member of this group, want register it in?")
                .setView(inflater.inflate(R.layout.fragment_dialog_register_in_group,null))
                .setPositiveButton("Register", DialogInterface.OnClickListener { dialogInterface, i ->
                    var sended = false
                    val reason = view?.findViewById<EditText>(R.id.dfrg_reason_editText)?.text.toString()
                    if(reason != null){
                        val membershipRequest = MembershipRequest(param2.toInt(),
                            param3.toInt(),
                            reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            val response = ihobapi.postMembership("Bearer $param1",membershipRequest)
                            if (response.isSuccessful && response.body() != null){
                                  sended = true
                             }else {
                                     Log.e("Post Membership","Error to send register: "+response.errorBody().toString())
                             }
                        }
                        if (sended) {

                            onDismiss()
                        }
                    }
                })
                .setNegativeButton("Cancel",DialogInterface.OnClickListener { _, _ ->
                    dialog?.cancel()
                })
            builder.create()
        } ?:throw java.lang.IllegalStateException("Activity Cannot be Null")
    }

    fun onDismiss() {
        val bundle = Bundle()
        bundle.putString("useraccess",param1)
        bundle.putString("userid",param2)
        bundle.putString("group",param3)
        val fragment = GroupDetailViewFragment()
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            ?.replace(R.id.mv_fragmentContainerView,fragment)?.commit()
    }

}