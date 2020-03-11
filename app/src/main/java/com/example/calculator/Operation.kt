package com.example.calculator

abstract class Operation (val strOperation : String, val priority : Int)
{
    open fun executeOperation(x : Double?, y : Double?) : Double?
    {
        return null
    }
}

class PlusOperation : Operation ("+", 1)
{
    override fun executeOperation(x : Double?, y : Double?) : Double?
    {
        if (x != null && y != null) {
            return x + y
        }
        return null
    }
}

class MinusOperation : Operation ("-", 1)
{
    override fun executeOperation(x : Double?, y : Double?): Double?
    {
        if (x != null && y != null) {
            return x - y
        }
        return null
    }
}

class MultiplyOperation : Operation ("*", 0)
{
    override fun executeOperation(x : Double?, y : Double?) : Double?
    {
        if (x != null && y != null) {
            return x * y
        }
        return null
    }
}

class DivideOperation : Operation ("/", 0)
{
    override fun executeOperation(x : Double?, y : Double?) : Double?
    {
        if (x != null && y != null) {
            try {
                return x / y
            }
            catch (e : ArithmeticException)
            {
                throw IllegalArgumentException("Попытка деления на 0: $x / $y")
            }
        }
        return null
    }

}