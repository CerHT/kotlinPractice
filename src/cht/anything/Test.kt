package cht.anything

/**
 *
 * TODO
 * @author chenhantao
 * @since 2019/9/18
 */
class Test {
    var name: String? = ""
    var num: Int? = 0
}

fun main() {
    var test: Test = Test()

    val bool: Boolean? = true

    bool?.let { println(123123) }

    test.num = 6
    println(compare(test, 5))
}

fun compare(a: Test, b: Int): Int? {
    return a.num?.let {
        val temp = it.compareTo(b)
        return when {
            temp < 0 -> -1
            temp > 0 -> 1
            else -> 0
        }

    }
}