package com.example.calculator

class SimpleExpression(override val expressionStr : String/*, override var result : Double? = null*/) : Expression(expressionStr, null)
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
