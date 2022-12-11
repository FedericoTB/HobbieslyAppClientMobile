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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hobappclient.MainActivity
import com.example.hobappclient.R
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import com.example.hobappclient.databinding.FragmentGroupDetailViewBinding
import com.example.hobappclient.viewmodels.RoomsListViewModel
import com.example.hobappclient.viewmodels.UserGroupViewModel
import com.example.hobappclient.views.adapters.RoomListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"
private const val ARG_PARAM3 = "group"
/**
 * A simple [Fragment] subclass.
 * Use the [GroupViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupDetailViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var _binding : FragmentGroupDetailViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var userGroup:UserGroupResponse
    private lateinit var roomsList: ArrayList<RoomResponse>
    private lateinit var adapter : RoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupDetailViewBinding.inflate(inflater,container,false)
        userGroup = UserGroupResponse(0,"","","",null,0)
        roomsList = ArrayList()
        initData()
        initComponents()
        onBackMobileButton()
        return binding.root
    }

    private fun initData() {
        initUserGroupData()
        initRoomsData()
    }

    private fun initUserGroupData() {
        val userGroupViewModel: UserGroupViewModel = ViewModelProvider(this)[UserGroupViewModel::class.java]
        userGroupViewModel.getUserGroupObserver().observe(viewLifecycleOwner, Observer<UserGroupResponse?> {
            if(it !=null){
                userGroup = it
                if (!userGroup.image_url.isNullOrEmpty()) {
                    context?.let { it1 -> Glide.with(it1).load(userGroup.image_url).into(binding.gdvImageImageView) }
                }
                else{
                    binding.gdvImageImageView.setImageResource(R.drawable.ic_baseline_groups_24)
                }
                binding.gdvTitleTextView.text = userGroup.name
            }
        })
        param1?.let { param3?.let { it1 -> userGroupViewModel.makeApiCall(it, it1) } }
    }

    private fun initRoomsData() {
        val roomsViewModel : RoomsListViewModel = ViewModelProvider(this)[RoomsListViewModel::class.java]
        roomsViewModel.getRoomsListObserver().observe(viewLifecycleOwner,Observer<ArrayList<RoomResponse>?>{
            if(it !=null){
                adapter.setUpdateData(it)
            }
        })
        param1?.let { param3?.let { it1 -> roomsViewModel.makeApiCall(it, it1) } }
    }

    private fun initComponents() {
        val recycleView = binding.gdvRecycler
        recycleView.layoutManager=LinearLayoutManager(activity)
        adapter = RoomListAdapter(context,param1,param2,parentFragmentManager)
        adapter.setUpdateData(roomsList)
        recycleView.adapter = adapter
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
         * @return A new instance of fragment GroupViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3:String) =
            GroupDetailViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }

}