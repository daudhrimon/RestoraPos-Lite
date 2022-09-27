package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.DialogPasswordBinding
import es.dmoral.toasty.Toasty

class PasswordDialog(context: Context,
                     private val parentFragment: Fragment?
                     ): Dialog(context) {

    private lateinit var binding: DialogPasswordBinding
    private val sharedPref = SharedPref
    private var welcome = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPasswordBinding.bind(layoutInflater.inflate(R.layout.dialog_password,null))
        setContentView(binding.root)

        sharedPref.init(context)
        welcome = sharedPref.readWelcome() ?: 0

        if (welcome == 0) {
            binding.closeBtn.visibility = View.INVISIBLE
            binding.currentLay.visibility = View.GONE
            binding.changePinBtn.text = "Set PIN"
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }


        binding.changePinBtn.setOnClickListener {
            if (welcome != 0){
                if (binding.currentPinEt.text.toString().trim().isEmpty()){
                    binding.currentPinEt.error = "Enter Current Pin"
                    binding.currentPinEt.requestFocus()
                    return@setOnClickListener
                }
                if (binding.currentPinEt.text.toString().trim() != (sharedPref.readPIN() ?: "")){
                    binding.currentPinEt.error = "Current Pin is Wrong"
                    binding.currentPinEt.requestFocus()
                    return@setOnClickListener
                }

                checkNewPins()

            } else {
                checkNewPins()
            }

        }

    }

    private fun checkNewPins() {
        if (binding.newPin1Et.text.toString().trim().length < 5){
            binding.newPin1Et.error = "Pin must be in 5 character"
            binding.newPin1Et.requestFocus()
            return
        }
        if (binding.newPin2Et.text.toString().trim().length < 5){
            binding.newPin2Et.error = "Pin must be in 5 character"
            binding.newPin2Et.requestFocus()
            return
        }
        if (binding.newPin1Et.text.toString().trim() != binding.newPin2Et.text.toString().trim()) {
            binding.newPin2Et.error = "Confirm Pin is Not Matching With new Pin"
            binding.newPin2Et.requestFocus()
            return
        }
        sharedPref.writePIN(binding.newPin1Et.text.toString().trim())
        val toast = if (welcome==0) {
            "Pin Set Successfully"
            }else{
            "Password Changed Successfully"
        }
        Toasty.success(context,toast,Toasty.LENGTH_SHORT,true).show()
        /*if (welcome==0){
            sharedPref.writeWelcome(1)
        }*/
        dismiss()
        parentFragment?.findNavController()?.navigate(R.id.settingFrag2homeFrag)
    }

}