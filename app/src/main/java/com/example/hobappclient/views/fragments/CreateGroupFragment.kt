package com.example.hobappclient.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobappclient.MainActivity
import com.example.hobappclient.R
import com.example.hobappclient.data.categories.CategoryResponse
import com.example.hobappclient.data.classifications.ClassificationRequest
import com.example.hobappclient.data.memberships.MembershipRequest
import com.example.hobappclient.data.messages.MessageRequest
import com.example.hobappclient.data.rooms.RoomRequest
import com.example.hobappclient.data.usersgroups.UserGroupRequest
import com.example.hobappclient.databinding.FragmentCreateGroupBinding
import com.example.hobappclient.databinding.FragmentGroupsSearchBinding
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.viewmodels.CategoriesListModelView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateGroupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val iHobApi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

    private var _binding : FragmentCreateGroupBinding? = null
    private val binding get() = _binding!!

    private lateinit var categorySpinner: Spinner

    private lateinit var categoriesList: ArrayList<CategoryResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGroupBinding.inflate(inflater,container,false)
        categoriesList = ArrayList()
        initComponents()
        onBackMobileButton()
        return binding.root
    }

    private fun initComponents() {
        initCategoriesList()
        initButtonCreate()
    }



    private fun initCategoriesList() {
        val categoriesViewModel = ViewModelProvider(this)[CategoriesListModelView::class.java]
        categoriesViewModel.getCategoriesListObserver()
            .observe(viewLifecycleOwner, Observer<ArrayList<CategoryResponse>?> {
                if (it != null) {
                    categoriesList = it
                    categorySpinner = binding.dcgCategorySpinner
                    val arrayAdapter = activity?.let {
                        ArrayAdapter<String>(
                            it,
                            android.R.layout.simple_dropdown_item_1line,
                            categoriesList.map { x-> x.name }.toList()
                        )
                    }
                    categorySpinner.adapter = arrayAdapter
                }
            })
        param1?.let { categoriesViewModel.makeApiCall(it) }
    }

    private fun initButtonCreate() {
        binding.button2.setOnClickListener {
            var sended = false
            val groupName = binding.dcgNameEditText.text.toString()
            val groupDescription = binding.dcgDescriptionEditText.text.toString()
            val category = categorySpinner.selectedItemPosition
            if(!groupName.isNullOrBlank()&&!groupDescription.isNullOrBlank()){
                val token = "Bearer $param1"
                val group = param2?.let { it1 ->
                    UserGroupRequest(
                        groupName,
                        groupDescription,
                        "https://cdn0.iconfinder.com/data/icons/users-android-l-lollipop-icon-pack/24/group2-512.png",
                        it1.toInt()
                    )
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val groupResponse =
                        group?.let { it1 -> iHobApi.postUserGroup(token, it1) }
                    if (groupResponse != null) {
                        if(groupResponse.isSuccessful && groupResponse.body() !=null){
                            val groupBody = groupResponse.body()

                            CoroutineScope(Dispatchers.IO).launch {
                                val membership = groupBody?.let { it1 ->
                                    param2?.let { it2 ->
                                        MembershipRequest(
                                            it2.toInt(),
                                            it1.id, "owner"
                                        )
                                    }
                                }
                                val membershipResponse  = membership?.let { it1 ->
                                    iHobApi.postMembership(token, it1)
                                }

                            }

                            CoroutineScope(Dispatchers.IO).launch {
                                val classification = groupBody?.let { it1 ->
                                    ClassificationRequest(
                                        category,
                                        it1.id
                                    )
                                }

                                val classificationResponse = classification?.let { it1 ->
                                    iHobApi.postClassifications(token,
                                        it1
                                    )
                                }

                            }

                            CoroutineScope(Dispatchers.IO).launch {
                                val room1 = groupBody?.let { it1 ->
                                    param2?.let { it2 ->
                                        RoomRequest(
                                            "Rules",
                                            "Rules of group",
                                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiQXlC2tvUMDTtAWWiHN_aALDW22RULpEtXWhvc2jLZK4eg6X2bT6sOqyXlLNW9mrtg3Y&usqp=CAU",
                                            it1.id, it2.toInt()
                                        )
                                    }
                                }
                                val room1Response = room1?.let {
                                        it1 -> iHobApi.postRoom(token, it1) }
                                if (room1Response != null) {
                                    if (room1Response.isSuccessful && room1Response.body()!=null){
                                        val message1 = param2?.let { it1 ->
                                            MessageRequest("Here comes the rules of the group",
                                                room1Response.body()!!.id,
                                                it1.toInt()
                                            )
                                        }
                                        val message1Response = message1?.let {
                                                it1 -> iHobApi.postMessage(token, it1) }
                                        if (message1Response != null) {
                                            if (message1Response.isSuccessful && message1Response.body() != null){

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onBackMobileButton() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
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
         * @return A new instance of fragment CreateGroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateGroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}