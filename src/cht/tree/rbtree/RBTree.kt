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

        // 左旋后，a的父节点变为b的父节点
        b?.parent = a.parent

        // a有可能是根节点，因为左旋后，b可能会变成根节点
        if (a.parent == null) {
            this.root = b
        } else {
            // 变更后，需要更改父节点的子节点信息，即 pa的左右子节点
            if (a.parent?.left == a) {
                a.parent?.left = b
            } else {
                a.parent?.right = b
            }
        }

        // a变为b的左节点
        b?.left = a
        // b变为a的父节点
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
        // 获取b的左节点a
        val a = b.left

        // b的左节点变为a的右节点
        b.left = a?.right
        // ra不为空的话，父节点变为b
        a?.right?.parent = b

        // 右旋后，b的父节点变为a的父节点
        a?.parent = b.parent

        // b有可能是根节点
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

    private fun insert(node: RBTNode<T>) {
        var cmp: Int
        var temp: RBTNode<T>? = null
        var sign: RBTNode<T>? = this.root

        // 1. 从根节点找起，将红黑树当做二叉查找树，将节点添加到二叉查找树中
        while (sign != null) {
            temp = sign
            cmp = sign.key?.let { node.key?.compareTo(it) } ?: 0
            // 如果node < root，则取更小的来比较
            sign = if (cmp < 0) {
                sign.right
            } else {
                sign.left
            }
        }
        // 对比过后，找到node的位置,将node的设置在某个节点下
        node.parent = temp

        // 把node设置为别的节点的子节点
        if (temp != null) {
            cmp = temp.key?.let { node.key?.compareTo(it) } ?: 0
            if (cmp < 0) {
                temp.left = node
            } else {
                temp.right = node
            }
        } else {
            this.root = node
        }
        // 2. 设置节点为红色
        node.color = RED

        // 3. 修正为二叉查找树
        insertFix(node)
    }

    private fun insertFix(rbtNode: RBTNode<T>) {
        var node = rbtNode

        var gParent: RBTNode<T>?

        // 若父节点存在，且父节点为红色
        var parent: RBTNode<T>? = parentOf(node)
        while (parent != null && isRed(parent)) {
            gParent = parentOf(parent)
            if (parent == gParent)

        }


    }

    // 一些简单的方法，从Java版参考，有些可能不需要
    private fun minNode(node: RBTNode<T>?): RBTNode<T>? {
        node ?: return null
        var temp = node

        while (temp?.left != null) {
            temp = temp.left
        }

        return temp
    }

    private fun parentOf(node: RBTNode<T>?): RBTNode<T>? = node?.parent
    
    private fun isRed(node: RBTNode<T>?): Boolean = node?.color == RED

    private fun isBlack(node: RBTNode<T>?): Boolean = node?.color == BLACK


    private fun setRed(node: RBTNode<T>?) {
        node?.color = RED
    }

    private fun setBlack(node: RBTNode<T>?) {
        node?.color = BLACK
    }

    private fun search(key: T): RBTNode<T>? = this.search(this.root, key)

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