import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputFull(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

data class DataPoint<T>(val x: Int, val y: Int, val data: T)

typealias CharMatrix = Array<CharArray>
fun List<String>.toCharMatrix() = Array(size) { idx -> get(idx).toCharArray() }
fun CharMatrix.dimension() = size to get(0).size
fun CharMatrix.dataPoints() = flatMapIndexed { i, r -> r.mapIndexed { j, d -> DataPoint(i, j, d) } }
fun CharMatrix.adjacentPoints(dataPoint: DataPoint<Char>) = buildList {
    val (x, y, _) = dataPoint
    val (n, m) = dimension()
    for ((a, b) in listOf(x-1 to y, x+1 to y, x to y-1, x to y+1)) {
        if (a in 0 until n && b in 0 until m) add(DataPoint(a, b, this@adjacentPoints[a][b]))
    }
}

fun <N> bfs(startNode: N, adjacent: (N) -> List<N>): HashMap<N, Int> {

    val queue = ArrayDeque<N>()
    val visited = HashMap<N, Int>()

    fun enqueue(node: N, distance: Int) {
        if (node in visited) return
        visited[node] = distance
        queue += node
    }

    enqueue(startNode, 0)

    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        val distance = visited[node]!! + 1

        for (otherNode in adjacent(node)) {
            enqueue(otherNode, distance)
        }
    }

    return visited
}