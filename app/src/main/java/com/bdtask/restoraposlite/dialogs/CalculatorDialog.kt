package com.bdtask.restoraposlite.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bdtask.restoraposlite.databinding.DialogCalculatorBinding
import java.util.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class CalculatorDialog(context: Context) : Dialog(context) {
    private var _binding: DialogCalculatorBinding? = null
    private val binding get() = _binding!!
    var workings = ""
    var formula = ""
    var tempFormula = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener { dismiss() }

        binding.equalsOnClick.setOnClickListener { equalsOnClick() }
        binding.decimalOnClick.setOnClickListener { decimalOnClick() }
        binding.zeroOnClick.setOnClickListener { zeroOnClick() }
        binding.plusOnClick.setOnClickListener { plusOnClick() }
        binding.threeOnClick.setOnClickListener { threeOnClick() }
        binding.twoOnClick.setOnClickListener { twoOnClick() }
        binding.oneOnClick.setOnClickListener { oneOnClick() }
        binding.minusOnClick.setOnClickListener { minusOnClick() }
        binding.sixOnClick.setOnClickListener { sixOnClick() }
        binding.fiveOnClick.setOnClickListener { fiveOnClick() }
        binding.fourOnClick.setOnClickListener { fourOnClick() }
        binding.timesOnClick.setOnClickListener { timesOnClick() }
        binding.nineOnClick.setOnClickListener { nineOnClick() }
        binding.eightOnClick.setOnClickListener { eightOnClick() }
        binding.sevenOnClick.setOnClickListener { sevenOnClick() }
        binding.divisionOnClick.setOnClickListener { divisionOnClick() }
        binding.powerOfOnClick.setOnClickListener { powerOfOnClick() }
        binding.bracketsOnClick.setOnClickListener { bracketsOnClick() }
        binding.clearOnClick.setOnClickListener { clearOnClick() }
    }

    private fun equalsOnClick() {
        var result: Double? = null
        val engine: ScriptEngine = ScriptEngineManager().getEngineByName("rhino")
        checkForPowerOf()
        try {
            result = engine.eval(formula) as Double?
        } catch (e: ScriptException) {

        }
        if (result != null) binding.resultsTV.text = result.toDouble().toString()
    }

    @JvmName("setWorkings1")
    private fun setWorkings(givenValue: String) {
        workings += givenValue
        binding.workingsTV.text = workings
    }

    private fun checkForPowerOf() {
        val indexOfPowers = ArrayList<Int>()
        for (i in 0 until workings.length) {
            if (workings[i] == '^') indexOfPowers.add(i)
        }
        formula = workings
        tempFormula = workings
        for (index in indexOfPowers) {
            changeFormula(index)
        }
        formula = tempFormula
    }

    private fun changeFormula(index: Int) {
        var numberLeft = ""
        var numberRight = ""
        for (i in index + 1 until workings.length) {
            numberRight = if (isNumeric(workings[i])) numberRight + workings[i] else break
        }
        for (i in index - 1 downTo 0) {
            numberLeft = if (isNumeric(workings[i])) numberLeft + workings[i] else break
        }
        val original = "$numberLeft^$numberRight"
        val changed = "Math.pow($numberLeft,$numberRight)"
        tempFormula = tempFormula.replace(original, changed)
    }

    private fun isNumeric(c: Char): Boolean {
        return c in '0'..'9' || c == '.'
    }

    private fun clearOnClick() {
        binding.workingsTV.text = ""
        workings = ""
        binding.resultsTV.text = ""
        leftBracket = true
    }

    var leftBracket = true

    private fun bracketsOnClick() {
        leftBracket = if (leftBracket) {
            setWorkings("(")
            false
        } else {
            setWorkings(")")
            true
        }
    }

    private fun powerOfOnClick() {
        setWorkings("^")
    }

    private fun divisionOnClick() {
        setWorkings("/")
    }

    private fun sevenOnClick() {
        setWorkings("7")
    }

    private fun eightOnClick() {
        setWorkings("8")
    }

    private fun nineOnClick() {
        setWorkings("9")
    }

    private fun timesOnClick() {
        setWorkings("*")
    }

    private fun fourOnClick() {
        setWorkings("4")
    }

    private fun fiveOnClick() {
        setWorkings("5")
    }

    private fun sixOnClick() {
        setWorkings("6")
    }

    private fun minusOnClick() {
        setWorkings("-")
    }

    private fun oneOnClick() {
        setWorkings("1")
    }

    private fun twoOnClick() {
        setWorkings("2")
    }

    private fun threeOnClick() {
        setWorkings("3")
    }

    private fun plusOnClick() {
        setWorkings("+")
    }

    private fun decimalOnClick() {
        setWorkings(".")
    }

    private fun zeroOnClick() {
        setWorkings("0")
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}