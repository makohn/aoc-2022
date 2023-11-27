import kotlin.math.max

fun main() {

    val day = "16"

    data class Valve(val idx: Int, val flowRate: Int, val tunnels: List<String>)

    fun parseInput(input: List<String>) = input
        .map { Regex("([A-Z]{2}|\\d+)").findAll(it).map { res -> res.value }.toList() }
        .mapIndexed { idx, res -> res[0] to Valve(idx, res[1].toInt(), res.drop(2)) }
        .toMap()

    fun part1(input: List<String>): Int {
        val valves = parseInput(input)
        println(valves)

        data class Configuration(val openValvesMask: Long, val atValve: String)
        val maxTime = 30
        val dp = Array(maxTime + 1) { HashMap<Configuration, Int>() }
        fun memoize(time: Int, openValvesMask: Long, atValve: String, pressure: Int) {
            val config = Configuration(openValvesMask, atValve)
            // Get the current pressure for this valve
            val current = dp[time][config]
            // Set the time-configuration pressure to the given pressure if it doesn't exit or is lower
            if (current == null || pressure > current) dp[time][config] = pressure
        }
        // In the beginning we are at valve AA, no valve open, no pressure released
        memoize(0, 0, "AA", 0)

        for (time in 0 ..< maxTime) {
            // Get all memoized configurations and their pressure level at the current time
            for ((config, pressure) in dp[time]) {
                val (openValvesMask, valveName) = config
                val valve = valves[valveName]!!
                val valvePos = 1L shl valve.idx
                // if this configuration's valve's flow rate is relevant and the valve is not yet open...
                if (valve.flowRate != 0 && (valvePos and openValvesMask) == 0L) {
                    // ...open it and increase pressure level correspondingly
                    memoize(
                        /*time*/ time + 1,
                        /*openValveMask*/ valvePos or openValvesMask,
                        /*atValve*/ valveName,
                        /*pressure*/ pressure + (maxTime - time -1) * valve.flowRate
                    )
                }
                // Now go to all the neighbouring valves (without opening them yet)
                for (neighbourValve in valve.tunnels) {
                    memoize(
                        /*time*/ time + 1,
                        /*openValveMask*/ openValvesMask,
                        /*atValve*/ neighbourValve,
                        /*pressure*/ pressure
                    )
                }
            }
        }

        return dp[maxTime].maxOf { (_, pressure) -> pressure }
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