package com.bdtask.restoraposlite.Fragment

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.MainActivity.Companion.appCurrency
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.FragmentSplashBinding
import es.dmoral.toasty.Toasty

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val sharedPref = SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())



        if (ContextCompat.checkSelfPermission(requireContext().applicationContext,
                Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                requestPermission.launch(Manifest.permission.BLUETOOTH_CONNECT)

            } else {
                navigateToHome()
            }
        } else {
            navigateToHome()
        }


        if (sharedPref.readPayList() == null){
            val payList = arrayOf("Cash Payment","Card Payment")
            sharedPref.writePayList(payList.toMutableList())
        }



        appCurrency = sharedPref.readCurrency() ?: "$"



        binding.signInBtn.setOnClickListener {
            Util.hideSoftKeyBoard(requireContext(),binding.root)

            if (binding.signInEt.text.toString().isEmpty()){
                Toasty.error(requireContext(),"Enter PIN first",Toasty.LENGTH_SHORT,true).show()
                return@setOnClickListener
            }

            if (binding.signInEt.text.toString().length < 5){
                Toasty.error(requireContext(),"PIN is too short",Toasty.LENGTH_SHORT,true).show()
                return@setOnClickListener
            }

            if ((sharedPref.readPIN() ?: "").isNotEmpty() &&
                sharedPref.readPIN() == binding.signInEt.text.toString()){

                Toasty.success(requireContext(),"Verified PIN Successfully",Toasty.LENGTH_SHORT,true).show()

                sharedPref.writeSignIn("OK")

                findNavController().navigate(R.id.splashFrag2homeFrag)

            } else {
                Toasty.error(requireContext(),"PIN Verification Failed, Please try again",Toasty.LENGTH_SHORT,true).show()
            }
        }



        binding.forgotPass.setOnClickListener {
            val pin = sharedPref.readPIN() ?: "     "
            val hint = pin.removeRange(0,2)
            Toasty.info(requireContext(),"Last 3 digit of your PIN is: $hint",Toasty.LENGTH_SHORT,true).show()
        }

        return binding.root
    }



    private fun navigateToHome(){

        Handler(Looper.getMainLooper()).postDelayed({

            if ((sharedPref.readWelcome() ?: 0) == 0){

                findNavController().navigate(R.id.splashFrag2onboardFrag)

            } else {

                if (sharedPref.readSignIn() == "OK") {
                    findNavController().navigate(R.id.splashFrag2homeFrag)
                } else {
                    binding.signInLay.visibility = View.VISIBLE
                }
            }

        }, 3000)
    }



    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            sharedPref.writeEMode(0)
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.splashFrag2homeFrag)
            }, 3000)
        } else {
            val alert = AlertDialog.Builder(context)
            alert.setView(layoutInflater.inflate(R.layout.dialog_progress,null))
            val dialog = alert.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                requireActivity().finish()
            }, 5000)
        }
    }
}