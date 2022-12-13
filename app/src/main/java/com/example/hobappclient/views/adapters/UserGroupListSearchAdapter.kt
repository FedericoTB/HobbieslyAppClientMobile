package com.example.hobappclient.views.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.hobappclient.data.classifications.ClassificationResponse
import com.example.hobappclient.data.memberships.MembershipResponse
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import com.example.hobappclient.views.fragments.GroupDetailViewFragment
import com.example.hobappclient.views.fragments.RegisterInGroupDialogFragment
import kotlin.collections.ArrayList

class UserGroupListSearchAdapter(
    private val context: Context?,
    private val param1: String?,
    private val param2: String?,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<UserGroupListSearchAdapter.ViewHolder>() {
    private var items: ArrayList<UserGroupResponse> = ArrayList()
    private var currentItems : ArrayList<UserGroupResponse> = items
    private var classfications : ArrayList<ClassificationResponse> = ArrayList()
    private var memberships :ArrayList<MembershipResponse> = ArrayList()

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val titleTextView : TextView = this.itemView.findViewById(R.id.iugls_title_textView)
        val descriptionTextView : TextView = this.itemView.findViewById(R.id.iugls_description_textView)
        val imgView: ImageView = this.itemView.findViewById(R.id.iugls_imgview)
        val enterButton : Button = this.itemView.findViewById(R.id.iugls_enter_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usergroup_search_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentItems.isNullOrEmpty()){
            val group = UserGroupResponse(
                0,
                "There is no Groups",
                "",
                "",
                null,
                0
            )
            currentItems.add(group)
            holder.enterButton.visibility = View.GONE
        }
        if(!currentItems[position].image_url.isNullOrEmpty()) context?.let {
            Glide.with(it).load(currentItems[position].image_url).override(108).into(holder.imgView) }
        else{
            holder.imgView.setImageResource(R.drawable.ic_baseline_groups_24)
        }
        holder.titleTextView.text = currentItems[position].name
        holder.descriptionTextView.text = currentItems[position].description
        holder.enterButton.setOnClickListener {
            try{
                val memberlist = memberships.filter { m -> (m.group == currentItems[position].id) }.map{ u -> u.user}.toList()
                val bundle = Bundle()
                bundle.putString("useraccess",param1)
                bundle.putString("userid",param2)
                bundle.putString("group",items[position].id.toString())
                if(param2?.let { it1 -> memberlist.contains(it1.toInt()) } == true){
                    val fragment = GroupDetailViewFragment()
                    fragment.arguments = bundle
                    fragmentManager.beginTransaction().replace(R.id.mv_fragmentContainerView,fragment).commit()
                }else{
                    val fragment = RegisterInGroupDialogFragment()
                    fragment.arguments = bundle
                    fragment.show(fragmentManager,"Register in Group")

                }
            } catch (e : Exception){
                Log.e("filter","Error in looking in memberships"+e.stackTraceToString())
            }

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
        return currentItems.size
    }

    fun filterData(nameForSearch: String, spinnerSelectedId: Int) {
        var filteredItems = items.filter { u-> u.name.lowercase().contains(nameForSearch) } as ArrayList<UserGroupResponse>
        if(spinnerSelectedId > 0){
            val groupsIdFiltered = classfications.filter { c->c.category==spinnerSelectedId }.map { cf-> cf.group }.toList()
            filteredItems = filteredItems.filter { f-> groupsIdFiltered.contains(f.id) } as ArrayList<UserGroupResponse>
        }
        setUpdateData(filteredItems)
    }

    fun setUpdateData(it: ArrayList<UserGroupResponse>) {
        if(items.isEmpty()){
            items = it
            currentItems = items
            notifyDataSetChanged()
        }else{
            currentItems = it
            notifyDataSetChanged()
        }
    }

    fun setUpdateClassification(classficationList: ArrayList<ClassificationResponse>){
        classfications = classficationList
    }

    fun setUpdateMemberships(membershipsList : ArrayList<MembershipResponse>){
        memberships = membershipsList
    }

}