package com.bdtask.restoraposroomdbtab

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.Room.PosDatabase
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogAddPaymentBinding
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import es.dmoral.toasty.Toasty


class MainActivity: AppCompatActivity() {
    companion object{
        lateinit var drawerLayout:DrawerLayout
        lateinit var navDrawer: NavigationView
        lateinit var database: PosDatabase
        var foodList = mutableListOf<Food>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navDrawer = findViewById(R.id.navDrawer)

        // getting instance of ROOM database
        database = PosDatabase.getDatabaseInstance(this)

        database.foodDao().getAllFood().observe(this, Observer{
            foodList.clear()
            foodList = it.toMutableList()
        })

        // Nav Drawer Controller
        navDrawer.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.addFood -> findNavController(R.id.navController).navigate(R.id.homeFrag2foodFrag)

                R.id.addPay -> {
                    val dialog = Dialog(this)
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width)/7,WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }

            drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }
    }
}
