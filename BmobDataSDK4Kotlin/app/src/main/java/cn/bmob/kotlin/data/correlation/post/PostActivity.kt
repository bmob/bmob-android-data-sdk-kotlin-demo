package cn.bmob.kotlin.data.correlation.post

import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.datatype.BmobRelation
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_post.*


/**
 * Created on 2018/10/23 20:44
 * @author zhangchaozhou
 */
class PostActivity : BaseActivity(), View.OnClickListener {


    var objectId: String? = null


    override fun onClick(v: View?) {

        var id = v!!.id
        when (id) {
            R.id.btn_like -> like(objectId)
            R.id.btn_likes -> likes(objectId)
            R.id.btn_unlike -> unlike(objectId)
        }
    }


    /**
     * 取消喜欢该帖子
     */
    private fun unlike(objectId: String?) {
        val post = Post()
        post.objectId = objectId
        val user = BmobUser.getCurrentUser<User>(User::class.java!!)
        val relation = BmobRelation()
        relation.remove(user)
        post.likes = relation
        post.update(object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_unlike, "关联关系删除成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_likes, "关联关系删除失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }

        })
    }


    /**
     * 查询喜欢该帖子的所有用户
     */
    private fun likes(objectId: String?) {
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表
        val query = BmobQuery<User>()
        val post = Post()
        post.objectId = objectId
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", BmobPointer(post))
        query.findObjects(object : FindListener<User>() {
            override fun done(users: List<User>, e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_likes, "查询成功：" + users.size, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_likes, "查询失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 喜欢该帖子
     */
    private fun like(objectId: String?) {
        val user = BmobUser.getCurrentUser<User>(User::class.java)
        if (user != null) {
            Snackbar.make(btn_like, "请先登录", Snackbar.LENGTH_LONG).show()
            return
        }
        val post = Post()
        post.objectId = objectId
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        val relation = BmobRelation()
        //将当前用户添加到多对多关联中
        relation.add(user)
        //多对多关联指向`post`的`likes`字段
        post.likes = relation
        post.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_like, "多对多关联添加成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_like, "多对多关联添加失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        btn_like.setOnClickListener(this)

        objectId = intent.getStringExtra("post")
    }
}