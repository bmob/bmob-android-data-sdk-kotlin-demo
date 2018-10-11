package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobUser
import java.lang.Boolean.FALSE

/**
 *
 * bmob用户 继承自BmobUser
 * Created on 2018/10/11 14:18
 * @author zhangchaozhou
 */
class User : BmobUser() {
    var name: String? = null
    var age: Int = 0
    var gender: Boolean? = FALSE


}