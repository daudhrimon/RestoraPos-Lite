package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.BidiFormatter
import android.text.InputType
import android.text.TextDirectionHeuristics
import android.view.View
import androidx.fragment.app.FragmentManager
import com.bdtask.restoraposroomdbtab.MainActivity
import com.bdtask.restoraposroomdbtab.R
import com.bdtask.restoraposroomdbtab.Util.SharedPref
import com.bdtask.restoraposroomdbtab.databinding.DialogSingleItemetBinding
import com.mynameismidori.currencypicker.CurrencyPicker
import es.dmoral.toasty.Toasty
import java.util.*

class VatChargeDialog(context: Context,
                      private val status: Int,
                      private val header: String,
                      private val hint: String,
                      private val supportFragmentManager: FragmentManager): Dialog(context) {

    private lateinit var binding: DialogSingleItemetBinding
    private val sharedPref = SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref.init(context)
        super.onCreate(savedInstanceState)
        binding = DialogSingleItemetBinding.bind(layoutInflater.inflate(R.layout.dialog_single_itemet,null))
        setContentView(binding.root)
        binding.itemTv.text = header
        binding.itemEt.hint = hint

        if (status == 2) {
            binding.itemEt.isFocusable = false
            binding.addCurrency.visibility = View.VISIBLE
            binding.itemBtn.text = "Set"
        }

        if (status == 3){
            binding.itemEt.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
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