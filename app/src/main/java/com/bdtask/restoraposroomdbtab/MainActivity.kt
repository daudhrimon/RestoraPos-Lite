package com.bdtask.restoraposroomdbtab

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.Room.PosDatabase
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogAddPaymentBinding
import com.bdtask.restoraposroomdbtab.databinding.DialogPaymentBinding
import com.google.android.material.navigation.NavigationView


class MainActivity: AppCompatActivity() {
    companion object{
        lateinit var drawerLayout:DrawerLayout
        lateinit var navDrawer: NavigationView
        lateinit var database: PosDatabase
        var foodList = mutableListOf<Food>()
    }
    private val paymentType = arrayOf("Simple Payment","Card Payment")

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

                R.id.addFood -> findNavController(R.id.nav_graph).navigate(R.id.homeFrag2foodFrag)

                R.id.addPay -> addPaymentMethod()
            }

            drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun addPaymentMethod(){
        val dialog = Dialog(this)
        val binding = DialogAddPaymentBinding.bind(layoutInflater.inflate(R.layout.dialog_add_payment,null))
        dialog.setContentView(binding.root)

        binding.typeSpinner.adapter = ArrayAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,paymentType)

        binding.payCross.setOnClickListener {
            dialog.dismiss()
        }

        binding.root.setOnClickListener {
            Util.hideSoftKeyBoard(this,binding.root)
        }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                when (pos){
                    0 -> {
                        binding.terminal.visibility = View.GONE
                        binding.name.hint = "Enter Payment Name"
                    }
                    1 -> {
                       binding.terminal.visibility = View.VISIBLE
                        binding.name.hint = "Enter Bank Name"
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}

        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7,WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
