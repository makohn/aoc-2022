fun main() {

    val day = "13"

    fun eval(str: MutableList<Char>): List<Any> {
        val list = mutableListOf<Any>()
        var cs = ""
        while (str.isNotEmpty()) {
            when (val c = str.removeFirst()) {
                in '0'..'9' -> cs+=c
                '[' -> list.add(eval(str))
                ']' -> {
                    if (cs.isNotEmpty()) list.add(cs.toInt())
                    return list
                }
                ',' -> {
                    if (cs.isNotEmpty()) list.add(cs.toInt())
                    cs = ""
                }
                else -> error("Unexpected!")
            }
        }
        return list
    }

    fun compare(a: Any, b: Any): Int {
        if (a is Int && b is Int) {
            return a.compareTo(b)
        }

        val l = if (a is List<*>) a else listOf(a)
        val r = if (b is List<*>) b else listOf(b)

        for (i in l.indices) {
            if (i !in r.indices) return 1
            val res = compare(l[i]!!, r[i]!!)
            if (res != 0) return res
        }
        return l.size.compareTo(r.size)
    }

    fun part1(input: List<String>): Int {
        val numRightOrder = input
            .asSequence()
            .filter { it.isNotEmpty() }
            .chunked(2)
            .map {
                val (a, b) = it
                eval(a.toMutableList())[0] as List<*> to eval(b.toMutableList())[0] as List<*>
            }
            .map { (a, b) ->  compare(a, b) }
            .foldIndexed(0) { idx, acc, b -> if (b < 0) acc + idx + 1 else acc }

        return numRightOrder
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 13)
    //check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input))
    //println(part2(input))
}