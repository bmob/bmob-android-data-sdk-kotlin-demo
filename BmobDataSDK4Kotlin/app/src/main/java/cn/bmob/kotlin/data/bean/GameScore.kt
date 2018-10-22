package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile


class GameScore : BmobObject() {

    var playerName: String? = null
    var score: Int? = null
    var isPay: Boolean? = null
    var pic: BmobFile? = null
    @Transient
    var count: Int? = null


}