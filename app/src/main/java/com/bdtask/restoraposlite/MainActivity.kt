package com.bdtask.restoraposlite

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bdtask.restoraposlite.Room.Entity.Food
import com.bdtask.restoraposlite.Room.AppDatabase
import com.bdtask.restoraposlite.Util.SharedPref
import com.google.android.material.navigation.NavigationView

class MainActivity: AppCompatActivity() {

    companion object{
        lateinit var drawerLayout:DrawerLayout
        lateinit var navDrawer: NavigationView
        lateinit var database: AppDatabase
        var foodList = listOf<Food>()
        var appCurrency = "$"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        navDrawer = findViewById(R.id.navDrawer)


        // getting instance of ROOM database
        database = AppDatabase.getDatabaseInstance(this)


        database.AppDao().getAllFood().observe(this, Observer{
            foodList = it
        })


        // Nav Drawer Controller
        navDrawer.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.addFoodD -> {
                    findNavController(R.id.navController).navigate(R.id.homeFrag2foodFrag)
                }

                R.id.adminPanel -> {
                    findNavController(R.id.navController).navigate(R.id.homeFrag2settingFrag)
                }

                R.id.salesRepo -> {
                    findNavController(R.id.navController).navigate(R.id.homeFrag2repoFrag)
                }

                R.id.premium -> {
                    findNavController(R.id.navController).navigate(R.id.homeFrag2aboutUs)
                }

                R.id.signOut -> {
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("SignOut Alert !")
                    alert.setMessage("Make sure, that you want to do SignOut")

                    alert.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }

                    alert.setPositiveButton("Yes") { dialog, which ->
                        SharedPref.init(this)
                        SharedPref.writeSignIn("")
                        findNavController(R.id.navController).navigate(R.id.homeFrag2splashFrag)
                    }

                    alert.show()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            return@setNavigationItemSelectedListener true
        }
    }
}
