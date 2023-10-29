fun main() {

    val day = "12"

    fun solve(input: List<String>, predicate: (Int) -> Boolean, startWith: Char, vararg endWith: Char): Int {
        val inputMatrix = input.toCharMatrix()
        val dataPoints = inputMatrix.dataPoints()
        val startNode = dataPoints.single { it.data == startWith }

        fun Char.height() = when(this) {
            'S' -> 0
            'E' -> 'z' - 'a'
            else -> this - 'a'
        }

        val visited = bfs(startNode) { p ->
            inputMatrix.adjacentPoints(p).filter { predicate(it.data.height() - p.data.height()) }
        }

        return visited.filter { it.key.data in endWith }.minBy { it.value }.value
    }

    fun part1(input: List<String>): Int {
        return solve(input, { diff -> diff <= 1 }, 'S', 'E')
    }

    fun part2(input: List<String>): Int {
        return solve(input, { diff -> diff >= -1 }, 'E', 'a', 'S')
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}