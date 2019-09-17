package cht.tree.rbtree

/**
 *
 * 节点类
 * @author chenhantao
 * @since 2019/9/9
 */
data class RBTNode<T : Comparable<T>>(
        private var color: Boolean,
        private var key: T?,
        var parent: RBTNode<T>?,
        var right: RBTNode<T>?,
        var left: RBTNode<T>?
)