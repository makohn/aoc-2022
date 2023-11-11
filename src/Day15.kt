import kotlin.math.abs

fun main() {

    val day = "15"

    fun List<List<Vec2>>.scanArea(row: Int) = this.map { (scanner, beacon) ->
        val yDistance = abs(scanner.y - row)
        val distance = manhattanDistance(scanner, beacon)
        if (yDistance <= distance) {
            (scanner.x - distance) + yDistance..(scanner.x + distance) - yDistance
        } else IntRange.EMPTY
    }

    fun parseInput(input: List<String>) = input
        .map { it.findInts() }
        .flatMap { (x1, y1, x2, y2) -> listOf( Vec2(x1, y1), Vec2(x2, y2)) }
        .chunked(2)

    fun List<IntRange>.merge() = this
        .filter { !it.isEmpty() }
        .sortedBy { it.first }
        .foldIndexed(ArrayDeque<IntRange>()) { idx, stack, range ->
            if (idx == 0) stack.add(range)
            else if (range.last <= stack.last().last) return@foldIndexed stack
            else if (range.first > stack.last().last + 1) stack.add(range)
            else stack.add(stack.removeLast().first..range.last)
            stack
        }

    fun List<IntRange>.notIncludes(otherRange: IntRange): IntRange? = this.firstOrNull {
        otherRange.first < it.first || otherRange.last > it.last
    }

    fun part1(input: List<String>, row: Int): Int {
        val report = parseInput(input)
        val ans = report.scanArea(row).merge()

        return ans.sumOf { it.last - it.first }
    }

    fun part2(input: List<String>, range: IntRange): Long {
        val report = parseInput(input)
        for (row in range) {
            val ans = report.scanArea(row).merge().notIncludes(range)
            if (ans != null) {
                val col = (range.toSet() - ans.toSet()).first()
                return col * 4000000L + row
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput, 10).also { println(it) } == 26)
    check(part2(testInput, 0..20).also { println(it) } == 56000011L)

    val input = readInput("Day${day}")
    println(part1(input, 2000000))
    println(part2(input, 0..4000000))
}