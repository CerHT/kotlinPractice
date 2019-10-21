package cht.queen

/**
 *
 * 八皇后
 * @author chenhantao
 * @since 2019/10/8
 */
class Queen(private val max_size: Int) {
    private val chessBoard: Array<IntArray> = Array(max_size) { kotlin.IntArray(max_size) }

    init {
        settleQueen(0)
    }

    private fun check(x: Int, y: Int): Boolean {
        for (i in 0 until y) {
            // 纵向检查
            if (chessBoard[x][i] == 1) {
                return false
            }
            // 检查左侧斜向
            if (x - 1 - i >= 0 && chessBoard[x - 1 - i][y - 1 - i] == 1) {
                return false
            }
            // 检查右侧斜向
            if (x + 1 + i < max_size && chessBoard[x + 1 + i][y - 1 - i] == 1) {
                return false
            }
        }
        return true
    }

    private fun settleQueen(y: Int): Boolean {
        // 行数超过最大，说明已经找到答案
        if (y == max_size) {
            return true
        }
        // 遍历当前行，逐一格子验证
        for (i in 0 until max_size) {
            // 为当前行清零，以免在回溯的时候出现脏数据
            for (x in 0 until max_size) {
                chessBoard[x][y] = 0
            }
            // 检查是否符合规则，如果符合，更改元素值并进一步递归
            if (check(i, y)) {
                chessBoard[i][y] = 1
                // 递归如果返回true，说明下层已找到解决办法，无需继续循环
                if (settleQueen(y + 1)) {
                    return true
                }
            }
        }
        return false
    }

    fun printQueen() {
        for (i in 0 until max_size) {
            for (j in 0 until max_size) {
                print("${chessBoard[i][j]} ")
            }
            println()
        }
    }
}
