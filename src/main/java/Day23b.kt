fun main(args: Array<String>) {

    var b = 79 // Tejpbit 93
    var c = 79
    b *= 100
    b -= -100000
    c = b
    c -= -17000
    var f = 1
    var g = 0
    var d = 0
    var e = 0
    var h = 0
    do {
        f = 1
        d = 2
        do {
            e = 2
            do {
                val eRange = 2..b

                if (d * e == b) {
                    f = 0
                }
                e++
            } while (e != b) // jnz g -8 // goto line 12: set g d
            d++
            g = d
            g -= b
        } while (g != 0) // jnz g -13
        if (f == 0) {
            h++
        }
        g = b
        g -= c
        if (g == 0) {
            println(h)
            return //     jnz 1 3 // Exit
        }
        b += 17
    } while (true) // jnz 1 -23


}