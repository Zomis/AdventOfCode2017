enum class Dir4 constructor(val x: Int, val y: Int) {

    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    ;

    fun opposite(): Dir4 {
        return when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
        }
    }

    fun rotateLeft(): Dir4 {
        return when (this) {
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            UP -> LEFT
        }
    }

    fun rotateRight(): Dir4 {
        return rotateLeft().rotateLeft().rotateLeft()
    }

}

