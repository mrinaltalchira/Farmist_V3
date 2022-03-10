package com.android.farmist.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.android.farmist.R
import com.android.farmist.api.Api_Controller
import com.android.farmist.model.getUserInfo.getUserModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.farmist.fragments.Account_Fragment
import com.android.farmist.fragments.Alerts_Fragment
import com.android.farmist.fragments.Home_Fragment
import com.android.farmist.fragments.Prices_Fragment
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessaging.getInstance
import com.google.firebase.messaging.FirebaseMessagingService
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        val firstFragment = Home_Fragment()
//        val secondFragment = Alerts_Fragment()
//        val thirdFragment = Prices_Fragment()
//        val forthFragment = Account_Fragment()
//
//        setCurrentFragment(firstFragment)
//
//        bottomNavigationView.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.nav_home -> setCurrentFragment(firstFragment)
//                R.id.nav_alerts -> setCurrentFragment(secondFragment)
//                R.id.nav_prices -> setCurrentFragment(thirdFragment)
//                R.id.nav_account -> setCurrentFragment(forthFragment)
//
//            }
//            true
//        }



//
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.navHostFragment
        ) as NavHostFragment
//
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != bottomNavigationView.selectedItemId) NavigationUI.onNavDestinationSelected(
                item,
                navController
            )
            else
            {
                bottomNavigationView.setupWithNavController(navController)
            true

            }

        }

    }
//
//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.navHostFragment, fragment)
//            commit()
//        }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()

    }




}