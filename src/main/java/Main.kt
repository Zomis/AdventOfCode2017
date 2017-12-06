fun main(args: Array<String>) {

    for (day in 1..25) {
        val d: Day<Any>
        try {
            val cl = Day::class.java.classLoader.loadClass("Day$day")
            println(cl.simpleName)
            d = cl.newInstance() as Day<Any>
        } catch (ex: ClassNotFoundException) {
            return
        }
        println("Load $day")
        val url = d.javaClass.classLoader.getResource("day$day")
        val input = url.readText()
        val parsed = d.parse(input)

        println("Part1: " + d.part1(parsed))
        println("Part2: " + d.part2(parsed))
        println()
    }

}