import kotlin.math.abs

fun main() {

    val day = "15"

    fun part1(input: List<String>, row: Int): Int {
        val reportRaw = input
            .map { it.findInts() }
            .flatMap { (x1, y1, x2, y2) -> listOf( Vec2(x1, y1), Vec2(x2, y2)) }

        val report = reportRaw.chunked(2)

        val ans = report.map { (scanner, beacon) ->
            val yDistance = abs(scanner.y - row)
            val distance = manhattanDistance(scanner, beacon)
            if (yDistance <= distance) {
                val withinRange = ((scanner.x - distance) + yDistance .. (scanner.x + distance) - yDistance).toSet()
                if (beacon.y == row) withinRange - beacon.x else withinRange
            }
            else emptySet()
        }

        return ans
            .reduce { acc, ints ->  acc.union(ints) }
            .count()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput, 10) == 26)
//    check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input, 2000000))
//    println(part2(input))
}