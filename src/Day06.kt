fun main() {

    fun findMarker(input: String, windowSize: Int) = input
        .withIndex()
        .windowed(windowSize, 1)
        .first { it.map { i -> i.value }.distinct().size == windowSize }
        .last()
        .index + 1

    fun part1(input: String) = findMarker(input, 4)

    fun part2(input: String) = findMarker(input, 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput[0]) == 7)
    check(part1(testInput[1]) == 5)
    check(part1(testInput[2]) == 6)
    check(part1(testInput[3]) == 10)
    check(part1(testInput[4]) == 11)

    check(part2(testInput[0]) == 19)
    check(part2(testInput[1]) == 23)
    check(part2(testInput[2]) == 23)
    check(part2(testInput[3]) == 29)
    check(part2(testInput[4]) == 26)

    val input = readInput("Day06")
    println(part1(input.first()))
    println(part2(input.first()))
}