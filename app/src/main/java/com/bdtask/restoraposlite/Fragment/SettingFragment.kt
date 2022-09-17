package com.bdtask.restoraposlite.Fragment

import android.app.Activity
import android.os.Bundle
import android.text.BidiFormatter
import android.text.TextDirectionHeuristics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bdtask.restoraposlite.MainActivity
import com.bdtask.restoraposlite.Room.Entity.Cstmr
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.Util.Util
import com.bdtask.restoraposlite.databinding.FragmentSettingBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.mynameismidori.currencypicker.CurrencyPicker
import es.dmoral.toasty.Toasty
import java.util.*
import com.bdtask.restoraposlite.R

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val sharedPref = SharedPref
    private var welcome = 0
    private var posLogo = ""
    private var pos = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        sharedPref.init(requireContext())
        welcome = sharedPref.readWelcome() ?: 0


        if (welcome == 0) {
            binding.back.visibility = View.INVISIBLE
            binding.resInfoLay.visibility = View.VISIBLE
        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        //Settings OnClicks

        binding.resInfoCard.setOnClickListener {
            getVisibilityAndGone(
                binding.resInfoLay,
                binding.currencyLay,
                binding.posLogoLay,
                binding.vatLay,
                binding.crgLay,
                binding.paymentLay,
                binding.operatorLay
            )
            var resInf: Cstmr? = null
            resInf = sharedPref.readResInf()

            if (welcome == 1 && resInf != null){
                binding.resNameEt.setText(resInf.nm)
                binding.resEmailEt.setText(resInf.eml)
                binding.resMobileEt.setText(resInf.mbl)
                binding.resAddEt.setText(resInf.adrs)
            }
        }


        binding.currencyCard.setOnClickListener {
            getVisibilityAndGone(
                binding.currencyLay,
                binding.resInfoLay,
                binding.posLogoLay,
                binding.vatLay,
                binding.crgLay,
                binding.paymentLay,
                binding.operatorLay
            )
            if (welcome == 1 && (sharedPref.readCurrency() ?: "").isNotEmpty()){
                binding.currencyTv.text = sharedPref.readCurrency()
            }
        }


        binding.posLogoCard.setOnClickListener {
            getVisibilityAndGone(
                binding.posLogoLay,
                binding.resInfoLay,
                binding.currencyLay,
                binding.vatLay,
                binding.crgLay,
                binding.paymentLay,
                binding.operatorLay
            )
            if (welcome == 1 && (sharedPref.readPosLogo() ?: "").isNotEmpty()){
                binding.addPosLogoBtn.setImageURI(sharedPref.readPosLogo()?.toUri())
            }
        }


        binding.vatCard.setOnClickListener {
            getVisibilityAndGone(
                binding.vatLay,
                binding.resInfoLay,
                binding.currencyLay,
                binding.posLogoLay,
                binding.crgLay,
                binding.paymentLay,
                binding.operatorLay
            )
            if (welcome == 1){
                binding.vatEt.setText((sharedPref.readVat() ?: 0.0).toString())
            }
        }


        binding.crgCard.setOnClickListener {
            getVisibilityAndGone(
                binding.crgLay,
                binding.resInfoLay,
                binding.currencyLay,
                binding.posLogoLay,
                binding.vatLay,
                binding.paymentLay,
                binding.operatorLay
            )
            if (welcome == 1){
                binding.crgEt.setText((sharedPref.readCharge() ?: 0.0).toString())
            }
        }


        binding.paymentCard.setOnClickListener {
            getVisibilityAndGone(
                binding.paymentLay,
                binding.resInfoLay,
                binding.currencyLay,
                binding.posLogoLay,
                binding.vatLay,
                binding.crgLay,
                binding.operatorLay
            )
        }


        binding.operatorCard.setOnClickListener {
            getVisibilityAndGone(
                binding.operatorLay,
                binding.resInfoLay,
                binding.currencyLay,
                binding.posLogoLay,
                binding.vatLay,
                binding.crgLay,
                binding.paymentLay
            )
            if (welcome == 1 && (sharedPref.readOperator() ?: "").isNotEmpty()){
                binding.operatorEt.setText(sharedPref.readOperator())
            }
        }

        //set Buttons Click

        binding.setResInfoBtn.setOnClickListener {
            setResInfoClickHandler()
            hideKeyboard()
        }


        binding.setCurrency.setOnClickListener {
            setCurrencyClickHandler()
            hideKeyboard()
        }


        binding.setPosLogoBtn.setOnClickListener {
            setPosLogoClickHandler()
            hideKeyboard()
        }


        binding.setVatBtn.setOnClickListener {
            setVatClickHAndler()
            hideKeyboard()
        }


        binding.setCrgBtn.setOnClickListener {
            setServiceChargeHandler()
            hideKeyboard()
        }

        binding.addPaymentBtn.setOnClickListener {
            addPaymentClickHandler()
            hideKeyboard()
        }

        binding.setOperatorBtn.setOnClickListener {
            setOperatorClickHandler()
            hideKeyboard()
        }


        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sPos: Int, p3: Long) {
                pos = sPos
                when (sPos){
                    0 -> {
                        binding.terminal.visibility = View.GONE
                        binding.name.hint = " Enter Payment Name"
                    }
                    1 -> {
                        binding.terminal.visibility = View.VISIBLE
                        binding.name.hint = " Enter Bank Name"
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {/**/}
        }


        // Currency Picker
        binding.addCurrency.setOnClickListener {
            val picker = CurrencyPicker.newInstance("Select Currency") // dialog title
            picker.setListener { name, code, symbol, flagDrawableResID ->
                var mixedLanguageText = symbol
                if(BidiFormatter.getInstance().isRtl(symbol)){
                    val rtlLocale = Locale.ENGLISH
                    mixedLanguageText = BidiFormatter.getInstance(rtlLocale).unicodeWrap(mixedLanguageText, TextDirectionHeuristics.LTR);
                }
                binding.currencyTv.text = mixedLanguageText
                binding.addCurrency.setImageResource(flagDrawableResID)
                picker.dismiss()
            }
            picker.show(parentFragmentManager, "CURRENCY_PICKER")
        }



        //Image Picker
        binding.addPosLogoBtn.setOnClickListener {
            ImagePicker.with(this)
                .crop(5f,2f)
                .compress(48)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(167, 65)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForPosLogoResult.launch(intent)
                }
        }



        // payment Type Spinner Adapter Set
        val payType = arrayOf("Simple Payment","Card Payment")
        binding.typeSpinner.adapter = ArrayAdapter(requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,payType)



        return binding.root
    }

    private fun setOperatorClickHandler() {
        if (binding.operatorEt.text.toString().isEmpty()){
            binding.operatorEt.error = "Empty Service Charge Can't Save"
            binding.operatorEt.requestFocus()
            return
        }

        sharedPref.writeOperator(binding.operatorEt.text.toString())
        Toasty.success(requireContext(),"Assign ${binding.operatorEt.text.toString()} as Current Operator Successfully",Toasty.LENGTH_SHORT,true).show()

        if (welcome == 0) {
            binding.operatorLay.visibility = View.GONE
            findNavController().navigate(R.id.settingFrag2homeFrag)
        }
    }



    private fun addPaymentClickHandler() {

        if (pos == 0 && binding.name.text.toString().isEmpty()) {
            showPaymentToasty(0,"Empty Value Forbidden")
            return
        }

        if (pos == 1 && binding.terminal.text.toString().isEmpty() && binding.name.text.toString().isEmpty()) {
            showPaymentToasty(0,"Empty Value Forbidden")
            return
        }

        when (pos){
            0 -> {
                var payList = mutableListOf<String>()
                if (sharedPref.readPayList() != null) {
                    payList = sharedPref.readPayList()!!
                    if (!Gson().toJson(payList).contains(Gson().toJson(binding.name.text.toString()))){
                        payList.add(binding.name.text.toString())
                        sharedPref.writePayList(payList)
                        showPaymentToasty(1,"Added New Payment Successfully")
                    } else {
                        showPaymentToasty(0,"This Payment is Already In")
                    }
                } else {
                    payList.add(binding.name.text.toString())
                    sharedPref.writePayList(payList)
                    showPaymentToasty(1,"Added New Payment Successfully")
                }

                binding.name.setText("")
                binding.name.hint = " Enter Payment Name"

            }

            1 -> {
                if (binding.terminal.text.toString().isNotEmpty()) {
                    var terList = mutableListOf<String>()
                    if (sharedPref.readTerminalList() != null){
                        terList.clear()
                        terList = sharedPref.readTerminalList()!!
                        if (!Gson().toJson(terList).contains(Gson().toJson(binding.terminal.text.toString()))){
                            terList.add(binding.terminal.text.toString())
                            sharedPref.writeTerminalList(terList)
                            showPaymentToasty(1,"Added New Terminal Successfully")
                        } else {
                            showPaymentToasty(0,"This Terminal is Already In")
                        }
                    } else {
                        terList.add(binding.terminal.text.toString())
                        sharedPref.writeTerminalList(terList)
                        showPaymentToasty(1,"Added New Terminal Successfully")
                    }
                }

                if (binding.name.text.toString().isNotEmpty()){
                    if (sharedPref.readBankList() != null){
                        var bankList = mutableListOf<String>()
                        bankList = sharedPref.readBankList()!!
                        if (!Gson().toJson(bankList).contains(Gson().toJson(binding.name.text.toString()))){
                            bankList.add(binding.name.text.toString())
                            sharedPref.writeBankList(bankList)
                            showPaymentToasty(1,"Added New Bank Successfully")
                        } else {
                            showPaymentToasty(0,"This Bank is Already In")
                        }
                    } else {
                        val bankList = mutableListOf<String>()
                        bankList.add(binding.name.text.toString())
                        sharedPref.writeBankList(bankList)
                        showPaymentToasty(1,"Added New Bank Successfully")
                    }
                }

                binding.terminal.setText("")
                binding.name.setText("")
                binding.terminal.hint = " Enter Card Terminal"
                binding.name.hint = " Enter Bank Name"

            }
        }
    }




    private fun setServiceChargeHandler() {
        if (binding.crgEt.text.toString().isEmpty()){
            binding.crgEt.error = "Empty Service Charge Can't Save"
            binding.crgEt.requestFocus()
            return
        }

        sharedPref.writeCharge(binding.crgEt.text.toString())
        Toasty.success(requireContext(),"${sharedPref.readCharge()!!} % Set as Global Service Charge",Toasty.LENGTH_SHORT,true).show()

        ifWelcomeModeHandler(binding.crgLay,binding.paymentLay)
    }




    private fun setVatClickHAndler() {
        if (binding.vatEt.text.toString().isEmpty()){
            binding.vatEt.error = "Empty Vat Can't Save"
            binding.vatEt.requestFocus()
            return
        }

        sharedPref.writeVat(binding.vatEt.text.toString())
        Toasty.success(requireContext(),"${sharedPref.readVat()!!} % Set as Global Vat/Tax",Toasty.LENGTH_SHORT,true).show()

        ifWelcomeModeHandler(binding.vatLay,binding.crgLay)
    }




    private fun setPosLogoClickHandler() {
        if (posLogo.isEmpty()){
            Toasty.error(requireContext(),"Please Add a POS Logo", Toasty.LENGTH_SHORT,true).show()
        } else {
            SharedPref.init(requireContext())
            SharedPref.writePosLogo(posLogo)
            Toasty.success(requireContext(),"Added POS Logo Successfully",Toasty.LENGTH_SHORT,true).show()

            ifWelcomeModeHandler(binding.posLogoLay,binding.vatLay)
        }
    }





    private fun setCurrencyClickHandler() {

        if (binding.currencyTv.text.toString().isEmpty()){

            Toasty.error(requireContext(),"Select a Currency",Toasty.LENGTH_SHORT,true).show()

        } else {
            sharedPref.writeCurrency(binding.currencyTv.text.toString().trim())
            MainActivity.appCurrency = binding.currencyTv.text.toString().trim()
            Toasty.success(requireContext(),"Now ${sharedPref.readCurrency()!!} is your Global Currency",Toasty.LENGTH_SHORT,true).show()

            ifWelcomeModeHandler(binding.currencyLay,binding.posLogoLay)
        }
    }





    private fun setResInfoClickHandler() {
        if (binding.resNameEt.text.toString().isEmpty()){
            binding.resNameEt.error = "Name is Empty"
            binding.resNameEt.requestFocus()
            return
        }
        if (binding.resEmailEt.text.toString().isEmpty()){
            binding.resEmailEt.error = "Mobile is Empty"
            binding.resEmailEt.requestFocus()
            return
        }
        if (binding.resMobileEt.text.toString().isEmpty()){
            binding.resMobileEt.error = "Mobile is Empty"
            binding.resMobileEt.requestFocus()
            return
        }
        if (binding.resAddEt.text.toString().isEmpty()){
            binding.resAddEt.error = "Address is Empty"
            binding.resAddEt.requestFocus()
            return
        }

        SharedPref.init(requireContext())
        SharedPref.writeResInf(
            Cstmr(0,binding.resNameEt.text.toString(),binding.resEmailEt.text.toString(),
            binding.resMobileEt.text.toString(),binding.resAddEt.text.toString())
        )
        Toasty.success(requireContext(),"Restaurant Information Saved Successfully", Toasty.LENGTH_SHORT,true).show()

        ifWelcomeModeHandler(binding.resInfoLay,binding.currencyLay)
    }




    // this will check welcome mode and will show settings step by step
    private fun ifWelcomeModeHandler(currentLay: CardView, upComingLay: CardView){
        if (welcome == 0){
            currentLay.visibility = View.GONE
            upComingLay.visibility = View.VISIBLE
        }
        hideKeyboard()
    }




    private fun getVisibilityAndGone(
        currentLay: CardView,
        goneLay1: CardView,
        goneLay2: CardView,
        goneLay3: CardView,
        goneLay4: CardView,
        goneLay5: CardView,
        goneLay6: CardView
    ) {
        if (welcome != 0){
            when {
                currentLay.isVisible -> currentLay.visibility = View.GONE
                else -> {
                    goneLay1.visibility = View.GONE
                    goneLay2.visibility = View.GONE
                    goneLay3.visibility = View.GONE
                    goneLay4.visibility = View.GONE
                    goneLay5.visibility = View.GONE
                    goneLay6.visibility = View.GONE
                    currentLay.visibility = View.VISIBLE
                }
            }
        }
        hideKeyboard()
    }




    //Show Payment Toast
    private fun showPaymentToasty (status: Int, toast: String) {
        if (status == 1){
            Toasty.success(requireContext(),toast, Toasty.LENGTH_SHORT).show()
        } else {
            Toasty.info(requireContext(),toast, Toasty.LENGTH_SHORT).show()
        }
        ifWelcomeModeHandler(binding.paymentLay,binding.operatorLay)
    }



    //hide Keyboard
    private fun hideKeyboard() {
        Util.hideSoftKeyBoard(requireContext(),binding.root)
    }


    // image picker RESULT
    private val startForPosLogoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    posLogo = fileUri.toString()
                    binding.addPosLogoBtn.setImageURI(fileUri)
                }

                ImagePicker.RESULT_ERROR -> {
                    Toasty.error(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT, true).show()
                }

                else -> {
                    Toasty.error(requireContext(),"Cancelled", Toast.LENGTH_SHORT, true).show()
                }
            }
        }
}