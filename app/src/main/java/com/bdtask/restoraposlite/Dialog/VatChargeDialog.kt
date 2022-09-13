package com.bdtask.restoraposlite.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.BidiFormatter
import android.text.InputType
import android.text.TextDirectionHeuristics
import android.view.View
import androidx.fragment.app.FragmentManager
import com.bdtask.restoraposlite.Fragment.MainFragment.Companion.mBinding
import com.bdtask.restoraposlite.MainActivity
import com.bdtask.restoraposlite.R
import com.bdtask.restoraposlite.Util.SharedPref
import com.bdtask.restoraposlite.databinding.DialogSingleItemetBinding
import com.mynameismidori.currencypicker.CurrencyPicker
import es.dmoral.toasty.Toasty
import java.util.*

class VatChargeDialog( context: Context,
                       private val status: Int,
                       private val header: String,
                       private val hint: String,
                       private val supportFragmentManager: FragmentManager ): Dialog(context) {

    private lateinit var binding: DialogSingleItemetBinding
    private val sharedPref = SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        super.onCreate(savedInstanceState)
        binding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        setContentView(binding.root)
        binding.itemTv.text = header
        binding.itemEt.hint = hint

        when(status){
            0 -> {
                val vat = sharedPref.readVat() ?: 0.0
                if (vat.toString().isNotEmpty()) {
                    binding.itemEt.setText(vat.toString())
                }
            }
            1 -> {
               val crg = sharedPref.readCharge() ?: 0.0
                if (crg.toString().isNotEmpty()) {
                    binding.itemEt.setText(crg.toString())
                }
            }
            2 -> {
                binding.itemEt.isFocusable = false
                binding.addCurrency.visibility = View.VISIBLE
                binding.itemBtn.text = "Set"

                val currency = sharedPref.readCurrency() ?: ""
                if (currency.isNotEmpty()) {
                    binding.itemEt.setText(currency)
                }
            }
            3 -> {
                binding.itemEt.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                val operator = sharedPref.readOperator() ?: ""
                if (operator.isNotEmpty()) {
                    binding.itemEt.setText(operator)
                }
            }
        }

        binding.itemCross.setOnClickListener {
            dismiss()
        }

        binding.itemBtn.setOnClickListener {
            if (binding.itemEt.text.toString().isEmpty()){
                if (status == 2){
                    binding.itemEt.error = "Please Select A Currency First"
                } else {
                    binding.itemEt.error = "Empty Value Can't Save"
                }
                binding.itemEt.requestFocus()
                return@setOnClickListener
            }

            when(status){
                0 -> {
                    sharedPref.writeVat(binding.itemEt.text.toString())
                    Toasty.success(context,"${sharedPref.readVat()!!} % Set as Global Vat",Toasty.LENGTH_SHORT,true).show()
                    dismiss()
                }

                1 -> {
                    sharedPref.writeCharge(binding.itemEt.text.toString())
                    Toasty.success(context,"${sharedPref.readCharge()!!} % Set as Global Service Charge",Toasty.LENGTH_SHORT,true).show()
                    dismiss()
                }

                2 -> {
                    sharedPref.writeCurrency(binding.itemEt.text.toString().trim())
                    MainActivity.appCurrency = binding.itemEt.text.toString().trim()
                    Toasty.success(context,"Now ${sharedPref.readCurrency()!!} is your Global Currency",Toasty.LENGTH_SHORT,true).show()
                    mBinding.grandTotalTv.text = " : ${sharedPref.readCurrency()!!}"
                    dismiss()
                }

                3 -> {
                    sharedPref.writeOperator(binding.itemEt.text.toString())
                    Toasty.success(context,"Assign ${binding.itemEt.text.toString()} as Current Operator Successfully",Toasty.LENGTH_SHORT,true).show()
                    dismiss()
                }
            }
        }

        binding.addCurrency.setOnClickListener {
            val picker = CurrencyPicker.newInstance("Select Currency") // dialog title
            picker.setListener { name, code, symbol, flagDrawableResID ->

                var mixedLanguageText = symbol

                if(BidiFormatter.getInstance().isRtl(symbol)){
                    val rtlLocale = Locale.ENGLISH
                    mixedLanguageText = BidiFormatter.getInstance(rtlLocale).unicodeWrap(mixedLanguageText, TextDirectionHeuristics.LTR);
                }
                binding.itemEt.setText(mixedLanguageText)
                binding.addCurrency.setImageResource(flagDrawableResID)
                picker.dismiss()
            }
            picker.show(supportFragmentManager, "CURRENCY_PICKER")
        }
    }
}