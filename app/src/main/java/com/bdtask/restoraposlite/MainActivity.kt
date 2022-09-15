package com.bdtask.restoraposlite

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bdtask.restoraposlite.Dialog.AddCustomerDialog
import com.bdtask.restoraposlite.Dialog.AddPaymentDialog
import com.bdtask.restoraposlite.Dialog.VatChargeDialog
import com.bdtask.restoraposlite.Room.Entity.Food
import com.bdtask.restoraposlite.Room.AppDatabase
import com.bdtask.restoraposlite.Util.SharedPref
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.navigation.NavigationView
import es.dmoral.toasty.Toasty

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

            drawerLayout.closeDrawer(GravityCompat.START)

            drawerLayout.postDelayed({

                when(it.itemId){

                    R.id.addFoodD -> {
                        findNavController(R.id.navController).navigate(R.id.homeFrag2foodFrag)
                    }

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

                    R.id.setInf -> {
                        val dialog = AddCustomerDialog(this,1)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.show()
                        val width = resources.displayMetrics.widthPixels
                        val win = dialog.window
                        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
                        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }

                    R.id.setLogo -> {
                        ImagePicker.with(this)
                            .crop(5f,2f)
                            .compress(48)         //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(167, 65)  //Final image resolution will be less than 1080 x 1080(Optional)
                            .createIntent { intent ->
                                startForProfileImageResult.launch(intent)
                            }
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

                    R.id.salesRepo -> {
                        findNavController(R.id.navController).navigate(R.id.homeFrag2repoFrag)
                    }

                    R.id.premium -> {
                        findNavController(R.id.navController).navigate(R.id.homeFrag2aboutUs)
                    }
                }

            },191)

            return@setNavigationItemSelectedListener true
        }
    }


    // image picker RESULT
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    SharedPref.init(this)
                    SharedPref.writePosLogo(fileUri.toString())
                    Toasty.success(this,"Added POS Logo Successfully",Toasty.LENGTH_SHORT).show()
                }

                ImagePicker.RESULT_ERROR -> {
                    Toasty.error(this, ImagePicker.getError(data), Toast.LENGTH_SHORT, true).show()
                }

                else -> {
                    Toasty.error(this,"Cancelled", Toast.LENGTH_SHORT, true).show()
                }
            }
        }

}
