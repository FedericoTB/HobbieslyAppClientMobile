package com.example.hobappclient.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hobappclient.MainActivity
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import com.example.hobappclient.databinding.FragmentUserListGroupsViewBinding
import com.example.hobappclient.viewmodels.UserGroupsListViewModel
import com.example.hobappclient.views.adapters.UserGroupListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"
/**
 * A simple [Fragment] subclass.
 * Use the [UserListGroupsViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserListGroupsViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentUserListGroupsViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var userGroupList : ArrayList<UserGroupResponse>
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : UserGroupListAdapter

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
    ): View {
        userGroupList = ArrayList()
        _binding = FragmentUserListGroupsViewBinding.inflate(inflater,container,false)
        recyclerView = binding.ulgfRecyclerview
        initRecycler()
        initViewModel()
        return binding.root
    }

    private fun initRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = UserGroupListAdapter(context,param1,param2,parentFragmentManager)
        recyclerView.adapter = adapter
    }


    private fun initViewModel(){
        val viewModel = ViewModelProvider(this)[UserGroupsListViewModel::class.java]
        viewModel.getUserGroupsListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setUpdateData(it)
            } else Toast.makeText(
                activity,
                "Error getting UserGroupData for Recycler",
                Toast.LENGTH_LONG
            ).show()
        }
        param1?.let { param2?.let { it1 -> viewModel.makeApiCallOwnUser(it, it1) } }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserListGroupsViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserListGroupsViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}