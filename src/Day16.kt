import kotlin.math.max

fun main() {

    val day = "16"

    data class Valve(val idx: Int, val name: String, val flowRate: Int, val tunnels: List<String>)

    fun parseInput(input: List<String>) = input
        .map { Regex("([A-Z]{2}|\\d+)").findAll(it).map { res -> res.value }.toList() }
        .mapIndexed { idx, res -> Valve(idx, res[0], res[1].toInt(), res.drop(2)) }

    fun part1(input: List<String>): Int {
        val valves = parseInput(input)
        println(valves)

        val valveMap = valves.associateBy { it.name }

        // Floyd-Warshall could be used to determine the shortest distance between all pairs of valves
        // But it isn't the most efficient algorithm here, as it calculates the shortest distance between _all_ pairs
        // We are only looking at a select number of nodes/valves

        // BFS
        val distanceMap = HashMap<Valve, HashMap<Valve, Int>>()
        for (valve in valves) {

            if (valve.name != "AA" && valve.flowRate == 0) continue

            distanceMap.computeIfAbsent(valve) { hashMapOf(valve to 0, valveMap["AA"]!! to 0) }

            val visited = hashSetOf(valve.name)
            val queue = ArrayDeque<Pair<Int, Valve>>()
            queue += 0 to valve

            while (queue.isNotEmpty()) {
                val (distance, position) = queue.removeFirst()
                for (neighbor in position.tunnels) {
                    if (neighbor in visited) continue
                    visited.add(neighbor)
                    val neighborValve = valveMap[neighbor]!!
                    if (neighborValve.flowRate != 0) {
                        distanceMap[valve]!![neighborValve] = distance + 1
                    }
                    queue += distance + 1 to neighborValve
                }
            }
            distanceMap[valve]!!.remove(valve)
            if (valve.name != "AA") {
                distanceMap[valve]?.remove(valveMap["AA"])
            }
        }

        println()
        distanceMap.keys.forEach {
            println("${it.name}: ${distanceMap[it]?.map { (k, v) -> k.name to v }}")
        }

        // DFS with memoization:
        data class State(val time: Int, val valve: Valve, val openValveBitMask: Long)
        val cache = HashMap<State, Int>()

        fun dfs(state: State): Int {
            println(state)
            if (state in cache) return cache[state]!!

            val (time, valve, bitmask) = state
            var maxVal = 0
            for (neighbor in distanceMap[valve]!!.keys) {
                val bit = 1L shl valve.idx
                if (bitmask and bit != 0L) continue
                val remainingTime = time - distanceMap[valve]!![neighbor]!! - 1
                if (remainingTime <= 0) continue
                maxVal = max(maxVal,
                    dfs(State(remainingTime, neighbor, bitmask or bit))
                            + neighbor.flowRate * remainingTime)
            }
            cache[State(time, valve, bitmask)] = maxVal
            return maxVal
        }

        val ans = dfs(State(30, valveMap["AA"]!!, 0))
        println(ans)
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