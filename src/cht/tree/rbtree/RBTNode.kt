package cht.tree.rbtree

/**
 *
 * TODO
 * @author chenhantao
 * @since 2019/9/9
 */
class RBTNode<T : Comparable<T>> {
    var color: Boolean = true
    var key: T? = null

    var parent: RBTNode<T>? = null
    var right: RBTNode<T>? = null
    var left: RBTNode<T>? = null;
    

}