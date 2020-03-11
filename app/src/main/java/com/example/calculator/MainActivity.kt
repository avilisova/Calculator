package com.example.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clear(view : View) {
        val str = findViewById<TextView> (R.id.etExpression)
        str.text = null
        val resultText = findViewById<TextView> (R.id.tvResult)
        resultText.visibility = View.INVISIBLE
    }

    fun calc(view : View) {
        val expression = findViewById<TextView> (R.id.etExpression)
        val str = expression.text.toString()
        //var str : String = "-5.1 + ((1 + 4) - (2 * 3))"
        val resultText = findViewById<TextView> (R.id.tvResult)

        if (str != null && checkInputString(str)) {
            val preparedStr = this.prepareInputString(str)
             try {
                 val e = CalcExpression(preparedStr)
                 var calcResult = e.getCalcResult()
                 if (calcResult!= null) {
                     resultText.text = calcResult.toString()
                 }
             }
             catch (e : IllegalArgumentException)
             {
                 resultText.text = e.message
             }
            resultText.visibility = View.VISIBLE
        }
        else
            resultText.text ="Некорректное выражение!"
    }

    private fun prepareInputString( str : String) : String {
        var formatStr = str.replace(" ","")
        formatStr =  formatStr.replace(",",".")
        formatStr =  formatStr.replace("(-","(0-")
        if ( formatStr.startsWith("-"))
        {
            formatStr = "0$formatStr"
        }
        return  formatStr
    }
    private fun checkInputString( str : String) : Boolean {
        if ( str.matches( Regex("""[\d \(\)\+\-\*\/,.]+""")))
        {
            return true
        }
        return false
    }
}

