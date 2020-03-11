package com.example.calculator

class OperationExpression(private val expressionStr : String) : CalcExpression(expressionStr)
{
    override fun getCalcResult() : Double?
    {
        return null
    }

    override fun chooseOperation(str : String) : Operation? {
        return this.operations.getOrNull( this.operations.indexOfFirst{e -> e.strOperation == str})
    }
}