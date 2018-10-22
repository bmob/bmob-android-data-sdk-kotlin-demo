package cn.bmob.kotlin.data.crud.retrieve

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import kotlinx.android.synthetic.main.activity_retrieve.*


/**
 * 查询数据
 *  * Created on 2018/10/22 14:18
 * @author zhangchaozhou
 */
class RetrieveActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when(id){
            R.id.btn_query_one->getObject("此处填写已经存在的objectId")
            R.id.btn_query_batch->queryObjects()
        }
    }

    /**
     * bmob 查询数据列表
     */
    private fun queryObjects() {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Post>() {
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (posts != null) {
                        for (post: Post in posts) {
                            Log.e("Post", post.title)
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * bmob查询单条数据
     */
    private fun getObject(objectId: String?) {
        var bmobQuery: BmobQuery<Post> = BmobQuery()
        bmobQuery.getObject(objectId, object : QueryListener<Post>() {
            override fun done(post: Post?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve)
        btn_query_one.setOnClickListener(this)
        btn_query_batch.setOnClickListener(this)
    }
}