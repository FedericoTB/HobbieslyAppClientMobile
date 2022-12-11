package com.example.hobappclient.views.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hobappclient.MainActivity
import com.example.hobappclient.R
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.data.users.UserResponse
import com.example.hobappclient.databinding.FragmentProfileBinding
import com.example.hobappclient.viewmodels.UserMeViewModel
import java.sql.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"
private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)
/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user : UserResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        user = UserResponse(
            1,
            "",
            "",
            "",
            "",
            "",
            Date.valueOf("2022-12-06"),
            "",
            "",
            "",
            "")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        initializeComponents()
        onBackMobileButton()
        return binding.root
    }

    private fun initializeComponents() {
        userObtainerFromApi()

    }

    private fun userObtainerFromApi() {
        val viewModel = ViewModelProvider(this)[UserMeViewModel::class.java]

        viewModel.getUserDataObserver().observe(viewLifecycleOwner,Observer<UserResponse?>{
            if(it != null){
                 user = it
                if (!user.image_url.isNullOrEmpty()) {
                    context?.let { cont -> Glide.with(cont)
                        .load(user.image_url).override(250).into(binding.profAvatarImageView) }
                }else{
                    binding.profAvatarImageView.setImageResource(R.drawable.profile_foreground)
                }
                binding.profUsernameTextView.text = user.username
                binding.profEmailTextView.text = user.email
                binding.profFirstnameEditText.setText(user.first_name)
                binding.profLastnameEditText.setText(user.last_name)
                binding.profGenderEditText.setText(user.gender)
                binding.profBirthEditText.setText(user.birth_date.toString())
                binding.profAdressEditText.setText(user.adress)
                binding.profCityEditText.setText(user.city)
                binding.profCountryEditText.setText(user.country)
            }
        })
        param1?.let { param2?.let { it1 -> viewModel.makeApiCall(it, it1) } }
    }

    private fun onBackMobileButton() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val bundle = Bundle()
                    bundle.putString("useraccess", param1)
                    bundle.putString("userid", param2)
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}