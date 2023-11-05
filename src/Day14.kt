
fun main() {

    val day = "14"

    data class Pos(val x: Int, val y: Int)

    data class Path(val from: Pos, val to: Pos) {
        operator fun contains(other: Pos) =
            ((other.x in from.x .. to.x) && (other.y in from.y .. to.y)) ||
                    ((other.x in to.x .. from.x) && (other.y in to.y .. from.y))
    }

    operator fun CharMatrix.contains(pos: Pos) = pos.y in indices && pos.x in this[0].indices
    operator fun CharMatrix.get(pos: Pos) = this[pos.y][pos.x]

    fun simulateSand(map: CharMatrix, sandSource: Pos): Pos {
        var sandPos = sandSource
        dropSand@while (sandPos in map) {
            for (pos in arrayOf(
                Pos(sandPos.x, sandPos.y+1),
                Pos(sandPos.x-1, sandPos.y+1),
                Pos(sandPos.x+1, sandPos.y+1)
            )) {
                if (pos !in map) return Pos(-1, -1) else if (map[pos] == '.') {
                    sandPos = pos
                    continue@dropSand
                }
            }
            return sandPos
        }
        return Pos(-1, -1)
    }

    fun part1(input: List<String>): Int {
        val rockCoordinates = input
            .map { line -> line.split(" -> ")
                .map { coord -> coord.split(",") }
                .map { (x, y) -> Pos(x.toInt(), y.toInt()) }
            }

        val yMax = rockCoordinates.maxOf { it.maxOf { it.y } }
        val xMin = rockCoordinates.minOf { it.minOf { it.x } }
        val xMax = rockCoordinates.maxOf { it.maxOf { it.x } }

        println(yMax)
        println(xMin)
        println(xMax)

        val rockPaths = rockCoordinates.flatMap { it.zipWithNext().map { (a, b) -> Path(a, b) } }

        val map = Array(yMax+1) { i -> CharArray((xMax - xMin) + 1) { j ->
            val p = Pos(xMin + j, i)
            if (rockPaths.any { p in it }) '#' else '.'
        }}

        fun CharMatrix.visualize() = this.forEach { println(it.joinToString("")) }
        map.visualize()

        val sandSource = Pos(500 - xMin, 0)

        var sandPos = simulateSand(map, sandSource)
        var c = 0
        while (sandPos in map && sandPos != sandSource) {
            map[sandPos.y][sandPos.x] = 'o'
            sandPos = simulateSand(map, sandSource)
            c++
            map.visualize()
            println()
        }

        return c
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}