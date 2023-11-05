
fun main() {

    val day = "14"

    data class Pos(val x: Int, val y: Int)

    data class Path(val from: Pos, val to: Pos) {
        operator fun contains(other: Pos) =
            ((other.x in from.x .. to.x) && (other.y in from.y .. to.y)) ||
                    ((other.x in to.x .. from.x) && (other.y in to.y .. from.y))
    }

    fun Map<Pos, Char>.bounds() = this.keys.maxOf { it.x } to this.keys.maxOf { it.y }

    fun Map<Pos, Char>.visualize() {
        val (x, y) = bounds()
        for (i in 0..y) {
            for (j in 0..x) {
                print(getOrDefault(Pos(j, i), '.'))
            }
            println()
        }
    }

    operator fun Pos.minus(other: Pos) = Pos(this.x - other.x, this.y - other.y)

    fun simulateSand(map: Map<Pos, Char>, sandSource: Pos, bottom: Int): Pos {
        var sandPos = sandSource
        dropSand@while (sandPos.y <= bottom && map[sandPos] == null) {
            for (pos in arrayOf(
                Pos(sandPos.x, sandPos.y+1),
                Pos(sandPos.x-1, sandPos.y+1),
                Pos(sandPos.x+1, sandPos.y+1)
            )) {
                if (map[pos] == null) {
                    sandPos = pos
                    continue@dropSand
                }
            }
            return sandPos
        }
        return sandPos
    }

    fun parseInput(input: List<String>): Pair<MutableMap<Pos, Char>, Pos> {
        val rockCoordinates = input
            .map { line ->
                line.split(" -> ")
                    .map { coord -> coord.split(",") }
                    .map { (x, y) -> Pos(x.toInt(), y.toInt()) }
            }

        val yMax = rockCoordinates.maxOf { it.maxOf { it.y } }
        val xMin = rockCoordinates.minOf { it.minOf { it.x } }
        val xMax = rockCoordinates.maxOf { it.maxOf { it.x } }

        val refPos = Pos(xMin, 0)

        val rockPaths = rockCoordinates.flatMap { it.zipWithNext().map { (a, b) -> Path(a - refPos, b - refPos) } }

        return buildMap {
            for (i in 0..yMax) {
                for (j in 0..xMax - xMin) {
                    val p = Pos(j, i)
                    if (rockPaths.any { p in it }) put(p, '#')
                }
            }
        }.toMutableMap() to Pos(500 - xMin, 0)
    }

    fun part1(input: List<String>): Int {
        val (map, source) = parseInput(input)
        val (_, y) = map.bounds()
        var sandPos = simulateSand(map, source, y)
        var c = 0
        while (sandPos.y < y) {
            map[sandPos] = 'o'
            c++
            sandPos = simulateSand(map, source, y)
//            map.visualize()
//            println()
        }
        return c
    }

    fun part2(input: List<String>): Int {
        val (map, source) = parseInput(input)
        val (_, y) = map.bounds()
        var sandPos = Pos(0, 0)
        var c = 0
        while (sandPos != source) {
            sandPos = simulateSand(map, source, y)
            map[sandPos] = 'o'
            c++
//            map.visualize()
//            println()
        }
        return c
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput).also { println(it) } == 24)
    check(part2(testInput).also { println(it) } == 93)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}