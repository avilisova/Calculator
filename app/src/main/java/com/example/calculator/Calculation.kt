package com.example.calculator


object Calculation {
    private val operations: Array<Operation> =
        arrayOf(MultiplyOperation(), DivideOperation(), PlusOperation(), MinusOperation())

    private fun transformToList(str : String): ArrayList<Expression> {
        var items = ArrayList<Expression>()

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
                    items.add(SimpleExpression(this.calc(substr)))
                }
                this.operations.any {e -> e.strOperation == strSymbol} -> {
                    val substr = str.substring(startPos, endPos)
                    if (substr != "") {
                        items.add(SimpleExpression(substr))
                    }
                    items.add(OperationExpression(strSymbol, chooseOperation(strSymbol)))
                    startPos = endPos + 1
                    endPos = startPos
                }
                strSymbol == ")" -> {
                    throw IllegalArgumentException("Некорректная расстановка скобок - нет открывающей: $str")
                }
                else -> endPos++
            }
        }
        if (startPos < str.length) {
            val substr = str.substring(startPos, str.length)
            items.add(SimpleExpression(substr))
        }
        return items
    }

    fun calc(str : String) : String {
        var items = this.transformToList(str)

        var pos: Int
        for (priority in 0..1) {
            pos = 0
            while (pos != -1) {
                pos = items.indexOfFirst { e -> (e.operation != null && e.operation?.priority == priority)}
                if (pos != -1) {
                    val operand1 = items[pos - 1].getCalcResult()
                    val operand2 = items[pos + 1].getCalcResult()


                    val oper = items[pos].operation
                    if (oper != null) {
                        var newElement = items[pos].operation?.executeOperation(
                            operand1,
                            operand2
                        )

                        items[pos - 1] = SimpleExpression(newElement.toString())
                        items.removeAt(pos)
                        items.removeAt(pos)
                    }
                }
            }
        }
        return items[0].toString()
    }

    private fun indexOfClosingBracket(str : String, pos : Int) : Int {
        var cnt = 0
        var bracketPos = 0
        var i = str.indexOf("(", pos)
        var iClosing = str.indexOf(")", pos)
        if (iClosing == -1)
        {
            throw IllegalArgumentException("Некорректная расстановка скобок - нет закрывающей: $str")
        }
        else if (i > iClosing ){
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

    private fun chooseOperation(str : String) : Operation? {
        return this.operations.getOrNull( this.operations.indexOfFirst{e -> e.strOperation == str})
    }
}

