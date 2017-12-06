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
        val parsed1 = d.parse(input)
        println("Part1: " + d.part1(parsed1))

        val parsed2 = d.parse(input)
        println("Part2: " + d.part2(parsed2))
        println()
    }

}