package com.example.hobappclient.views.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hobappclient.R
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.views.fragments.RoomViewFragment
import java.sql.Date
import kotlin.collections.ArrayList

class RoomListAdapter(
    private val context: Context?,
    private val param1: String?,
    private val param2: String?,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {

    private var items: ArrayList<RoomResponse> = ArrayList()
    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val titleTextView : TextView = this.itemView.findViewById(R.id.irv_title_textView)
        val descriptionTextView : TextView = this.itemView.findViewById(R.id.irv_description_textView)
        val imgView: ImageView = this.itemView.findViewById(R.id.irv_imageview)
        val enterButton : Button = this.itemView.findViewById(R.id.irv_enter_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.isNullOrEmpty()){
            val defaultRoom = RoomResponse(
                0,
                "There is no Rooms",
                "",
                "",
                Date.valueOf("1988-12-12"),
                Date.valueOf("1988-12-12"),
                0,
                0
            )
            items.add(defaultRoom)
            holder.enterButton.visibility = View.GONE
        }
        if(!items[position].image_url.isNullOrEmpty())
            context?.let { Glide.with(it).load(items[position].image_url).override(108).into(holder.imgView) }
        else{
            holder.imgView.setImageResource(R.drawable.ic_baseline_chat_24)
        }
        holder.titleTextView.text = items[position].name
        holder.descriptionTextView.text = items[position].description
        holder.enterButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("useraccess",param1)
            bundle.putString("userid",param2)
            bundle.putString("room",items[position].id.toString())
            val fragment = RoomViewFragment()
            fragment.arguments = bundle
            fragmentManager.beginTransaction().replace(R.id.mv_fragmentContainerView,fragment).commit()
        }
    }

    override fun getItemCount(): Int {
      //  var counter : Int = 0
       // if (items.isNullOrEmpty()) {
       //    counter = 1
      //  }
       // else {
       //     counter = items.size
       // }
       // return counter
        return items.size
    }

    fun setUpdateData(it: ArrayList<RoomResponse>) {
        items = it
        notifyDataSetChanged()
    }


}