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
                g = d // {a=1, b=107900, c=124900, f=0, d=3, e=31317, g=-76583}, {a=1, b=107900, c=124900, f=0, d=3, e=31318, g=-76582}
                g *= e
                g -= b
                if (g == 0) {
                    f = 0
                }
                e -= 1
                g = e
                g -= b
            } while (g != 0) // jnz g -8 // goto line 12: set g d
            d -= -1
            g = d
            g -= b
        } while (g != 0) // jnz g -13
        if (f == 0) {
            h -= -1
        }
        g = b
        g -= c
        if (g == 0) {
            println(h)
            return //     jnz 1 3 // Exit
        }
        b -= -17
    } while (true) // jnz 1 -23


}