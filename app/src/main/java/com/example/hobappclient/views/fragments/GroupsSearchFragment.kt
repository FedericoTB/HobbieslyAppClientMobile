package com.example.hobappclient.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hobappclient.MainActivity
import com.example.hobappclient.R
import com.example.hobappclient.data.categories.CategoryResponse
import com.example.hobappclient.data.classifications.ClassificationResponse
import com.example.hobappclient.data.memberships.MembershipResponse
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import com.example.hobappclient.databinding.FragmentGroupsSearchBinding
import com.example.hobappclient.viewmodels.CategoriesListModelView
import com.example.hobappclient.viewmodels.ClassicationsListModelView
import com.example.hobappclient.viewmodels.MembershipsListModelView
import com.example.hobappclient.viewmodels.UserGroupsListViewModel
import com.example.hobappclient.views.adapters.UserGroupListSearchAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsSearchFragment : Fragment(){
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentGroupsSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView : RecyclerView
    private lateinit var categorySpinner : Spinner
    private lateinit var groupNameEditText: EditText
    private lateinit var floatingButton: FloatingActionButton

    private lateinit var userGroupList : ArrayList<UserGroupResponse>
    private lateinit var categoriesList : ArrayList<CategoryResponse>
    private lateinit var classificationList : ArrayList<ClassificationResponse>
    private lateinit var membershipsList: ArrayList<MembershipResponse>

    private lateinit var adapter : UserGroupListSearchAdapter

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
        // Inflate the layout for this fragment
        userGroupList = ArrayList()
        categoriesList = ArrayList()
        classificationList = ArrayList()
        _binding = FragmentGroupsSearchBinding.inflate(inflater,container,false)
        initLists()
        initcomponents()
        onBackMobileButton()
        return binding.root
    }

    private fun initLists() {
        initClassificationsList()
        initMembershipsList()
        initCategoriesList()
        initUserGroupsList()
    }



    private fun initUserGroupsList() {
        val groupsViewModel = ViewModelProvider(this)[UserGroupsListViewModel::class.java]
        groupsViewModel.getUserGroupsListObserver().observe(viewLifecycleOwner,
            Observer<ArrayList<UserGroupResponse>?> {
                if (it != null) {
                    adapter.setUpdateData(it)
                } else Toast.makeText(
                    activity,
                    "Error getting UserGroupData",
                    Toast.LENGTH_LONG
                ).show()
            })
        param1?.let { groupsViewModel.makeApiCall(it) }
    }

    private fun initCategoriesList() {
        val categoriesViewModel = ViewModelProvider(this)[CategoriesListModelView::class.java]
        categoriesViewModel.getCategoriesListObserver()
            .observe(viewLifecycleOwner, Observer<ArrayList<CategoryResponse>?> {
                if (it != null) {
                    categoriesList = it
                    categoriesList.add(0, CategoryResponse("None","None Category"))
                    categorySpinner = binding.srchCategorySpinner
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

    private fun initMembershipsList() {
        val membershipsViewModel : MembershipsListModelView = ViewModelProvider(this)[MembershipsListModelView::class.java]
        membershipsViewModel.getClassificationsListObserver().observe(viewLifecycleOwner,Observer<ArrayList<MembershipResponse>?>{
            if(it != null){
                membershipsList = it
                adapter.setUpdateMemberships(membershipsList )
            }
        })
        param1?.let { membershipsViewModel.makeApiCall(it) }
    }

    private fun initClassificationsList() {
        val classificationsViewModel = ViewModelProvider(this)[ClassicationsListModelView::class.java]
        classificationsViewModel.getClassificationsListObserver()
            .observe(viewLifecycleOwner, Observer<ArrayList<ClassificationResponse>?> {
                if (it != null) {
                    adapter.setUpdateClassification(it)
                }
            })
        param1?.let { classificationsViewModel.makeApiCall(it) }
    }

    private fun initcomponents() {
        groupNameEditText = binding.srchGroupnameEditText
        floatingButton = binding.srchFloatingActionButton
        recyclerView = binding.srchGroupsRecycleview
        categorySpinner = binding.srchCategorySpinner
        initRecycler()
        categorySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerSelectedId  = (position)
                val nameForSearch = groupNameEditText.text.toString().lowercase()
                adapter.filterData(nameForSearch,spinnerSelectedId)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        groupNameEditText.addTextChangedListener { text_filter->
           val spinnerSelectedId  = (categorySpinner.selectedItemPosition)
            val nameForSearch = text_filter.toString().lowercase()
            adapter.filterData(nameForSearch,spinnerSelectedId)
        }
        floatingButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("useraccess",param1)
            bundle.putString("userid",param2)
            val fragment = CreateGroupFragment()
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                ?.replace(R.id.mv_fragmentContainerView,fragment)?.commit()
        }
    }

    private fun initRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = UserGroupListSearchAdapter(context,param1,param2,parentFragmentManager)
        adapter.setUpdateData(userGroupList)
        recyclerView.adapter = adapter
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
         * @return A new instance of fragment GroupsSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupsSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}