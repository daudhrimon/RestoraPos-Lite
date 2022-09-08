package com.bdtask.restoraposroomdbtab.Fragment

import android.app.Activity
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
import com.bdtask.restoraposroomdbtab.Room.Entity.Catgry
import com.bdtask.restoraposroomdbtab.Model.Adn
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
    private lateinit var fBinding: FragmentFoodBinding
    private var btmBinding: BtmsheetItemEditDeleteBinding? = null
    private var cateList = mutableListOf<Catgry>()
    private var fCategoryList = mutableListOf<String>()
    private var tempVariantList = mutableListOf<Variant>()
    private var tempAddonsList = mutableListOf<Adn>()
    private var spinnerCategory = ""
    private var foodImage = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fBinding = FragmentFoodBinding.inflate(inflater, container, false)


        // getting category from database and setting them to Spinner
        getCategoryLive()


        // hideKeyboard to Touch on Screen
        fBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),fBinding.root)
            fBinding.foodTitleEt.clearFocus()
        }


        fBinding.secondView.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),fBinding.root)
            fBinding.foodTitleEt.clearFocus()
        }


        // BackButton On Click
        fBinding.foodBack.setOnClickListener {
            findNavController().popBackStack()
        }


        // setting Category Item From Spinner
        fBinding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spinnerCategory = fBinding.categorySpinner.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }


        // Category Add Button On Click
        fBinding.cateAddBtn.setOnClickListener{
            addNewCategoryDialog()
            fBinding.foodTitleEt.clearFocus()
        }


        // Category Add Button On Click
        tempAddonsList.clear()
        fBinding.variantPlusBtn.setOnClickListener{
            variantPlusButtonDialog()
            fBinding.foodTitleEt.clearFocus()
        }


        // on longClick at CategoryButton will show all category in a BottomSheet
        // and user can edit and delete them
        fBinding.cateAddBtn.setOnLongClickListener {
            showCategoryBtmSheet()
            fBinding.foodTitleEt.clearFocus()
            return@setOnLongClickListener true
        }


        fBinding.addImageBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop(1.4f,1f)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
            fBinding.foodTitleEt.clearFocus()
        }

        //add addons
        tempAddonsList.clear()
        fBinding.addAddonsBtn.setOnClickListener {
            addAddonsButtonClick()
            fBinding.foodTitleEt.clearFocus()
        }

        // add Food
        fBinding.addFoodBtn.setOnClickListener {
            addFoodBtnClickHandler()
        }

        return fBinding.root
    }

    // getting category from database and setting them to Spinner
    private fun getCategoryLive() {
        MainActivity.database.categoryDao().getAllCategory().observe(viewLifecycleOwner, Observer {
            cateList.clear()
            fCategoryList.clear()
            cateList = it.toMutableList()

            for (i in cateList.indices){
                fCategoryList.add(cateList[i].fCat)
            }

            // setting Spinner ADAPTER
            fBinding.categorySpinner.adapter = ArrayAdapter(requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, fCategoryList)

            // setting BottomSheet Recycler
            if (btmBinding != null){
                btmBinding?.btmRecycler?.adapter = CategoryBtmAdapter(requireContext(), cateList)
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

        btmBinding?.btmRecycler?.adapter = CategoryBtmAdapter(requireContext(),cateList)
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
                MainActivity.database.categoryDao().insertCategory(Catgry(0,dbinding.itemEt.text.toString().trim()))
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
        val dBinding = DialogInsertAddonBinding.bind(LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon,null))
        dialog.setContentView(dBinding.root)

        dBinding.addonHeaderTv.text = "Add Food Variant"
        dBinding.addonNameEt.hint = "Enter Variant"
        dBinding.addonPriceEt.hint = "Enter Price"

        dBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(),dBinding.root) }

        dBinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dBinding.addonAddBtn.setOnClickListener {
            if (dBinding.addonNameEt.text.toString().isEmpty()){
                dBinding.addonNameEt.setError("Enter Variant")
                dBinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempVariantList.indices){
                if (tempVariantList[i].vari == dBinding.addonNameEt.text.toString()){
                    Toasty.error(requireContext(),"Already Available",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }
            }

            if (dBinding.addonPriceEt.text.toString().isEmpty()){
                dBinding.addonPriceEt.setError("Enter Price")
                dBinding.addonPriceEt.requestFocus()
                return@setOnClickListener
            }

            tempVariantList.add(Variant(dBinding.addonNameEt.text.toString(), dBinding.addonPriceEt.text.toString().toDouble()))

            fBinding.variantRecycler.adapter = TempVariantAdapter(requireContext(), tempVariantList)

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
        val dBinding = DialogInsertAddonBinding.bind(LayoutInflater.from(requireContext()).inflate(R.layout.dialog_insert_addon,null))
        dialog.setContentView(dBinding.root)

        var addonPrice: Double = 0.0

        dBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(),dBinding.root) }

        dBinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dBinding.addonAddBtn.setOnClickListener {
            if (dBinding.addonNameEt.text.toString().isEmpty()){
                dBinding.addonNameEt.setError("Enter Addon")
                dBinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempAddonsList.indices){
                if (tempAddonsList[i].adnNm == dBinding.addonNameEt.text.toString()){
                    Toasty.error(requireContext(),"Already Available",Toast.LENGTH_SHORT,true).show()
                    return@setOnClickListener
                }
            }

            if (dBinding.addonPriceEt.text.toString().isNotEmpty()){
                addonPrice = dBinding.addonPriceEt.text.toString().toDouble()
            }

            tempAddonsList.add(Adn(dBinding.addonNameEt.text.toString(),addonPrice))

            fBinding.addonsRecycler.adapter = TempAddonsAdapter(requireContext(),tempAddonsList)

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
        if (fBinding.foodTitleEt.text.toString().isEmpty()){
            fBinding.foodTitleEt.setError("Enter Food Title")
            fBinding.foodTitleEt.requestFocus()
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
            if (foodList[i].fCate == spinnerCategory && foodList[i].fTitle == fBinding.foodTitleEt.text.toString()){
                Toasty.error(requireContext(),"This Food already Available", Toast.LENGTH_SHORT, true).show()
                return
            }
        }

        GlobalScope.launch {
            MainActivity.database.foodDao().insertFood(
                Food(0, spinnerCategory, fBinding.foodTitleEt.text.toString(), tempVariantList, foodImage, tempAddonsList.toList())
            )
        }
        Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
        fBinding.foodTitleEt.text.clear()
        fBinding.addImageBtn.setImageResource(R.drawable.add_image)
        fBinding.variantRecycler.adapter = null
        fBinding.addonsRecycler.adapter = null
        tempVariantList.clear()
        tempAddonsList.clear()
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
                    foodImage = fileUri.toString()
                    fBinding.addImageBtn.setImageURI(fileUri)
                }

                ImagePicker.RESULT_ERROR -> {
                    Toasty.error(requireContext(),ImagePicker.getError(data), Toast.LENGTH_SHORT, true).show()
                }

                else -> {
                    Toasty.error(requireContext(),"Cancelled", Toast.LENGTH_SHORT, true).show()
                }
            }
        }
}