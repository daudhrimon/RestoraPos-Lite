package com.bdtask.restoraposroomdbtab.Fragment

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
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val sharedPref = SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())

        if (ContextCompat.checkSelfPermission(requireContext().applicationContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermission.launch(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.splashFrag2homeFrag)
                }, 3000)
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.splashFrag2homeFrag)
            }, 3000)
        }

        if (sharedPref.readPayList() == null){
            val payList = arrayOf("Cash Payment","Card Payment")
            sharedPref.writePayList(payList.toMutableList())
        }

        if (sharedPref.readCurrency() == null){
            sharedPref.writeCurrency("$")
        } else {
            MainActivity.appCurrency = sharedPref.readCurrency() ?: "$"
        }

        return binding.root
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