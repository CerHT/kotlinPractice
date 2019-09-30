package cht.anything

/**
 *
 * 测试语法
 * @author chenhantao
 * @since 2019/9/18
 */
class Test {
    var name: String? = ""
    var num: Int? = 0
}

fun main() {
    var test: Test? = Test()

    val bool: Boolean? = true

    bool?.let { println(123123) }

    var num: Int? = 10
    num = num?.let {

        if (num == 12) {
            num = null
        }
        num?.plus(1)

    }

    test?.num = 6
    //test = null
    println(compare(test, 5))

    val map = HashMap<String, Int>()
    map["1"] = 2
    testMap(map)
}

fun compare(a: Test?, b: Int): Int? {
    a ?: return 0

    return a.num?.let {
        val temp = it.compareTo(b)
        return when {
            temp < 0 -> -1
            temp > 0 -> 1
            else -> 0
        }

    }
}

fun testMap(map: HashMap<String, Int>?) {
    map?.get("1")?.let { map.put("1", it + 3) } ?: map?.put("1", 3)
    println(map)
}