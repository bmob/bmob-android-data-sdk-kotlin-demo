package cn.bmob.kotlin.data.correlation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.kotlin.data.bean.User
import cn.bmob.kotlin.data.correlation.post.PostsActivity
import cn.bmob.kotlin.data.correlation.post.PublishPostActivity
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import kotlinx.android.synthetic.main.activity_correlation.*


class CorrelationActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_publish_post -> startActivity(Intent(mContext, PublishPostActivity::class.java))
            R.id.btn_posts -> startActivity(Intent(mContext, PostsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correlation)

        btn_publish_post.setOnClickListener(this)
        btn_posts.setOnClickListener(this)


        val user = BmobUser.getCurrentUser<User>(User::class.java)
        val query = BmobQuery<Post>()
        query.addWhereEqualTo("author", user)  // 查询当前用户的所有帖子
        query.order("-updatedAt")
        query.include("author")// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(object : FindListener<Post>() {

            override fun done(posts: List<Post>, e: BmobException?) {
                if (e == null) {
                    Log.i("bmob", "成功")
                } else {
                    Log.i("bmob", "失败：" + e.message)
                }
            }
        })
    }
}