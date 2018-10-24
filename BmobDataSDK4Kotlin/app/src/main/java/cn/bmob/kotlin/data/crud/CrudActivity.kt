package cn.bmob.kotlin.data.crud

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_crud.*

/**
 * Created on 2018/10/11 14:25
 * @author zhangchaozhou
 */
class CrudActivity : BaseActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        var id: Int = v!!.id

        when (id) {
            R.id.btn_save -> saveObject()
            R.id.btn_to_delete -> queryAndDeleteObject()
            R.id.btn_to_update -> queryAndUpdateObject()
            R.id.btn_to_query -> queryObjects()
            R.id.btn_to_get -> getObject("此处替换为你需要查询的objectId")
        }
    }


    /**
     * bmob查询单条数据
     */
    private fun getObject(objectId: String?) {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.getObject(objectId, object : QueryListener<Post>() {
            override fun done(post: Post?, ex: BmobException?) {
                if (ex == null) {
                    Snackbar.make(btn_to_get, "查询成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_to_get, "查询失败：" + ex.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询并删除第一条数据
     */
    private fun queryAndDeleteObject() {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Post in posts) {
                            Log.e("Post", post.title)
                        }
                        /**
                         * 删除查询结果的第一条数据
                         */
                        if (posts.size > 0) {
                            deleteObject(posts[0].objectId)
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询并修改第一条数据
     */
    private fun queryAndUpdateObject() {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {
                if (ex == null) {
                    Snackbar.make(btn_to_get, "查询成功", Snackbar.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Post in posts) {
                            Log.e("Post", post.title)
                        }
                        /**
                         * 更新查询结果的第一条数据
                         */
                        if (posts.size > 0) {
                            updateObject(posts[0].objectId)
                        }
                    }
                } else {

                    Snackbar.make(btn_to_get, "查询失败：${ex.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * bmob 查询数据列表
     */
    private fun queryObjects() {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {

                if (ex == null) {
                    Snackbar.make(btn_to_get, "查询成功", Snackbar.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Post in posts) {
                            Log.e("Post", post.title)
                        }
                    }
                } else {
                    Snackbar.make(btn_to_get, "查询失败：${ex.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * bmob更新一条数据
     */
    private fun updateObject(objectId: String?) {
        var post = Post()
        post.objectId = objectId
        post.title = "更新名字+" + System.currentTimeMillis()
        post.update(object : UpdateListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Snackbar.make(btn_to_get, "更新成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_to_get, "更新失败：${ex.message}", Snackbar.LENGTH_LONG).show()
                }
            }

        })
    }


    /**
     * bmob删除一条数据
     */
    private fun deleteObject(objectId: String?) {
        var post = Post()
        post.objectId = objectId
        post.delete(object : UpdateListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * bmob新增一条数据
     */
    private fun saveObject() {
        var post = Post()
        post.title = "标题"
        post.content = "内容"
        post.author = BmobUser.getCurrentUser(User::class.java)
        post.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "保存成功:$objectId", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
        btn_save.setOnClickListener(this)
        btn_to_delete.setOnClickListener(this)
        btn_to_update.setOnClickListener(this)
        btn_to_query.setOnClickListener(this)
    }
}