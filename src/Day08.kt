fun main() {

    fun part1(input: List<String>): Int {
        val grid = input
            .map { it.chars().map { c -> c - 48 }.toArray() }.toTypedArray()

        val width = grid[0].size
        val height = grid.size

        var visibleTrees = width*2 + height*2 - 4

        for (row in 1..< grid.lastIndex) {
            for (col in 1..< grid[row].lastIndex) {
                var visible = 4
                left@for (beforeCol in 0..<col) {
                    if (grid[row][beforeCol] >= grid[row][col]) {
                        visible--
                        break@left
                    }
                }
                right@for (afterCol in col+1 .. grid[row].lastIndex) {
                    if (grid[row][afterCol] >= grid[row][col]) {
                        visible--
                        break@right
                    }
                }
                top@for (beforeRow in 0..<row) {
                    if (grid[beforeRow][col] >= grid[row][col]) {
                        visible--
                        break@top
                    }
                }
                bottom@for (afterRow in row+1 .. grid.lastIndex) {
                    if (grid[afterRow][col] >= grid[row][col]) {
                        visible--
                        break@bottom
                    }
                }
                if (visible > 0) {
                    visibleTrees++
                }
             }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    // check(part2(testInput) == 0)

    val input = readInput("Day08")
    println(part1(input))
    // println(part2(input))
}