package cn.bmob.kotlin.data.correlation.post

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.Post
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


/**
 * 帖子适配器
 *
 */
class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private var mContext: Context
    private var posts: MutableList<Post>? = null

    constructor(context: Context?, posts: MutableList<Post>?) : super() {
        this.mContext = context!!
        this.posts = posts
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts?.size as Int
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts?.size as Int > position) {
            val post = posts?.get(position)
            Glide.with(mContext).load(post?.image).into(holder?.ivImage)
            Glide.with(mContext).load(post?.author!!.avatar).into(holder?.civAvatar)
            holder?.tvContent?.text = post?.content
            holder?.tvNickname?.text= post?.author?.nickname
            holder.itemView.setOnClickListener {
                //跳转到详情页
                mContext.startActivity(Intent(mContext,PostActivity::class.java))
            }
        }
    }


    class ViewHolder : RecyclerView.ViewHolder {
        var civAvatar: CircleImageView
        var tvContent: TextView
        var tvNickname: TextView
        var ivImage: ImageView

        constructor(itemView: View) : super(itemView) {
            civAvatar = itemView.findViewById(R.id.civ_avatar)
            tvContent = itemView.findViewById(R.id.tv_content)
            tvNickname = itemView.findViewById(R.id.tv_nickname)
            ivImage = itemView.findViewById(R.id.iv_image)
        }
    }
}