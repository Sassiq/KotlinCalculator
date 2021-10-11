package com.example.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private var argExpression:String = "argExpression"
    private var argResult:String = "argResult"

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(argExpression, upper_text.text.toString())
        outState.putString(argResult, lower_text.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            upper_text.text = savedInstanceState.getString(argExpression, "")
            lower_text.text = savedInstanceState.getString(argResult, "")
        }

        num_zero_btn.setOnClickListener { setSymbol("0") }
        num_one_btn.setOnClickListener { setSymbol("1") }
        num_two_btn.setOnClickListener { setSymbol("2") }
        num_three_btn.setOnClickListener { setSymbol("3") }
        num_four_btn.setOnClickListener { setSymbol("4") }
        num_five_btn.setOnClickListener { setSymbol("5") }
        num_six_btn.setOnClickListener { setSymbol("6") }
        num_seven_btn.setOnClickListener { setSymbol("7") }
        num_eight_btn.setOnClickListener { setSymbol("8") }
        num_nine_btn.setOnClickListener { setSymbol("9") }
        plus_btn.setOnClickListener { setOperation("+") }
        minus_btn.setOnClickListener { setOperation("-") }
        divide_btn.setOnClickListener { setOperation("/") }
        multiply_btn.setOnClickListener { setOperation("*") }
        clear_btn.setOnClickListener { clearViews() }
        equals_btn.setOnClickListener {
            try {
                quickResult()
                upper_text.text = lower_text.text
                lower_text.text = ""
            }
            catch (e: Exception) {
                lower_text.text = getString(R.string.error_string)
            }
        }
        dot_btn.setOnClickListener { setOperation(".") }
        open_brackets_btn.setOnClickListener { setSymbol("(") }
        close_brackets_btn.setOnClickListener { setBrackets() }

    }

    private fun setBrackets(){
        val range = upper_text.text.indices
        val str: String = upper_text.text.toString()
        var open = 0
        var close = 0
        for (i in range) {
            if (str[i] == '(')
                open++
            if (str[i] == ')')
                close++
        }
        if (open > close)
            setSymbol(")")
    }

    private fun setSymbol(number: String) {
        upper_text.append(number)
        try {
            quickResult()
        }
        catch (e: Exception){

        }
    }

    private fun setOperation(number: String) {
        if (upper_text.text.isNotEmpty()) {
            val lastSymbol = upper_text.text.toString().last()
            if (lastSymbol != '-' && lastSymbol != '+' && lastSymbol != '/' && lastSymbol != '*'
                && lastSymbol != '.'
            ) {
                upper_text.append(number)
            }
        }
    }

    private fun clearViews(){
        upper_text.text = ""
        lower_text.text = ""
    }

    private fun quickResult() {
        val ex = ExpressionBuilder(upper_text.text.toString()).build()
        val res = ex.evaluate()
        val longResult = res.toLong()
        if (res == longResult.toDouble()) {
            lower_text.text = longResult.toString()
        }
        else {
            lower_text.text = res.toString()
        }
    }

}