
fun main() {

    val day = "11"

    fun toOperation(input: String): (Int) -> Int {
        val (x, op, y) = input.split(" ")
        return { old ->
            val a = if (x == "old") old else x.toInt()
            val b = if (y == "old") old else y.toInt()
            when (op) {
                "+" -> a + b
                "*" -> a * b
                else -> old
            }
        }
    }

    data class Item(var worryLevel: Int) {
        fun adaptWorryLevel(operation: (Int) -> Int) {
            worryLevel = operation(worryLevel)
        }
    }

    class Monkey(
        val id: Int,
        private val items: MutableList<Item>,
        private val operation: (Int) -> Int,
        private val divisor: Int,
        private val otherMonkeys: Pair<Int, Int>,
        var inspectionCount: Int = 0
    ) {

        lateinit var allOtherMonkeys: List<Monkey>

        fun inspectItems() {
            items.forEach {
                it.adaptWorryLevel(operation)
                it.worryLevel /= 3
                if (it.worryLevel % divisor == 0)
                    allOtherMonkeys.first { monkey -> monkey.id == otherMonkeys.first }.add(it)
                else
                    allOtherMonkeys.first { monkey -> monkey.id == otherMonkeys.second }.add(it)
            }
            inspectionCount += items.size
            items.clear()
        }

        fun add(item: Item) {
            items.add(item)
        }
    }

    fun part1(input: List<String>): Int {
        val monkeys = input
            .filter { it.isNotEmpty() }
            .chunked(6)
            .map { parts ->
                val id = parts[0].substringAfter("Monkey ").replace(":", "").toInt()
                val items = parts[1].substringAfter("Starting items: ").trim().split(",").map { Item(it.trim().toInt()) }.toMutableList()
                val operation = toOperation(parts[2].substringAfter("Operation: new = "))
                val divisor = parts[3].substringAfter("divisible by ").toInt()
                val monkeyA = parts[4].substringAfter("throw to monkey ").toInt()
                val monkeyB = parts[5].substringAfter("throw to monkey ").toInt()
                Monkey(id, items, operation, divisor,monkeyA to monkeyB)
            }
        monkeys.forEach { it.allOtherMonkeys = monkeys }

        repeat(20) {
            monkeys.forEach { it.inspectItems() }
        }

        return monkeys.map { it.inspectionCount }.sortedDescending().take(2).fold(1) { acc, i -> acc * i}
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}