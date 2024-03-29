package com.bdtask.restoraposlite.fragments

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.MainActivity.Companion.foodList
import com.bdtask.restoraposlite.adapters.*
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.room.entity.Category
import com.bdtask.restoraposlite.models.Addons
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.models.Variants
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Food
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class FoodFragment : Fragment() {
    private var _binding: FragmentFoodBinding? = null
    private val fBinding get() = _binding!!
    private var btmBinding: BtmsheetItemEditDeleteBinding? = null
    private var cateList = mutableListOf<Category>()
    private var fCategoryList = mutableListOf<String>()
    private var tempVariantsList = mutableListOf<Variants>()
    private var tempAddonsList = mutableListOf<Addons>()
    private var spinnerCategory = ""
    private var foodImage = ""
    private var food: Food? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        val type = object : TypeToken<Food?>() {}.type
        food = Gson().fromJson<Food>(arguments?.getString("Food"), type)

        if (food != null) {
            val variant = food!!.variants.toList()
            val addons = food!!.addons.toList()

            fBinding.header.text = "Edit Foods"
            fBinding.foodTitleEt.setText(food!!.title)
            fBinding.addFoodBtn.text = "Update"

            tempVariantsList = variant.toMutableList()
            tempAddonsList = addons.toMutableList()

            foodImage = food!!.image
            if (foodImage.isNotEmpty()) {
                fBinding.addImageBtn.setImageURI(foodImage.toUri())
            }

            fBinding.variantRecycler.adapter = TempVariantAdapter(requireContext(), tempVariantsList)
            fBinding.addonsRecycler.adapter = TempAddonsAdapter(requireContext(), tempAddonsList)
        }



        // getting category from database and setting them to Spinner
        AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().getAllCategory().observe(viewLifecycleOwner, Observer {
            cateList.clear()
            fCategoryList.clear()
            cateList = it.toMutableList()

            for (i in cateList.indices) {
                fCategoryList.add(cateList[i].category)
            }

            // setting Spinner ADAPTER
            fBinding.categorySpinner.adapter = ArrayAdapter(
                requireContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                fCategoryList
            )

            // setting BottomSheet Recycler
            if (btmBinding != null) {
                btmBinding?.btmRecycler?.adapter =
                    CategoryBtmAdapter(requireContext(), cateList, lifecycleScope)
            }

            if (food != null) {
                for (i in fCategoryList.indices) {
                    if (fCategoryList[i] == food!!.category) {
                        fBinding.categorySpinner.setSelection(i)
                    }
                }
            }
        })


        // hideKeyboard to Touch on Screen
        fBinding.root.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), fBinding.root)
            fBinding.foodTitleEt.clearFocus()
        }


        fBinding.secondView.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(), fBinding.root)
            fBinding.foodTitleEt.clearFocus()
        }


        // BackButton On Click
        fBinding.foodBack.setOnClickListener {
            findNavController().popBackStack()
        }


        // setting Category Item From Spinner
        fBinding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, position: Int, p3: Long
                ) {
                    spinnerCategory = fBinding.categorySpinner.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {/**/
                }
            }


        // Category Add Button On Click
        fBinding.cateAddBtn.setOnClickListener {
            addNewCategoryDialog()
            fBinding.foodTitleEt.clearFocus()
        }


        // Category Add Button On Click
        fBinding.variantPlusBtn.setOnClickListener {
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
            ImagePicker.with(this).crop(3f, 2f)
                .compress(240)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    360, 240
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
            fBinding.foodTitleEt.clearFocus()
        }

        //add addons
        fBinding.addAddonsBtn.setOnClickListener {
            if (cateList.size > 0) {
                addAddonsButtonClick()
            } else {
                Toasty.warning(
                    requireContext(), "No category is available to Edit", Toasty.LENGTH_SHORT, true
                ).show()
            }
            fBinding.foodTitleEt.clearFocus()
        }

        // add Food
        fBinding.addFoodBtn.setOnClickListener {
            addFoodBtnClickHandler()
        }

        return fBinding.root
    }


    // bottomSheet for edit or Update Categories
    private fun showCategoryBtmSheet() {
        val btmSheet = BottomSheetDialog(requireContext())
        btmBinding = BtmsheetItemEditDeleteBinding.bind(
            LayoutInflater.from(requireContext()).inflate(R.layout.btmsheet_item_edit_delete, null)
        )
        btmSheet?.setContentView(btmBinding!!.root)
        btmBinding?.btmHeader?.text = "Customize Categories"
        btmSheet?.show()

        btmBinding?.btmCrossBtn?.setOnClickListener {
            btmBinding = null
            btmSheet?.dismiss()
        }

        btmBinding?.btmRecycler?.adapter =
            CategoryBtmAdapter(requireContext(), cateList, lifecycleScope)
    }


    // show dialog for add new category
    private fun addNewCategoryDialog() {
        val dialog = Dialog(requireContext())
        val dbinding = DialogSingleItemetBinding.bind(
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_single_itemet, null)
        )
        dialog.setContentView(dbinding.root)

        dbinding.itemTv.text = "Add a new Category"
        dbinding.itemEt.hint = "Enter Category"
        dbinding.itemBtn.text = "Add Category"

        dbinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), dbinding.root) }

        dbinding.itemCross.setOnClickListener { dialog.dismiss() }

        dbinding.itemBtn.setOnClickListener {
            if (dbinding.itemEt.text.toString().isEmpty()) {
                dbinding.itemEt.error = "Enter Category"
                dbinding.itemEt.requestFocus()
                return@setOnClickListener
            }

            for (i in fCategoryList.indices) {
                if (fCategoryList[i] == dbinding.itemEt.text.toString()) {
                    Toasty.error(requireContext(), "Already Available", Toast.LENGTH_SHORT, true)
                        .show()
                    return@setOnClickListener
                }
            }


            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertCategory(Category(0, dbinding.itemEt.text.toString().trim()))

                withContext(Dispatchers.Main) {
                    Toasty.success(requireContext(), "Successful", Toast.LENGTH_SHORT, true).show()
                }
            }

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun variantPlusButtonDialog() {
        val dialog = Dialog(requireContext())
        val dBinding = DialogInsertAddonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.dialog_insert_addon, null)
        )
        dialog.setContentView(dBinding.root)

        dBinding.addonHeaderTv.text = "Add Food Variant"
        dBinding.addonNameEt.hint = "Enter Variant"
        dBinding.addonPriceEt.hint = "Enter Price"

        dBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), dBinding.root) }

        dBinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dBinding.addonAddBtn.setOnClickListener {
            if (dBinding.addonNameEt.text.toString().isEmpty()) {
                dBinding.addonNameEt.setError("Enter Variant")
                dBinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempVariantsList.indices) {
                if (tempVariantsList[i].variant == dBinding.addonNameEt.text.toString()) {
                    Toasty.error(requireContext(), "Already Available", Toast.LENGTH_SHORT, true)
                        .show()
                    return@setOnClickListener
                }
            }

            if (dBinding.addonPriceEt.text.toString().isEmpty()) {
                dBinding.addonPriceEt.setError("Enter Price")
                dBinding.addonPriceEt.requestFocus()
                return@setOnClickListener
            }

            tempVariantsList.add(
                Variants(
                    dBinding.addonNameEt.text.toString(),
                    dBinding.addonPriceEt.text.toString().toDouble()
                )
            )

            fBinding.variantRecycler.adapter = TempVariantAdapter(requireContext(), tempVariantsList)

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    // addonsButton click Handler
    private fun addAddonsButtonClick() {
        val dialog = Dialog(requireContext())
        val dBinding = DialogInsertAddonBinding.bind(
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_insert_addon, null)
        )
        dialog.setContentView(dBinding.root)

        var addonPrice = 0.0

        dBinding.root.setOnClickListener { Util.hideSoftKeyBoard(requireContext(), dBinding.root) }

        dBinding.addonCrossBtn.setOnClickListener { dialog.dismiss() }

        dBinding.addonAddBtn.setOnClickListener {
            if (dBinding.addonNameEt.text.toString().isEmpty()) {
                dBinding.addonNameEt.setError("Enter Addon")
                dBinding.addonNameEt.requestFocus()
                return@setOnClickListener
            }

            for (i in tempAddonsList.indices) {
                if (tempAddonsList[i].name == dBinding.addonNameEt.text.toString()) {
                    Toasty.error(requireContext(), "Already Available", Toast.LENGTH_SHORT, true)
                        .show()
                    return@setOnClickListener
                }
            }

            if (dBinding.addonPriceEt.text.toString().isNotEmpty()) {
                addonPrice = dBinding.addonPriceEt.text.toString().toDouble()
            }

            tempAddonsList.add(Addons(dBinding.addonNameEt.text.toString(), addonPrice))

            fBinding.addonsRecycler.adapter = TempAddonsAdapter(requireContext(), tempAddonsList)

            dialog.dismiss()
        }
        dialog.show()
        val width = resources.displayMetrics.widthPixels
        val win = dialog.window
        win!!.setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    // add Food to Database
    private fun addFoodBtnClickHandler() {
        if (spinnerCategory.isEmpty()) {
            Toasty.error(requireContext(), "Add Food Categories", Toast.LENGTH_SHORT, true).show()
            return
        }
        if (fBinding.foodTitleEt.text.toString().isEmpty()) {
            fBinding.foodTitleEt.setError("Enter Food Title")
            fBinding.foodTitleEt.requestFocus()
            return
        }

        if (tempVariantsList.size < 1) {
            Toasty.error(requireContext(), "Variant is Empty", Toast.LENGTH_SHORT, true).show()
            return
        }

        /*if (foodImage.isEmpty()){
            Toasty.error(requireContext(),"Add a FoodImage", Toast.LENGTH_SHORT, true).show()
            return
        }*/

        for (i in foodList.indices) {
            if (foodList[i].category == spinnerCategory && foodList[i].title == fBinding.foodTitleEt.text.toString() && food == null) {
                Toasty.error(
                    requireContext(), "This Food is already Available", Toast.LENGTH_SHORT, true
                ).show()
                return
            }
        }

        if (food != null) {
            if (food!!.category == spinnerCategory && food!!.title == fBinding.foodTitleEt.text.toString() && food!!.variants == tempVariantsList && food!!.image == foodImage && food!!.addons == tempAddonsList) {
                Log.wtf("FADDONS", food!!.addons.toString())
                Log.wtf("FADDONS", tempAddonsList.toString())

                Toasty.warning(requireContext(), "Nothing Changed Yet", Toasty.LENGTH_SHORT, true)
                    .show()
                return
            } else {
                food!!.category = spinnerCategory
                food!!.title = fBinding.foodTitleEt.text.toString()
                food!!.variants = tempVariantsList
                food!!.image = foodImage
                food!!.addons = tempAddonsList

                lifecycleScope.launch(Dispatchers.IO) {

                    AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().updateFood(food!!)

                    withContext(Dispatchers.Main) {

                        Toasty.success(
                            requireContext(), "Updated Successfully", Toast.LENGTH_SHORT, true
                        ).show()

                        food = null

                        findNavController().popBackStack()

                    }
                }
            }
        } else {
            lifecycleScope.launch(Dispatchers.IO) {

                AppDatabase.getDatabaseInstance(requireContext().applicationContext).AppDao().insertFood(
                    Food(
                        0,
                        spinnerCategory,
                        fBinding.foodTitleEt.text.toString(),
                        tempVariantsList,
                        foodImage,
                        tempAddonsList
                    )
                )

                withContext(Dispatchers.Main) {

                    Toasty.success(requireContext(), "Added Successfully", Toast.LENGTH_SHORT, true)
                        .show()

                    setFragDefault()
                }
            }
        }

    }

    private fun setFragDefault() {
        fBinding.foodTitleEt.text.clear()
        fBinding.addImageBtn.setImageResource(R.drawable.add_image)
        fBinding.variantRecycler.adapter = null
        fBinding.addonsRecycler.adapter = null
        tempVariantsList.clear()
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
                    Toasty.error(
                        requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT, true
                    ).show()
                }

                else -> {
                    Toasty.error(requireContext(), "Cancelled", Toast.LENGTH_SHORT, true).show()
                }
            }
        }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}