package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.bdtask.restoraposlite.MainActivity
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.DialogAddNewCustomerBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCustomerDialog(context: Context,
                        val state: Int): Dialog(context) {

    private lateinit var binding: DialogAddNewCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddNewCustomerBinding.bind(layoutInflater.inflate(R.layout.dialog_add_new_customer,null))
        setContentView(binding.root)

        binding.crossBtn.setOnClickListener {
            dismiss()
        }

        binding.root.setOnClickListener {
            Util.hideSoftKeyBoard(context,binding.root)
        }

        binding.addCview.setOnClickListener{
            Util.hideSoftKeyBoard(context,binding.addCview)
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        if (state == 1){
            binding.cusNameEt.hint = "Restaurant Name"
            binding.cusMobileEt.hint = "Restaurant Mobile no"
            binding.cusAddEt.hint = "Restaurant Address"
        }

        binding.submitBtn.setOnClickListener {
            if (binding.cusNameEt.text.toString().isEmpty()){
                binding.cusNameEt.setError("Name is Empty")
                binding.cusNameEt.requestFocus()
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

            if (state == 0) {
                GlobalScope.launch(Dispatchers.IO) {
                    MainActivity.database.AppDao().insertCustomer(
                        Cstmr(0,binding.cusNameEt.text.toString(),binding.cusEmailEt.text.toString(),
                            binding.cusMobileEt.text.toString(),binding.cusAddEt.text.toString()))

                    withContext(Dispatchers.Main){
                        Toasty.success(context,"Customer Added Successfully", Toast.LENGTH_SHORT, true).show()
                        dismiss()
                    }
                }
            } else {
                SharedPref.init(context)
                SharedPref.writeResInf(Cstmr(0,binding.cusNameEt.text.toString(),binding.cusEmailEt.text.toString(),
                    binding.cusMobileEt.text.toString(),binding.cusAddEt.text.toString()))
                Toasty.success(context,"Restaurant Information Saved Successfully",Toasty.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}