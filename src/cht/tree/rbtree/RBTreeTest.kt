package cht.tree.rbtree

/**
 *
 * 红黑树测试
 * @author chenhantao
 * @since 2019/9/30
 */

fun main() {
    val start = System.currentTimeMillis()
    val a = intArrayOf(11, 3, 20, 21, 44, 80, 311, 32, 1, 500)

    print("原始数据: ")
    a.forEach { print("$it ") }
    println()

    val tree = RBTree<Int>()
    a.forEach { tree.insert(it) }
    tree.order()

    tree.remove(3)
    tree.order()

    print("耗时: ${System.currentTimeMillis() - start}")
}