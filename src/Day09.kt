import kotlin.math.abs
import kotlin.math.sign

fun main() {

    data class Pos(var x: Int, var y: Int)

    infix fun Pos.dist(other: Pos) =
        (this.x - other.x) to (this.y - other.y)

    fun part1(input: List<String>): Int {
        val head = Pos(0, 0)
        val tail = Pos(0, 0)

        val tailPositions = mutableSetOf(Pos(0, 0))

        for ((op, count) in input.map { it.split(" ") }) {
            repeat(count.toInt()) {
                when (op) {
                    "L" -> head.x--
                    "R" -> head.x++
                    "U" -> head.y++
                    "D" -> head.y--
                }
                val (dx, dy) = head dist tail
                if (abs(dx) > 1 || abs(dy) > 1) {
                    tail.x += dx.sign
                    tail.y += dy.sign
                    tailPositions.add(tail.copy())
                }
            }
        }

        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        val knots = Array(10) { Pos(0, 0) }
        val head = knots[0]

        val knotPositions = mutableSetOf(Pos(0,0))

        for ((op, count) in input.map { it.split(" ") }) {
            repeat(count.toInt()) {
                when (op) {
                    "L" -> head.x--
                    "R" -> head.x++
                    "U" -> head.y++
                    "D" -> head.y--
                }
                for (i in 1 until knots.size) {
                    val knot = knots[i]
                    val pred = knots[i-1]
                    val (dx, dy) = pred dist knot
                    if (abs(dx) > 1 || abs(dy) > 1) {
                        knot.x += dx.sign
                        knot.y += dy.sign
                    }
                    if (i == 9) {
                        knotPositions.add(knot.copy())
                    }
                }
            }
        }

        return knotPositions.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    check(part1(testInput) == 13)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}