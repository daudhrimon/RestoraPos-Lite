package com.bdtask.restoraposlite.Fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.databinding.FragmentAboutusBinding
import es.dmoral.toasty.Toasty
import java.lang.Exception


class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutusBinding.inflate(inflater, container, false)



        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@restorapos.com")
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                requireActivity().startActivity(intent)
            } else {
                Toasty.info(requireContext(),"No Email Sender App found in your device",Toasty.LENGTH_SHORT).show()
            }
        }



        binding.phone.setOnClickListener {
            try {
                if (ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:+8801531987110")
                    requireActivity().startActivity(callIntent)
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 2)
                }
            } catch (e: Exception){
                Toasty.info(requireContext(),"No Phone App found in your device",Toasty.LENGTH_SHORT).show()
            }

        }



        binding.webSite.setOnClickListener {
            loadWebsite()
        }


        binding.logo.setOnClickListener {
            loadWebsite()
        }

        return binding.root
    }

    private fun loadWebsite() {
        try {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse("http://restorapos.com"))
            requireActivity().startActivity(intent)
        } catch (e: Exception){
            Toasty.info(requireContext(),"No Browser App found in your device",Toasty.LENGTH_SHORT).show()
        }
    }
}