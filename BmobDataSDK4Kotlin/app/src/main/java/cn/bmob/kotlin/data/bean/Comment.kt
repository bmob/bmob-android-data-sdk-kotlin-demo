package cn.bmob.kotlin.data.bean

import cn.bmob.v3.BmobObject

class Comment :BmobObject() {
    /**
     * 评论内容
     */
     var content: String? = null

    /**
     * 评论的用户，一对一关系
     */
     var user: User? = null

    /**
     * 所评论的帖子，一对一关系
     */
     var post: Post? = null

}