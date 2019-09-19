package cht.tree.rbtree

/**
 *
 * 节点类
 * @author chenhantao
 * @since 2019/9/9
 */
data class RBTNode<T : Comparable<T>>(
        var color: Boolean,
        var key: T?,
        var parent: RBTNode<T>?,
        var right: RBTNode<T>?,
        var left: RBTNode<T>?
)