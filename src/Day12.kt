fun main() {

    val day = "12"

    fun part1(input: List<String>): Int {
        val inputMatrix = input.toCharMatrix()
        val dataPoints = inputMatrix.dataPoints()
        val startNode = dataPoints.single { it.data == 'S' }

        fun Char.height() = when(this) {
            'S' -> 0
            'E' -> 'z' - 'a'
            else -> this - 'a'
        }

        val visited = bfs(startNode) { p ->
            inputMatrix.adjacentPoints(p).filter { it.data.height() - p.data.height() <= 1 }
        }

        return visited.filter { it.key.data == 'E' }.minBy { it.value }.value
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 31)
//    check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input))
//    println(part2(input))
}