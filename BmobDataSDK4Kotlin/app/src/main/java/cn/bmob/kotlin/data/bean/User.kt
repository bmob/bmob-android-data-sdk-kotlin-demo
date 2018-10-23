package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import java.lang.Boolean.FALSE

/**
 *
 * bmob用户 继承自BmobUser
 * Created on 2018/10/11 14:18
 * @author zhangchaozhou
 */
class User : BmobUser() {
    /**
     * 用户昵称
     */
    var nickname: String? = null


    /**
     * 用户头像
     */
    var avatar : BmobFile?= null



}