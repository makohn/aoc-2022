fun main() {

    operator fun IntRange.contains(other: IntRange) = other.first >= this.first && other.last <= this.last
    infix fun IntRange.overlapsWith(other: IntRange) = (
            (other.first >= this.first && other.last <= this.last) ||
            (other.first >= this.first && other.first <= this.last) ||
            (other.last >= this.first && other.last <= this.last))

    fun part1(input: List<String>): Int {
        var count = 0
        val pairs = mutableListOf<Pair<IntRange, IntRange>>()
        for (l in input) {
            val p = l.split(",")
            val p1 = p[0].split("-")
            val p2 = p[1].split("-")
            val s1 = p1[0].toInt()
            val e1 = p1[1].toInt()
            val s2 = p2[0].toInt()
            val e2 = p2[1].toInt()
            pairs.add(s1..e1 to s2 .. e2)
        }
        for (pair in pairs) {
            if ((pair.first in pair.second) || (pair.second in pair.first)) {
                count ++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val pairs = mutableListOf<Pair<IntRange, IntRange>>()
        for (l in input) {
            val p = l.split(",")
            val p1 = p[0].split("-")
            val p2 = p[1].split("-")
            val s1 = p1[0].toInt()
            val e1 = p1[1].toInt()
            val s2 = p2[0].toInt()
            val e2 = p2[1].toInt()
            pairs.add(s1..e1 to s2 .. e2)
        }
        for (pair in pairs) {
            if ((pair.first overlapsWith  pair.second) || (pair.second overlapsWith  pair.first)) {
                count ++
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}