package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobObject
import java.lang.Boolean.FALSE

/**
 * bmob数据 继承自BmobObject
 * Created on 2018/10/11 16:19
 * @author zhangchaozhou
 */
class Person : BmobObject() {
     var name: String? = null
     var age: Int = 0
     var gender: Boolean? = FALSE



}