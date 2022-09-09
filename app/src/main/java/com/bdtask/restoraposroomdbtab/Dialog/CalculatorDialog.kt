package com.bdtask.restoraposroomdbtab.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bdtask.restoraposroomdbtab.R
import java.util.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class CalculatorDialog(context: Context) : Dialog(context) {
    private lateinit var workingsTV: TextView
    private lateinit var resultsTV: TextView
    private lateinit var equalsOnClick: TextView
    private lateinit var decimalOnClick: TextView
    private lateinit var zeroOnClick: TextView
    private lateinit var plusOnClick: TextView
    private lateinit var threeOnClick: TextView
    private lateinit var twoOnClick: TextView
    private lateinit var oneOnClick: TextView
    private lateinit var minusOnClick: TextView
    private lateinit var sixOnClick: TextView
    private lateinit var fiveOnClick: TextView
    private lateinit var fourOnClick: TextView
    private lateinit var timesOnClick: TextView
    private lateinit var nineOnClick: TextView
    private lateinit var eightOnClick: TextView
    private lateinit var sevenOnClick: TextView
    private lateinit var divisionOnClick: TextView
    private lateinit var powerOfOnClick: TextView
    private lateinit var bracketsOnClick: TextView
    private lateinit var clearOnClick: TextView
    private lateinit var closeBtn: ImageView
    var workings = ""
    var formula = ""
    var tempFormula = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calculator)

        init()

        closeBtn = findViewById(R.id.closeBtn)

        equalsOnClick.setOnClickListener { equalsOnClickk() }
        decimalOnClick.setOnClickListener { decimalOnClick() }
        zeroOnClick.setOnClickListener { zeroOnClick() }
        plusOnClick.setOnClickListener { plusOnClick() }
        threeOnClick.setOnClickListener { threeOnClick() }
        twoOnClick.setOnClickListener { twoOnClick() }
        oneOnClick.setOnClickListener { oneOnClick() }
        minusOnClick.setOnClickListener { minusOnClick() }
        sixOnClick.setOnClickListener { sixOnClick() }
        fiveOnClick.setOnClickListener { fiveOnClick() }
        fourOnClick.setOnClickListener { fourOnClick() }
        timesOnClick.setOnClickListener { timesOnClick() }
        nineOnClick.setOnClickListener { nineOnClick() }
        eightOnClick.setOnClickListener { eightOnClick() }
        sevenOnClick.setOnClickListener { sevenOnClick() }
        divisionOnClick.setOnClickListener { divisionOnClick() }
        powerOfOnClick.setOnClickListener { powerOfOnClick() }
        bracketsOnClick.setOnClickListener { bracketsOnClick() }
        clearOnClick.setOnClickListener { clearOnClick() }
    }

    private fun init() {
        workingsTV = findViewById(R.id.workingsTextView)
        resultsTV = findViewById(R.id.resultTextView)
        equalsOnClick = findViewById(R.id.equalsOnClick)
        decimalOnClick = findViewById(R.id.decimalOnClick)
        zeroOnClick = findViewById(R.id.zeroOnClick)
        plusOnClick = findViewById(R.id.plusOnClick)
        threeOnClick = findViewById(R.id.threeOnClick)
        twoOnClick = findViewById(R.id.twoOnClick)
        oneOnClick = findViewById(R.id.oneOnClick)
        minusOnClick = findViewById(R.id.minusOnClick)
        sixOnClick = findViewById(R.id.sixOnClick)
        fiveOnClick = findViewById(R.id.fiveOnClick)
        fourOnClick = findViewById(R.id.fourOnClick)
        timesOnClick = findViewById(R.id.timesOnClick)
        nineOnClick = findViewById(R.id.nineOnClick)
        eightOnClick = findViewById(R.id.eightOnClick)
        sevenOnClick = findViewById(R.id.sevenOnClick)
        divisionOnClick = findViewById(R.id.divisionOnClick)
        powerOfOnClick = findViewById(R.id.powerOfOnClick)
        bracketsOnClick = findViewById(R.id.bracketsOnClick)
        clearOnClick = findViewById(R.id.clearOnClick)

    }

    private fun equalsOnClickk() {
        var result: Double? = null
        val engine: ScriptEngine = ScriptEngineManager().getEngineByName("rhino")
        checkForPowerOf()
        try {
            result = engine.eval(formula) as Double?
        } catch (e: ScriptException) {

        }
        if (result != null) resultsTV.text = result.toDouble().toString()
    }

    @JvmName("setWorkings1")
    private fun setWorkings(givenValue: String) {
        workings = workings + givenValue
        workingsTV.text = workings
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
        return if (c <= '9' && c >= '0' || c == '.') true else false
    }

    private fun clearOnClick() {
        workingsTV.text = ""
        workings = ""
        resultsTV.text = ""
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
}