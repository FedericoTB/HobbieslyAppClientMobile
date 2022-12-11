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
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import com.example.hobappclient.views.fragments.GroupDetailViewFragment
import kotlin.collections.ArrayList

class UserGroupListAdapter(
    private val context: Context?,
    private val param1: String?,
    private val param2: String?,
    private val fragmentManager: FragmentManager
    ) : RecyclerView.Adapter<UserGroupListAdapter.ViewHolder>() {
    private  var items: ArrayList<UserGroupResponse> = ArrayList()
    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val titleTextView : TextView
        val descriptionTextView : TextView
        val imgView: ImageView
        val enterButton : Button

        init {
            titleTextView = this.itemView.findViewById(R.id.iugl_title_textView)
            descriptionTextView  = this.itemView.findViewById(R.id.iugl_description_textView)
            imgView = this.itemView.findViewById(R.id.iugl_imgview)
            enterButton = this.itemView.findViewById(R.id.iugl_enter_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usergroup_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.isNullOrEmpty()){
            val defaultuser = UserGroupResponse(
                0,
                "There is no Groups",
                "",
                "",
                null,
                0
            )
            items.add(defaultuser)
            holder.enterButton.visibility = View.GONE
        }
        if(!items[position].image_url.isNullOrEmpty())
            context?.let { Glide.with(it).load(items[position].image_url).override(108).into(holder.imgView) }
        else{
            holder.imgView.setImageResource(R.drawable.ic_baseline_groups_24)
        }
        holder.titleTextView.text = items[position].name
        holder.descriptionTextView.text = items[position].description
        holder.enterButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("useraccess",param1)
            bundle.putString("userid",param2)
            bundle.putString("group",items[position].id.toString())
            val fragment = GroupDetailViewFragment()
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

    fun setUpdateData(it: ArrayList<UserGroupResponse>) {
        items = it
        notifyDataSetChanged()
    }

    fun setFilter(nameForSearch: String) {

    }


}