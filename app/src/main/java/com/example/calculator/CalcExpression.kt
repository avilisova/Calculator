package com.example.calculator

open class CalcExpression (private val expressionStr : String, open var result : Double? = null) {
    protected val operations: Array<Operation> =
        arrayOf(MultiplyOperation(), DivideOperation(), PlusOperation(), MinusOperation())
    private val operation : Operation? = this.chooseOperation(expressionStr)
    //private var result : Double? = null

    private fun transformToList(): /*ArrayList<String> {*/ ArrayList<CalcExpression> {
        val str = this.expressionStr
        var items = ArrayList<CalcExpression>()
        //var items = ArrayList<String>()

        var startPos = 0
        var endPos = 0
        while (endPos < str.length) {
            val strSymbol: String = str[endPos].toString()
            when {
                strSymbol == "(" -> {
                    val indexOfClosingBracket = this.indexOfClosingBracket(str, endPos)
                    val substr = str.substring(endPos + 1, indexOfClosingBracket)
                    endPos = indexOfClosingBracket + 1
                    startPos = endPos
                    items.add(CalcExpression(substr))
                    //items.add(substr)
                }
                this.operations.any {e -> e.strOperation == strSymbol} -> {
                    val substr = str.substring(startPos, endPos)
                    if (substr != "") {
                        var s = SimpleExpression(substr, substr.toDouble())
                        items.add(s)
                        //items.add(SimpleExpression(substr, substr.toDouble()))
                        //items.add(substr)
                    }
                    items.add(OperationExpression(strSymbol))
                    //items.add(strSymbol)
                    startPos = endPos + 1
                    endPos = startPos
                }
                else -> endPos++
            }
        }

        if (startPos < str.length) {
            val substr = str.substring(startPos, str.length)
            items.add(SimpleExpression(substr, substr.toDouble()))
            //items.add(substr)
        }
        return items
    }

    private fun calc() {
       var items = this.transformToList()

       var pos: Int
        for (priority in 0..1) {
            pos = 0
            while (pos != -1) {
                pos = items.indexOfFirst { e -> (e.operation != null && e.operation.priority == priority)}
                if (pos != -1) {
                    val operand1 = items[pos - 1].getCalcResult()
                    val operand2 = items[pos + 1].getCalcResult()


                    val oper = items[pos].operation
                    if (oper != null) {
                        var newElement = items[pos].operation?.executeOperation(
                            operand1,
                            operand2
                        )

                        items[pos - 1] = SimpleExpression(newElement.toString(), newElement)
                        items.removeAt(pos)
                        items.removeAt(pos)
                    }
                }
            }
        }

        this.result = items[0].getCalcResult()
        //this.result = 0.0
    }


    open fun getCalcResult(): Double? {
        if (this.result == null) {
            this.calc()
            if (this.result == null) {
                //println("Не удалось вычислить выражение " + this.expressionStr)
                throw java.lang.IllegalArgumentException("Не удалось вычислить выражение " + this.expressionStr)
            }
        }
        return this.result
    }

    override fun toString(): String {
        return this.expressionStr
    }

    private fun indexOfClosingBracket(str : String, pos : Int) : Int {
        var cnt = 0
        var bracketPos = 0
        var i = str.indexOf("(", pos)
        if (i > str.indexOf(")", pos)){
            throw IllegalArgumentException("Некорректная расстановка скобок - нет открывающей: $str")

        }
        while (bracketPos == 0) {
            if (str[i] == '(') {
                cnt++
            }
            else if (str[i] == ')') {
                if (cnt == 0) {
                    throw IllegalArgumentException("Некорректная расстановка скобок - нет открывающей: $str")
                }
                cnt--
            }

            if (cnt == 0) {
                bracketPos = i
            }
            i++
        }
        if (cnt > 0) {
            throw IllegalArgumentException("Некорректная расстановка скобок - нет закрывающей: $str")
        }
        return bracketPos
    }

    open fun chooseOperation(str : String) : Operation? {
        return null
    }
}

