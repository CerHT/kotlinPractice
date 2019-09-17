package cht.tree.rbtree

/**
 *
 * 红黑树的一些操作
 * @author chenhantao
 * @since 2019/9/16
 */
class RBTree<T : Comparable<T>> {
    private var root: RBTNode<T>? = null

    private val RED: Boolean = false
    private val BLACK: Boolean = true

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
        var right = a.right
        
    }

}