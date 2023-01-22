package com.bdtask.restoraposlite.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.bdtask.restoraposlite.room.AppDatabase
import com.bdtask.restoraposlite.room.entity.Customer
import com.bdtask.restoraposlite.utils.Util
import com.bdtask.restoraposlite.databinding.DialogAddNewCustomerBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class AddCustomerDialog(context: Context, private val lifecycleScope: LifecycleCoroutineScope) :
    Dialog(context) {
    private var _binding: DialogAddNewCustomerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogAddNewCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.crossBtn.setOnClickListener {
            dismiss()
        }

        binding.root.setOnClickListener {
            Util.hideSoftKeyBoard(context, binding.root)
        }

        binding.addCview.setOnClickListener {
            Util.hideSoftKeyBoard(context, binding.addCview)
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding.submitBtn.setOnClickListener {
            if (binding.cusNameEt.text.toString().isEmpty()) {
                binding.cusNameEt.setError("Customer's Name is empty")
                binding.cusNameEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusMobileEt.text.toString().isEmpty()) {
                binding.cusMobileEt.setError("Mobile no is empty")
                binding.cusMobileEt.requestFocus()
                return@setOnClickListener
            }
            if (binding.cusAddEt.text.toString().isEmpty()) {
                binding.cusAddEt.setError("Address is empty")
                binding.cusAddEt.requestFocus()
                return@setOnClickListener
            }


            lifecycleScope.launch(Dispatchers.IO) {
                AppDatabase.getDatabaseInstance(context.applicationContext).AppDao().insertCustomer(
                    Customer(
                        0,
                        binding.cusNameEt.text.toString(),
                        binding.cusEmailEt.text.toString(),
                        binding.cusMobileEt.text.toString(),
                        binding.cusAddEt.text.toString()
                    )
                )

                withContext(Dispatchers.Main) {
                    Toasty.success(context, "Customer Added Successfully", Toast.LENGTH_SHORT, true)
                        .show()
                    dismiss()
                }
            }
        }
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}