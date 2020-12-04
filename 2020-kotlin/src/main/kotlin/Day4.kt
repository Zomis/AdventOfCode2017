package net.zomis.aoc.y2020

object Day4 {

    data class Passport(val values: Map<String, String>) {
        fun valueInRange(key: String, range: IntRange): Boolean = values.getValue(key).toIntOrNull()?.let { it in range } ?: false
        fun heightValid(): Boolean {
            val height = values.getValue("hgt")
            val valid = height.endsWith("cm") || height.endsWith("in")
            if (!valid) return false
            return when {
                height.endsWith("cm") -> {
                    val h = height.replace("cm", "").toInt()
                    h in 150..193
                }
                height.endsWith("in") -> {
                    val h = height.replace("in", "").toInt()
                    h in 59..76
                }
                else -> false
            }
        }
        fun regexValid(key: String, regex: Regex) = values.getValue(key).matches(regex)
    }
    val day = Days.day<List<Passport>>(4) {
        input {
            val str = this.toList().joinToString("\n")
            val passports = str.split("\n\n")
            val each = passports.map { it.split("\n", " ") }
            each.map { passport ->
                Passport(passport.associate {
                    val split = it.split(":")
                    split[0] to split[1]
                })
            }
        }
        part1 {
            val allKeys = input.flatMap { it.values.keys }.distinct()
            val requiredKeys = allKeys.minus("cid")
            input.count { it.values.keys.containsAll(requiredKeys) }
        }
        part2 {
            val allKeys = input.flatMap { it.values.keys }.distinct()
            val requiredKeys = allKeys.minus("cid")
            input.filter { it.values.keys.containsAll(requiredKeys) }
                    .filter { it.valueInRange("byr", 1920..2002) }
                    .filter { it.valueInRange("iyr", 2010..2020) }
                    .filter { it.valueInRange("eyr", 2020..2030) }
                    .filter { it.heightValid() }
                    .filter { it.regexValid("hcl", Regex("#[\\da-f]+")) }
                    .filter { it.regexValid("ecl", Regex("(amb|blu|brn|gry|grn|hzl|oth)")) }
                    .filter { it.regexValid("pid", Regex("\\d{9}")) }
                    .count()
        }
    }

}
