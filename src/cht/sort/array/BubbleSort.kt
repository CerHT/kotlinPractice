package cht.sort.array

/**
 *
 * 冒泡排序
 * @author chenhantao
 * @since 2019/9/5
 */
class BubbleSort {
    companion object {

        fun <T : Comparable<T>> bubbleSortBase(array: Array<T>) {
            if (array.isEmpty()) {
                println("数组为空")
                return
            }

            val start = System.currentTimeMillis()

            var temp: T
            for (i in array.indices) {
                for (j in 0 until array.size - i - 1) {
                    if (array[j] > array[j + 1]) {
                        temp = array[j]
                        array[j] = array[j + 1]
                        array[j + 1] = temp
                    }
                }
            }

            println("耗时：" + (System.currentTimeMillis() - start) + "ms")

        }
    }
}