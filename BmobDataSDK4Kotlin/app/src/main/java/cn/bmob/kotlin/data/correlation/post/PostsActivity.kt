package cn.bmob.kotlin.data.correlation.post

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {

    private var mContext: Context? = null
    private var postAdapter : PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        this.mContext = this
        queryPosts()
    }

    private fun queryPosts() {
        var bmobQuery = BmobQuery<Post>()
        bmobQuery.findObjects(object : FindListener<Post>() {
            @SuppressLint("WrongConstant")
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {
                if (ex == null) {
                    postAdapter = PostAdapter(mContext, posts)
                    var linearLayoutManager = LinearLayoutManager(mContext)
                    linearLayoutManager.orientation =VERTICAL
                    rv_post.layoutManager = linearLayoutManager
                    rv_post.setHasFixedSize(true)
                    rv_post.adapter= postAdapter
                } else {
                    Toast.makeText(mContext, "查询帖子列表出错：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}