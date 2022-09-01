package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Room.Entity.Cstmr
import com.bdtask.restoraposroomdbtab.Util.Util
import com.bdtask.restoraposroomdbtab.databinding.DialogAddNewCustomerBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddCustomer(context: Context): Dialog(context) {
    private lateinit var binding: DialogAddNewCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddNewCustomerBinding.bind(layoutInflater.inflate(R.layout.dialog_add_new_customer,null))
        setContentView(binding.root)

        binding.root.setOnClickListener {
            Util.hideSoftKeyBoard(context,binding.root)
        }
        binding.addCview.setOnClickListener{
            Util.hideSoftKeyBoard(context,binding.addCview)
        }
        binding.crossBtn.setOnClickListener {
            onBackPressed()
        }
        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        binding.submitBtn.setOnClickListener {
            if (binding.cusNameEt.text.toString().isEmpty()){
                binding.cusNameEt.setError("Name is Empty")
                binding.cusNameEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusEmailEt.text.toString().isEmpty()){
                binding.cusEmailEt.setError("Email is Empty")
                binding.cusEmailEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusMobileEt.text.toString().isEmpty()){
                binding.cusMobileEt.setError("Mobile is Empty")
                binding.cusMobileEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusAddEt.text.toString().isEmpty()){
                binding.cusAddEt.setError("Address is Empty")
                binding.cusAddEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusNameEt.text.toString().isEmpty()){
                binding.cusNameEt.setError("Favourite Address is Empty")
                binding.cusNameEt.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch {
                MainActivity.database.customerDao().insertCustomer(
                    Cstmr(0,binding.cusNameEt.text.toString(),binding.cusEmailEt.text.toString(),
                        binding.cusMobileEt.text.toString(),binding.cusAddEt.text.toString(),binding.cusNameEt.text.toString())
                )
            }
            Toasty.success(context,"Successful", Toast.LENGTH_SHORT, true).show()
            onBackPressed()
        }
    }
}