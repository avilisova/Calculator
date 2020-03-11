package com.example.calculator

class SimpleExpression(private val expressionStr : String, override var result : Double? = null) : CalcExpression(expressionStr, result)
{
    override fun getCalcResult() : Double?
    {
        if (this.result == null)
        {
            this.result = this.expressionStr.toDouble()
        }
        return this.result
    }
}
