package com.example.calculator

class OperationExpression(override val expressionStr : String, override val operation : Operation? = null) : Expression(expressionStr, operation)
{
    override fun getCalcResult() : Double?
    {
        return null
    }
}