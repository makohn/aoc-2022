fun main() {

    fun part1(input: List<String>): Int {
        var acc = 0
        var x = 1
        var cycle = 0

        fun tick() {
            cycle++
            if (cycle in (20 .. 220) step 40) {
                acc += cycle * x
            }
        }

        for (parts in input.map { it.split(" ") }) {
            tick()
            val op = parts[0]
            when (op) {
                "addx" -> {
                    tick()
                    x+= parts[1].toInt()
                }
                "noop" -> Unit
            }
        }
        return acc
    }

    fun part2(input: List<String>): Int {
        var x = 1
        var cycle = 0
        var cursor = 0

        fun tick() {
            cycle++
            val crt = if (cursor in x-1..x+1) "#" else "."
            print(crt)
            cursor++
            if (cursor == 40) {
                println()
                cursor = 0
            }
        }

        for (parts in input.map { it.split(" ") }) {
            tick()
            val op = parts[0]
            if (op == "addx") {
                tick()
                x += parts[1].toInt()
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}