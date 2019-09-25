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
            // 其实这里的祖父接点是不会为null的，因为根节点不可能为红
            gParent = parentOf(parent)
            if (parent == gParent?.left) {
                val uncle: RBTNode<T>? = gParent.right
                // 1.叔节点为红色
                if (isRed(uncle)) {
                    setBlack(parent)
                    setRed(gParent)
                    setBlack(uncle)
                    node = gParent
                    continue
                }
                // 2.叔节点为黑色，且当前节点为右节点
                if (node == parent.right) {
                    leftRotate(parent)
                    val temp: RBTNode<T> = parent
                    parent = node
                    node = temp
                }
                // 3.叔节点为黑色，且当前节点为左节点
                setBlack(parent)
                setRed(gParent)
                rightRotate(gParent)
            } else {
                // 父节点为祖父节点的右节点
                val uncle: RBTNode<T>? = gParent?.left
                // 1.叔节点为红色
                if (isRed(uncle)) {
                    setBlack(uncle)
                    setBlack(parent)
                    setRed(gParent)
                    node = parent
                    continue
                }
                // 2.叔节点为黑色，且当前节点为左节点
                if (node == parent.left) {
                    rightRotate(parent)
                    val temp: RBTNode<T> = parent
                    parent = node
                    node = temp
                }

                // 3.叔节点为黑色，且当前接点为右节点
                setBlack(parent)
                setRed(gParent)
                // 这里其实不会为空
                leftRotate(gParent!!)
            }

            parent = parentOf(parent)
        }

        setBlack(this.root)
    }

    private fun remove(node: RBTNode<T>) {
        val child: RBTNode<T>?
        var parent: RBTNode<T>?
        val color: Boolean

        // 被删节点的左右子节点都不为空
        if (node.left != null && node.right != null) {
            // 用来替代被删除的节点，用该节点代替被删除的节点，然后再删除要删除的节点
            // 其实是不可能为空的，上面都判断过了，只是省下了下面的!!, !!看起来不太好
            var replace: RBTNode<T>?

            // 获取后续节点
            replace = node.right
            while (replace?.left != null) {
                replace = replace.left
            }
            // node不是根节点，即没有父节点的节点
            if (parentOf(node) != null) {
                if (parentOf(node)?.left == node) {
                    parentOf(node)?.left = replace
                } else {
                    parentOf(node)?.right = replace
                }
            } else {
                this.root = replace
            }
            // child是后继节点的右子节点，后继节点不存在左子节点
            child = replace?.right
            parent = parentOf(replace)

            // 保存取代节点的颜色
            color = replace?.color ?: BLACK

            // 被删除的节点是后继节点的父节点
            if (parent == node) {
                parent = replace
            } else {
                // child不为空
                child?.let { it.parent = parent }
                parent?.let { it.left = child }

                replace?.right = node.right
                node.right?.parent = replace
            }
            replace?.let {
                it.parent = node.parent
                it.color = node.color
                it.left = node.left
            }
            node.left?.parent = replace

            if (color == BLACK) {
                removeFix(child, parent)
            }

            return
        }
        child = if (node.left != null) {
            node.left
        } else {
            node.right
        }

        parent = node.parent
        color = node.color

        child?.let { it.parent = parent }

        // node 不是根节点
        if (parent != null) {
            if (parent.left == node) {
                parent.left = child
            } else {
                parent.right = child
            }
        } else {
            this.root = child
        }

        if (color == BLACK) {
            removeFix(child, parent)
        }
    }

    private fun removeFix(childNode: RBTNode<T>?, parentNode: RBTNode<T>?) {
        var node: RBTNode<T>? = childNode
        var parent: RBTNode<T>? = parentNode

        // node 的兄弟节点
        var otherNode: RBTNode<T>?

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent?.left == node) {
                otherNode = parent?.right
                if (isRed(otherNode)) {
                    // 1.node 的兄弟节点是红色
                    setBlack(otherNode)
                    setRed(parent)
                    parent?.let { leftRotate(it) }
                    otherNode = parent?.right
                }

                if ((otherNode?.left == null || isBlack(otherNode.left)) && (otherNode?.right == null) || isBlack(otherNode.right)) {
                    // 2.node的兄弟节点是黑色，且兄弟节点的两个子节点也是黑色
                    setRed(otherNode)
                    node = parent
                    parent = parentOf(node)
                } else {
                    if (otherNode.left == null || isBlack(otherNode.right)) {
                        // 3.node的兄弟节点是黑色，且兄弟节点的左节点是红色，右节点是黑色
                        setBlack(otherNode.left)
                        setRed(otherNode)
                        rightRotate(otherNode)
                        otherNode = parent?.right
                    }
                    // 4.node的兄弟节点是黑色，并且兄弟节点的右节点是红色，左节点任意颜色
                    // 吐槽一句，这种写法虽然要用Java来写需要写好多行，但是其实很多是不会为null的，写这种简直有毒，不这样写可以使用 !! 但是强迫症发作
                    parent?.color?.let { otherNode?.color = it }
                    setBlack(parent)
                    setBlack(otherNode?.right)
                    parent?.let { leftRotate(it) }
                    node = this.root
                    break
                }
            } else {
                otherNode = parent?.left
                if (isRed(otherNode)) {
                    // 1.node的兄弟节点是红色
                    setBlack(otherNode)
                }
            }
        }

    }

    // 一些简单的方法，从Java版参考，用kotlin的风格来一步完成
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