fun main(args: Array<String>) {
    var range: IntProgression = 1..25
    //range = 25 downTo 1
    for (day in range) {
        val d: Day<Any>
        try {
            val cl = Day::class.java.classLoader.loadClass("Day$day")
            println(cl.simpleName)
            d = cl.newInstance() as Day<Any>
        } catch (ex: ClassNotFoundException) {
            continue
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