package cht.sort

import cht.sort.array.BubbleSort

/**
 *
 * 测试类
 * @author chenhantao
 * @since 2019/9/5
 */

fun main() {
    val array = arrayOf(1, 3, 21, 44, 123, 411, 444, 31, 17)
    BubbleSort.bubbleSortBase(array)

    array.forEach { print("$it, ") }

}
