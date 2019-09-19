package cht.tree.rbtree

/**
 *
 * 红黑树的一些操作
 * @author chenhantao
 * @since 2019/9/16
 */
class RBTree<T : Comparable<T>> {
    private var root: RBTNode<T>? = null

    companion object {
        private const val RED: Boolean = false
        private const val BLACK: Boolean = true
    }


    /**
     * 对红黑树的节点(a)进行左旋转
     *
     * 左旋示意图(对节点a进行左旋)：
     *      pa                              pa
     *     /                               /
     *    a                               b
     *   /  \      --(左旋)-.           / \                #
     *  la   b                          a  rb
     *     /   \                       /  \
     *    lb   rb                     la  lb
     *
     * @param a 左旋的节点
     */
    private fun leftRotate(a: RBTNode<T>) {
        // a的右节点
        val b: RBTNode<T>? = a.right

        // b的左节点变为a的右节点
        a.right = b?.left

        // 这里使用了kotlin的 ?. 判断符，表达式左侧任意为null，就跳过
        b?.left?.parent = a

        b?.parent = a.parent

        if (a.parent == null) {
            this.root = b
        } else {
            if (a.parent?.left == a) {
                a.parent?.left = b
            } else {
                a.parent?.right = b
            }
        }

        b?.left = a
        a.parent = b
    }

    /**
     * 对红黑树的节点(b)进行右旋转
     *
     * 右旋示意图(对节点b进行左旋)：
     *            pb                               pb
     *           /                                /
     *          b                                a
     *         /  \      --(右旋)-.            /  \                     #
     *        a   rb                           la   b
     *       / \                                   / \                   #
     *      la  ra                                ra  rb
     *
     * @param b 右旋的节点
     */
    private fun rightRotate(b: RBTNode<T>) {
        val a = b.left

        b.left = a?.right

        a?.right?.parent = b

        a?.parent = b.parent

        if (b.parent == null) {
            this.root = a
        } else {
            if (b.parent?.left == b) {
                b.parent?.left = a
            } else {
                b.parent?.right = a
            }
        }

        a?.right = b
        b.parent = a

    }

    // 一些简单的方法，从Java版参考，有些可能不需要
    private fun parentOf(node: RBTNode<T>?): RBTNode<T>? {
        return node?.parent
    }

    private fun isRed(node: RBTNode<T>?): Boolean {
        return node?.color == RED
    }

    private fun isBlack(node: RBTNode<T>?): Boolean {
        return node?.color == BLACK
    }

    private fun setRed(node: RBTNode<T>?) {
        node?.color = RED
    }

    private fun setBlack(node: RBTNode<T>?) {
        node?.color = BLACK
    }

    private fun search(key: T): RBTNode<T>? {
        return this.search(this.root, key)
    }
    private fun search(node: RBTNode<T>?, key: T): RBTNode<T>? {
        node ?: return null

        return node.key?.let {
            val temp = key.compareTo(it)
            return when {
                temp < 0 -> search(node.left, key)
                temp > 0 -> search(node.right, key)
                else -> node
            }
        }
    }
}