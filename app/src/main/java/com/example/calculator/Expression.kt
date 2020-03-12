package com.example.calculator

abstract class Expression (open val expressionStr : String/*, open var result : Double? = null*/, open val operation : Operation? = null) {
    var result : Double? = null

    open fun getCalcResult(): Double? {
        if (this.result == null) {

            throw java.lang.IllegalArgumentException("Не удалось вычислить выражение " + this.expressionStr)
        }
        return this.result
    }

    override fun toString(): String {
        return this.expressionStr
    }

}