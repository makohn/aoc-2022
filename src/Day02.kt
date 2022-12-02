import java.lang.IllegalArgumentException

fun main() {

    fun toScore(c:  Char) = when(c) {
        'X',
        'A' -> 1
        'Y',
        'B' -> 2
        'Z',
        'C' -> 3
        else -> throw IllegalArgumentException()
    }

    fun calcScore(o: Char, m: Char): Int {
        val sm = toScore(m)
        val so = toScore(o)
        when (m) {
            'X' -> when (o) {
                'A' -> return sm + 3
                'B' -> return sm
                'C' -> return sm + 6
            }
            'Y' -> when (o) {
                'A' -> return sm + 6
                'B' -> return sm + 3
                'C' -> return sm
            }
            'Z' -> when (o) {
                'A' -> return sm
                'B' -> return sm + 6
                'C' -> return sm + 3
            }
        }
        return -1
    }

    fun calcScore2(o: Char, m: Char): Int {
        when (m) {
            'X' -> when (o) {
                'A' -> return toScore('Z')
                'B' -> return toScore('X')
                'C' -> return toScore('Y')
            }
            'Y' -> when (o) {
                'A' -> return toScore('X') + 3
                'B' -> return toScore('Y') + 3
                'C' -> return toScore('Z') + 3
            }
            'Z' -> when (o) {
                'A' -> return toScore('Y') + 6
                'B' -> return toScore('Z') + 6
                'C' -> return toScore('X') + 6
            }
        }
        return -1
    }

    fun part1(input: List<String>) = input
        .map { it.split(" ") }.sumOf { calcScore(it.first()[0], it.last()[0]) }

    fun part2(input: List<String>) = input
        .map { it.split(" ") }.sumOf { calcScore2(it.first()[0], it.last()[0]) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
