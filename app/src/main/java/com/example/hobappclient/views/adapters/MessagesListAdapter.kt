package com.example.hobappclient.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hobappclient.R
import com.example.hobappclient.data.messages.MessageResponse
import com.example.hobappclient.data.users.UserResponse
import java.sql.Date
import java.util.logging.Logger

class MessagesListAdapter(
    private val context: Context?,
    private val param1: String?,
    private val param2: String?
): RecyclerView.Adapter<MessagesListAdapter.ViewHolder>() {
    private var items: ArrayList<MessageResponse> = ArrayList()
    private var userList : ArrayList<UserResponse> = ArrayList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView : TextView = this.itemView.findViewById(R.id.imv_title_textView)
        val contextTextView : TextView = this.itemView.findViewById(R.id.imv_content_textView)
        val imgView: ImageView = this.itemView.findViewById(R.id.imv_imageView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message_card, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.isNullOrEmpty()){
            val defaultMessage = MessageResponse(
                1,
                1,
                1,
                "",
                Date.valueOf("1988-12-12"),
                Date.valueOf("1988-12-12")
            )
            items.add(defaultMessage)
        }
        try{
        val owner : UserResponse =userList.filter { u -> u.id == items[position].owner }.first()
        if(!owner.image_url.isNullOrEmpty())
            context?.let { Glide.with(it).load(owner.image_url).override(108).into(holder.imgView) }
        else{
            holder.imgView.setImageResource(R.drawable.profile_foreground)
        }
            holder.titleTextView.text = "${items[position].created_at.toString()} ${owner.username} :"
        }catch (e:Exception){
            Log.e("MessageListAdapter","Error : "+e.message)
        }

        holder.contextTextView.text = items[position].content
    }

    fun setUpdateData(it: ArrayList<MessageResponse>) {
        items = it
        notifyDataSetChanged()
    }
    fun setUpdateUsersData(it: ArrayList<UserResponse>) {
        userList = it
        notifyDataSetChanged()
    }
}