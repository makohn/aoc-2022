
fun main() {

    val day = "16"

    data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>, var open: Boolean = false)

    fun parseInput(input: List<String>) = input
        .map { Regex("([A-Z]{2}|\\d+)").findAll(it).map { res -> res.value }.toList() }
        .map { res -> Valve(res[0], res[1].toInt(), res.drop(2))}

    // This problem is a reformulation of the knapsack problem
    // see: https://courses.csail.mit.edu/6.006/fall11/rec/rec21_knapsack.pdf
    //
    // S: 30 (capacity)
    // n: number of valves
    // si: cost (in minutes)
    // vi: flow rate
    fun part1(input: List<String>): Int {
        val valves = parseInput(input)
        println(valves)

        val distanceMap = mutableMapOf<Valve, Map<Valve, Int>>()
        for (valve in valves) {
            val distances = bfs(valve) { va -> valves.filter { vb -> vb.name in va.tunnels }}
            distanceMap[valve] = distances
        }

        println(distanceMap)

        var time = 30
        var currentValve = valves.first()
        var totalFlow = 0
        while (time > 0) {
            val distances = bfs(currentValve) { va -> valves.filter { vb -> vb.name in va.tunnels }}
            val relevantDistances = distances.filter { !it.key.open }
            val gains = relevantDistances
                .map { (v, d) -> v to (time - d) * v.flowRate }
                .sortedByDescending { it.second }
            val nextValve = gains.first().first
            totalFlow += gains.first().second

            println("=== Minute: $time ===")
            println("Relevant: $relevantDistances")
            println("Move to: $nextValve")
            println("Total: $totalFlow")
            println()

            time -= relevantDistances[nextValve]!!
            nextValve.open = true
            currentValve = nextValve
            time--
        }
        println(totalFlow)
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 1651)
//    check(part2(testInput) == 0)

    val input = readInput("Day${day}")
    println(part1(input))
//    println(part2(input))
}