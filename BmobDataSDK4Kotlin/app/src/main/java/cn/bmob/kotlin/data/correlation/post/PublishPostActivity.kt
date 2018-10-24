package cn.bmob.kotlin.data.correlation.post

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import kotlinx.android.synthetic.main.activity_publish_post.*

class PublishPostActivity : BaseActivity(), View.OnClickListener {


    override fun onClick(v: View?) {

        var id = v!!.id
        when (id) {
            R.id.btn_publish -> publishPost()
        }
    }


    /**
     * 发布帖子
     */
    private fun publishPost() {
        val content = input_post.text.toString()
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_LONG).show()
            return
        }

        val user = BmobUser.getCurrentUser(User::class.java)
        if(user==null) {
            Toast.makeText(mContext, "请先登录", Toast.LENGTH_LONG).show()
            return
        }
        val post = Post()
        post.content = content
        post.author = user
        post.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(mContext, "发布失败：${ex.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_post)

        btn_publish.setOnClickListener(this)
    }
}