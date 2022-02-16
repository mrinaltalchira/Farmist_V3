package com.android.farmist.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.android.farmist.R
import kotlinx.android.synthetic.main.activity_main.*

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
//
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
        bottomNavigationView.setupWithNavController(navController,)

//
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            if(item.itemId != bottomNavigationView.selectedItemId)
//                NavigationUI.onNavDestinationSelected(item, navController)
//            true
//        }

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