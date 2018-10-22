package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.datatype.BmobRelation

class Post :BmobObject() {
    /**
     * 帖子标题
     */
     var title: String? = null

    /**
     * 帖子内容
     */
     var content: String? = null

    /**
     * 帖子发布者
     */
     var author: User? = null

    /**
     * 帖子图片
     */
     var image: BmobFile? = null

    /**
     * 一对多关系：用于存储喜欢该帖子的所有用户
     */
     var likes: BmobRelation? = null
}