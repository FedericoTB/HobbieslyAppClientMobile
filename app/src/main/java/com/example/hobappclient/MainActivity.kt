package com.example.hobappclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hobappclient.views.fragments.GroupsSearchFragment
import com.example.hobappclient.views.fragments.ProfileFragment
import com.example.hobappclient.views.fragments.SettingsFragment
import com.example.hobappclient.views.fragments.UserListGroupsViewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bundle : Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bundle = Bundle()
        bundle.putString("useraccess",intent.getStringExtra("useraccess").toString())
        bundle.putString("userid",intent.getStringExtra("userid"))

        setContentView(R.layout.activity_main)

        initcomponents()
    }

    private fun initcomponents() {
        val bottonNavigationMenu = findViewById<BottomNavigationView>(R.id.mv_BottonNavView)

       replaceFragment(UserListGroupsViewFragment())
        bottonNavigationMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.mv_home_bNavB -> replaceFragment(UserListGroupsViewFragment())
                R.id.mv_search_bNavB -> replaceFragment(GroupsSearchFragment())
                R.id.mv_profile_bNavB -> replaceFragment(ProfileFragment())
                R.id.mv_settings_bNavB -> replaceFragment(SettingsFragment())
            }
             true
        }


    }
    fun replaceFragment(fragment: Fragment){
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.mv_fragmentContainerView,fragment).commit()
    }

}

