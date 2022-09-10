package com.bdtask.restoraposroomdbtab

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bdtask.restoraposroomdbtab.Dialog.AddPaymentDialog
import com.bdtask.restoraposroomdbtab.Dialog.VatChargeDialog
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.Room.PosDatabase
import com.google.android.material.navigation.NavigationView


class MainActivity: AppCompatActivity() {

    companion object{
        lateinit var drawerLayout:DrawerLayout
        lateinit var navDrawer: NavigationView
        lateinit var database: PosDatabase
        var foodList = mutableListOf<Food>()
        lateinit var posLogo: Uri
        var appCurrency = "$"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        navDrawer = findViewById(R.id.navDrawer)




        // getting instance of ROOM database
        database = PosDatabase.getDatabaseInstance(this)




        database.foodDao().getAllFood().observe(this, Observer{
            foodList.clear()
            foodList = it.toMutableList()
        })




        // Nav Drawer Controller
        navDrawer.setNavigationItemSelectedListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            when(it.itemId){

                R.id.addFoodD -> findNavController(R.id.navController).navigate(R.id.homeFrag2foodFrag)

            R.id.addPayD -> {
                    val dialog = AddPaymentDialog(this)
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width)/7,WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                R.id.setVatD -> {
                    val dialog = VatChargeDialog(
                        this,
                        0,
                        "Add Global Vat in %",
                        "Enter Vat in % here",
                        supportFragmentManager
                    )
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width)/7,WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                R.id.setCrgD -> {
                    val dialog = VatChargeDialog(
                        this,
                        1,
                        "Add Global Service Charge in %",
                        "Enter Service Charge in % here",
                        supportFragmentManager
                    )
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width)/7,WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                R.id.setCurr -> {
                    val dialog = VatChargeDialog(
                        this,
                        2,
                        "Set Global Currency",
                        "Selected Currency Will Appear Here",
                        supportFragmentManager
                    )
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }

                R.id.setOp -> {
                    val dialog = VatChargeDialog(
                        this,
                        3,
                        "Set Operator's Name",
                        "Enter Name here",
                        supportFragmentManager
                    )
                    dialog.show()
                    val width = resources.displayMetrics.widthPixels
                    val win = dialog.window
                    win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
                    win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
            return@setNavigationItemSelectedListener true
        }

        posLogo = Uri.parse("android.resource://com.bdtask.restoraposroomdbtab/drawable/poslogo")
    }
}
