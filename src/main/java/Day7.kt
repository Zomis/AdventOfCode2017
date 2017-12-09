import java.util.HashMap

class Day7: Day<Map<String, Day7.ProgramTree>> {
    class ProgramTree(val root: String, val weight: Int, val children: Collection<String>) {

        override fun toString(): String {
            return root
        }

        fun weightBalance(allPrograms: Map<String, ProgramTree>): Int {
            return this.weight + children.map { allPrograms[it]!! }.sumBy {
                it.weightBalance(allPrograms)
            }
        }

        fun weightText(allPrograms: Map<String, ProgramTree>): String {
            if (children.isEmpty()) {
                return "$root = $weight"
            }
            val childrenJoined = children.joinToString(" + ")
            val childrenWeight = children.map { allPrograms[it]!!.weightBalance(allPrograms) }
            val childrenWeightJoined = childrenWeight.joinToString(" + ")
            val weightSum = weight + childrenWeight.sum()
            return "$root + ($childrenJoined) = $weight + ($childrenWeightJoined) = $weightSum"
            // padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
        }

        fun isBalanced(allPrograms: Map<String, ProgramTree>): Boolean {
            if (children.isEmpty()) {
                return true
            }
            val first = allPrograms[children.iterator().next()]!!.weightBalance(allPrograms)
            return children.map { allPrograms[it]!! }.all { it.weightBalance(allPrograms) == first }
        }

        fun printRecursive(allPrograms: Map<String, ProgramTree>, indentation: Int) {
            for (i in 1..indentation) {
                print(' ')
            }
            println(weightText(allPrograms))
            children.forEach({
                allPrograms[it]!!.printRecursive(allPrograms, indentation + 2)
            })
        }

    }

    override fun parse(text: String): Map<String, ProgramTree> {
        val lines = text.split("\n").map { it.trim() }
        val trees = HashMap<String, ProgramTree>()
        for (line in lines) {
            val split = line.split(" -> ")
            val name = split[0].substringBefore(' ')
            val score = split[0].substringAfter('(').substringBefore(')').toInt()
            val children = if (split.size > 1) split[1].split(", ") else emptyList()


            //println("$name ($score) -> $children")
            trees[name] = ProgramTree(name, score, children)
        }
        return trees
    }

    override fun part1(input: Map<String, ProgramTree>): String {
        val possible = input.keys
        val allChildren = input.values.flatMap { it.children }
        return (possible - allChildren).first()
    }

    override fun part2(input: Map<String, ProgramTree>): Any {
        val list = input.values.filter { !it.isBalanced(input) }
        list.forEach({
            println(it)
            println(it.weightText(input))
            it.children.forEach({ child ->
                println(input[child]!!.weightText(input))
            })
        })
        val rootProgram = input[part1(input)]!!
        rootProgram.printRecursive(input, 0)
        return 0
    }

}