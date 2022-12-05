import java.util.Stack

fun main() {

    fun part1(input: String): String {
        val stacks = mutableListOf<Stack<Char>>()
        val (vis, moves) = input.split("\n\n")
        vis.lines()
            .reversed()
            .drop(1)
            .forEach {
                it.filterIndexed { i, _ -> i % 4 == 1
                }.forEachIndexed { i, s ->
                    if (i >= stacks.size) stacks.add(Stack<Char>())
                    if (s.isLetter()) stacks[i].push(s)
                }
            }

        for (move in moves.lines()) {
            val comp = move.split(" ")
            val count = comp[1].toInt()
            val src = comp[3].toInt() - 1
            val dst = comp[5].toInt() - 1
            repeat(count) {
                stacks[dst].push(stacks[src].pop())
            }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    fun part2(input: String): String {
        val stacks = mutableListOf<Stack<Char>>()
        val (vis, moves) = input.split("\n\n")
        vis.lines()
            .reversed()
            .drop(1)
            .forEach {
                it.filterIndexed { i, _ -> i % 4 == 1
                }.forEachIndexed { i, s ->
                    if (i >= stacks.size) stacks.add(Stack<Char>())
                    if (s.isLetter()) stacks[i].push(s)
                }
            }

        for (move in moves.lines()) {
            val comp = move.split(" ")
            val count = comp[1].toInt()
            val src = comp[3].toInt() - 1
            val dst = comp[5].toInt() - 1
            val els = mutableListOf<Char>()
            repeat(count) {
                els.add(stacks[src].pop())
            }
            els.reversed().forEach {
                stacks[dst].push(it)
            }
        }

        return stacks.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputFull("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputFull("Day05")
    println(part1(input))
    println(part2(input))
}