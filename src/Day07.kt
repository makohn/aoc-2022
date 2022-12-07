fun main() {

    class TreeNode<T>(val name: String, val value: T, val parent: TreeNode<T>? = null) {
        val children = mutableListOf<TreeNode<T>>()

        fun add(child: TreeNode<T>) = children.add(child)
    }

    fun TreeNode<Int>.getDirSize(): Int {
        return children.fold(0) { acc, treeNode ->
            acc + if (treeNode.value == -1) treeNode.getDirSize() else treeNode.value
        }
    }

    fun getDirs(input: List<String>): List<TreeNode<Int>> {
        val tree = TreeNode("/", -1)
        val dirs = mutableListOf(tree)
        var curDir = tree
        for (command in input) {
            val c = command.split(" ")
            when (c[0]) {
                "$" -> when (c[1]) {
                    "cd" -> curDir = if (c[2] == "..") curDir.parent!! else {
                        val newDir = TreeNode(c[1], -1, curDir)
                        dirs.add(newDir)
                        curDir.add(newDir)
                        newDir
                    }
                }
                "dir" -> Unit
                else -> curDir.add(TreeNode(c[1], c[0].toInt(), curDir))
            }
        }
        return dirs
    }

    fun part1(input: List<String>) = getDirs(input)
            .map { it.getDirSize() }
            .filter { it <= 100_000 }
            .sum()

    fun part2(input: List<String>): Int {
        val dirs = getDirs(input)
        val root = dirs.first { it.name == "/" }
        val used = root.getDirSize()
        val total = 70_000_000
        val needed = 30_000_000
        val actuallyNeeded = needed - (total - used)
        return dirs
            .map { it.getDirSize() }
            .filter { it >= actuallyNeeded }
            .min()
    }
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}