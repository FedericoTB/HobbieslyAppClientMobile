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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hobappclient.MainActivity
import com.example.hobappclient.R
import com.example.hobappclient.data.messages.MessageRequest
import com.example.hobappclient.data.messages.MessageResponse
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.data.users.UserResponse
import com.example.hobappclient.databinding.FragmentRoomViewBinding
import com.example.hobappclient.network.ApiClient
import com.example.hobappclient.network.IHobApi
import com.example.hobappclient.viewmodels.MessagesViewModel
import com.example.hobappclient.viewmodels.RoomViewModel
import com.example.hobappclient.viewmodels.UsersListViewModel
import com.example.hobappclient.views.adapters.MessagesListAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.sql.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "useraccess"
private const val ARG_PARAM2 = "userid"
private const val ARG_PARAM3 = "room"

/**
 * A simple [Fragment] subclass.
 * Use the [RoomViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomViewFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    private var _binding : FragmentRoomViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : MessagesListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var messagesList : ArrayList<MessageResponse>
    private lateinit var usersList : ArrayList<UserResponse>
    private lateinit var room : RoomResponse

    private val ihobapi: IHobApi = ApiClient.getClient().create(IHobApi::class.java)

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
        // Inflate the layout for this fragment
        _binding = FragmentRoomViewBinding.inflate(inflater,container,false)
        messagesList = ArrayList()
        usersList = ArrayList()
        room = RoomResponse(1,"","","", Date.valueOf("1988-12-12"),Date.valueOf("1988-12-12"),1,1)
        initData()
        initComponents()
        return binding.root
    }

    private fun initData() {
        initRoomData()
        initMessagesData()
        initUsersData()
    }

    private fun initRoomData() {
        val roomViewModel : RoomViewModel = ViewModelProvider(this)[RoomViewModel::class.java]
        roomViewModel.getRoomObserver().observe(viewLifecycleOwner, Observer<RoomResponse?> {
            if(it !=null){
                room = it
                if (!room.image_url.isNullOrEmpty()) {
                    context?.let { it1 -> Glide.with(it1).load(room.image_url).override(75).into(binding.rvImageView) }
                }
                else{
                    binding.rvImageView.setImageResource(R.drawable.ic_baseline_chat_24)
                }
                binding.rvTitleTextView.text = room.name
                binding.rvDescriptionTextView.text = room.description
            }
        })
        param1?.let { param3?.let { it1 -> roomViewModel.makeApiCall(it, it1) } }
    }

    private fun initUsersData() {
        val usersViewModel: UsersListViewModel = ViewModelProvider(this)[UsersListViewModel::class.java]
        usersViewModel.getUsersListObserver().observe(viewLifecycleOwner,Observer<ArrayList<UserResponse>?>{
            if(it != null){
                usersList = it
                adapter.setUpdateUsersData(usersList)
            }
        })
        param1?.let { usersViewModel.makeApiCall(it) }
    }

    private fun initMessagesData() {
        val messagesViewModel : MessagesViewModel = ViewModelProvider(this)[MessagesViewModel::class.java]
        messagesViewModel.getMessagesListObserver().observe(viewLifecycleOwner, Observer<ArrayList<MessageResponse>?>{
            if(it != null){
                messagesList = it
                adapter.setUpdateData(messagesList)
            }
        })
        param1?.let { param3?.let { it1 -> messagesViewModel.makeApiCall(it, it1) } }
    }

    private fun initComponents() {
        recyclerView = binding.rvRecycleView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MessagesListAdapter(context, param1, param2)
        recyclerView.adapter = adapter
        sendButtonListener()
    }


    private fun sendButtonListener() {
        binding.rvSendButton.setOnClickListener {
            if(!binding.rvTextinput.text.isNullOrBlank()) {
                val content = binding.rvTextinput.text.toString()
                val message = param2?.let { it1 -> MessageRequest(content, room.id, it1.toInt()) }
                if (message != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val messagePost =
                            param1?.let { it1 -> ihobapi.postMessage("Bearer $it1", message) }
                        if (messagePost != null) {
                            if (messagePost.isSuccessful && messagePost.body() != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    binding.rvTextinput.text?.clear()
                                    initMessagesData()
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
         * @return A new instance of fragment RoomViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3:String) =
            RoomViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM2, param3)
                }
            }
    }
}