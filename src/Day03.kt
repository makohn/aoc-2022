fun main() {
    
    fun part1(input: List<String>): Int {
        val items = mutableListOf<Char>()
        for (r in input) {
            outer@for (c in 0 until r.length/2) {
                for (c2 in r.length/2 until r.length) {
                    if (r[c] == r[c2]) {
                        items.add(r[c])
                        break@outer
                    }
                }
            }
        }
        return items.sumOf { if (it.isUpperCase()) it - 'A' + 27 else it - 'a' + 1 }
    }

    fun part2(input: List<String>): Int {
        val scores = mutableListOf<Int>()
        for (r in input.indices step 3) {
            val s1 = input[r].toList().toSet()
            val s2 = input[r+1].toList().toSet()
            val s3 = input[r+2].toList().toSet()
            val res = s1.intersect(s2).intersect(s3)
            scores.add(res.map { if (it.isUpperCase()) it - 'A' + 27 else it - 'a' + 1 }[0])
        }
        return scores.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}