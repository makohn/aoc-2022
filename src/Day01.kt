fun main() {
    fun part1(input: List<String>): Int {
        val calories = mutableListOf<Int>()
        var count = 0
        for (s in input) {
            if (s.isEmpty()) {
                calories.add(count)
                count = 0
            } else {
                count += s.toInt()
            }
        }
        return calories.max()
    }

    fun part2(input: List<String>): Int {
        val calories = mutableListOf<Int>()
        var count = 0
        for (s in input) {
            if (s.isEmpty()) {
                calories.add(count)
                count = 0
            } else {
                count += s.toInt()
            }
        }
        calories.add(count)
        var total = 0
        total += calories.max()
        calories.remove(calories.max())
        total += calories.max()
        calories.remove(calories.max())
        total += calories.max()
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
