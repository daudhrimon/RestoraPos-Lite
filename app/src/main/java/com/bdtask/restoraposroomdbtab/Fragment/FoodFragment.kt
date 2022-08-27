package com.bdtask.restoraposroomdbtab.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.MainActivity.Companion.foodList
import com.bdtask.restoraposroomdbtab.Adapter.*
import com.bdtask.restoraposroomdbtab.Room.Entity.Category
import com.bdtask.restoraposroomdbtab.Model.Addon
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Model.Variant
import com.bdtask.restoraposroomdbtab.Room.Entity.Food
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FoodFragment : Fragment() {
    private lateinit var foodBinding: FragmentFoodBinding
    private var btmBinding: BtmsheetItemEditDeleteBinding? = null
    private var categoryList = mutableListOf<Category>()
    private var fCategoryList = mutableListOf<String>()
    private var tempVariantList = mutableListOf<Variant>()
    private var tempAddonsList = mutableListOf<Addon>()
    private var spinnerCategory = ""
    private var foodImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        foodBinding = FragmentFoodBinding.inflate(inflater, container, false)

        // getting category from database and setting them to Spinner
        getCategoryLive()

        // hideKeyboard to Touch on Screen
        foodBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),foodBinding.root)
            foodBinding.foodTitleEt.clearFocus()
        }
        foodBinding.secondView.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),foodBinding.root)
            foodBinding.foodTitleEt.clearFocus()
        }

        // BackButton On Click
        foodBinding.foodBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // setting Category Item From Spinner
        foodBinding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spinnerCategory = foodBinding.categorySpinner.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }

        // Category Add Button On Click
        foodBinding.cateAddBtn.setOnClickListener{
            addNewCategoryDialog()
            foodBinding.foodTitleEt.clearFocus()
        }

        // Category Add Button On Click
        tempAddonsList.clear()
        foodBinding.variantPlusBtn.setOnClickListener{
            variantPlusButtonDialog()
            foodBinding.foodTitleEt.clearFocus()
        }

        // on longClick at CategoryButton will show all category in a BottomSheet
        // and user can edit and delete them
        foodBinding.cateAddBtn.setOnLongClickListener {
            showCategoryBtmSheet()
            foodBinding.foodTitleEt.clearFocus()
            return@setOnLongClickListener true
        }

        foodBinding.addImageBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop(1.4f,1f)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
            foodBinding.foodTitleEt.clearFocus()
        }

        //add addons
        tempAddonsList.clear()
        foodBinding.addAddonsBtn.setOnClickListener {
            addAddonsButtonClick()
            foodBinding.foodTitleEt.clearFocus()
        }

        // add Food
        foodBinding.addFoodBtn.setOnClickListener {
            addFoodBtnClickHandler()
        }

        return foodBinding.root
    }

    // getting category from database and setting them to Spinner
    private fun getCategoryLive() {
        MainActivity.database.categoryDao().getAllCategory().observe(viewLifecycleOwner, Observer {
            categoryList.clear()
            fCategoryList.clear()
            categoryList = it.toMutableList()

            for (i in categoryList.indices){
                fCategoryList.add(categoryList[i].fCategory)
            }

            // setting Spinner ADAPTER
            foodBinding.categorySpinner.adapter = ArrayAdapter(requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, fCategoryList)

            // setting BottomSheet Recycler
            if (btmBinding != null){
                btmBinding?.btmRecycler?.adapter = CategoryBtmAdapter(requireContext(), categoryList)
            }
        })
    }


    // bottomSheet for edit or Update Categories
    private fun showCategoryBtmSheet() {
        val btmSheet = BottomSheetDialog(requireContext())
        btmBinding = BtmsheetItemEditDeleteBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.btmsheet_item_edit_delete,null))
        btmSheet?.setContentView(btmBinding!!.root)
        btmBinding?.btmHeader?.text = "Customize Categories"
        btmSheet?.show()

        btmBinding?.btmCrossBtn?.setOnClickListener {
            btmBinding = null
            btmSheet?.dismiss()
        }

        btmBinding?.btmRecycler?.adapter = CategoryBtmAdapter(requireContext(),categoryList)
    }


    // show dialog for add new category
    private fun addNewCategoryDialog() {
        val dialog = Dialog(requireContext())
        val dbinding = DialogSingleItemetBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_single_itemet,null))
        dialog.setContentView(dbinding.root)

        dbinding.itemTv.text = "Add a new Category"
        dbinding.itemEt.hint = "Enter Category"
        dbinding.itemBtn.text = "Add Category"

        dbinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(),dbinding.root) }

        dbinding.itemCross.setOnClickListener { dialog.dismiss() }

        dbinding.itemBtn.setOnClickListener {
            if (dbinding.itemEt.text.toString().isEmpty()){
                dbinding.itemEt.setError("Enter Category")
                dbinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            for (i in fCategoryList.indices){
                if (fCategoryList[i] == dbinding.itemEt.text.toString()){
                    Toasty.error(requireContext(),"Already Available",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }
            }

            GlobalScope.launch {
                MainActivity.database.categoryDao().insertCategory(Category(0,dbinding.itemEt.text.toString().trim()))
            }

            Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun variantPlusButtonDialog() {
        val dialog = Dialog(requireContext())
        val dbinding = DialogInsertAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon,null))
        dialog.setContentView(dbinding.root)

        dbinding.addonHeaderTv.text = "Add Food Variant"
        dbinding.addonNameEt.hint = "Enter Variant"
        dbinding.addonPriceEt.hint = "Enter Price"

        dbinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(),dbinding.root) }

        dbinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dbinding.addonAddBtn.setOnClickListener {
            if (dbinding.addonNameEt.text.toString().isEmpty()){
                dbinding.addonNameEt.setError("Enter Variant")
                dbinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempVariantList.indices){
                if (tempVariantList[i].variant == dbinding.addonNameEt.text.toString()){
                    Toasty.error(requireContext(),"Already Available",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }
            }

            if (dbinding.addonPriceEt.text.toString().isEmpty()){
                dbinding.addonPriceEt.setError("Enter Price")
                dbinding.addonPriceEt.requestFocus()
                return@setOnClickListener
            }

            tempVariantList.add(Variant(dbinding.addonNameEt.text.toString(), dbinding.addonPriceEt.text.toString().toDouble()))

            foodBinding.variantRecycler.adapter = TempVariantAdapter(requireContext(), tempVariantList)

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    // addonsButton click Handler
    private fun addAddonsButtonClick() {
        val dialog = Dialog(requireContext())
        val dbinding = DialogInsertAddonBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_insert_addon,null))
        dialog.setContentView(dbinding.root)

        var addonPrice: Double = 0.0

        dbinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(),dbinding.root) }

        dbinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dbinding.addonAddBtn.setOnClickListener {
            if (dbinding.addonNameEt.text.toString().isEmpty()){
                dbinding.addonNameEt.setError("Enter Addon")
                dbinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempAddonsList.indices){
                if (tempAddonsList[i].adnName == dbinding.addonNameEt.text.toString()){
                    Toasty.error(requireContext(),"Already Available",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }
            }

            if (dbinding.addonPriceEt.text.toString().isNotEmpty()){
                addonPrice = dbinding.addonPriceEt.text.toString().toDouble()
            }

            tempAddonsList.add(Addon(dbinding.addonNameEt.text.toString(),addonPrice))

            foodBinding.addonsRecycler.adapter = TempAddonsAdapter(requireContext(),tempAddonsList)

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width)/7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    // add Food to Database
    private fun addFoodBtnClickHandler() {
        if(spinnerCategory.isEmpty()){
            Toasty.error(requireContext(),"Add Food Categories", Toast.LENGTH_SHORT, true).show()
            return
        }
        if (foodBinding.foodTitleEt.text.toString().isEmpty()){
            foodBinding.foodTitleEt.setError("Enter Food Title")
            foodBinding.foodTitleEt.requestFocus()
            return
        }

        if (tempVariantList.size < 1){
            Toasty.error(requireContext(),"Variant Empty", Toast.LENGTH_SHORT, true).show()
            return
        }

        if (foodImage.isEmpty()){
            Toasty.error(requireContext(),"Add a FoodImage", Toast.LENGTH_SHORT, true).show()
            return
        }

        for (i in foodList.indices){
            if (foodList[i].fCategory == spinnerCategory && foodList[i].fTitle == foodBinding.foodTitleEt.text.toString()){
                Toasty.error(requireContext(),"This Food already Available", Toast.LENGTH_SHORT, true).show()
                return
            }
        }

        GlobalScope.launch {
            MainActivity.database.foodDao().insertFood(
                Food(0, spinnerCategory, foodBinding.foodTitleEt.text.toString(), tempVariantList.toList(), foodImage, tempAddonsList.toList())
            )
        }
        Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
        foodBinding.foodTitleEt.text.clear()
        foodBinding.addImageBtn.setImageResource(R.drawable.add_image)
        foodBinding.variantRecycler.adapter = null
        foodBinding.addonsRecycler.adapter = null
        tempVariantList.clear()
        tempAddonsList.clear()
    }


    // image picker RESULT
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                foodImage = fileUri.toString()
                foodBinding.addImageBtn.setImageURI(fileUri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toasty.error(requireContext(),ImagePicker.getError(data), Toast.LENGTH_SHORT, true).show()
            } else {
                Toasty.error(requireContext(),"Cancelled", Toast.LENGTH_SHORT, true).show()
            }
        }
}