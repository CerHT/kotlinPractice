package cht.anything

/**
 *
 * 测试所有数据类都在一个文件
 * @author chenhantao
 * @since 2019/12/17
 */
data class User(
    var name: String = "",
    var age: Int = 0
)

data class Account(
    var account: String,
    var password: String
)