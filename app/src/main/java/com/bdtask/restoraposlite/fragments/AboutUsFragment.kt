package com.bdtask.restoraposlite.fragments

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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.MainActivity.Companion.drawerLayout
import com.bdtask.restoraposlite.databinding.FragmentAboutusBinding
import es.dmoral.toasty.Toasty
import java.lang.Exception

class AboutUsFragment : Fragment() {
    private var _binding: FragmentAboutusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutusBinding.inflate(inflater, container, false)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@restorapos.com")
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                requireActivity().startActivity(intent)
            } else {
                Toasty.info(
                    requireContext(), "No app found to send an Email", Toasty.LENGTH_SHORT, true
                ).show()
            }
        }


        binding.phone.setOnClickListener {
            try {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:+8801531987110")
                    requireActivity().startActivity(callIntent)
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 2
                    )
                }
            } catch (e: Exception) {
                Toasty.info(
                    requireContext(),
                    "No app found to perform a Phone Call",
                    Toasty.LENGTH_SHORT,
                    true
                ).show()
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://restorapos.com"))
            requireActivity().startActivity(intent)
        } catch (e: Exception) {
            Toasty.info(requireContext(), "No app found to open URL", Toasty.LENGTH_SHORT, true)
                .show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}