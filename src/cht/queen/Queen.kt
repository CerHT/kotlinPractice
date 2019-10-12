package cht.queen

/**
 *
 * 八皇后
 * @author chenhantao
 * @since 2019/10/8
 */
class Queen(private val max_size: Int) {
    private val chessBoard: Array<IntArray> = Array(max_size) { kotlin.IntArray(max_size) }

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

    fun settleQueen(y: Int): Boolean {
        // 行数超过最大，说明已经找到答案
        if (y == max_size) {
            return true
        }
        // 遍历当前行，逐一格子验证
    }
}
