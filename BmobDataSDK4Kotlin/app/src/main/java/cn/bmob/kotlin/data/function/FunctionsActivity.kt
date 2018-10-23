package cn.bmob.kotlin.data.function

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.function.acl.AclActivity
import cn.bmob.kotlin.data.function.article.ArticleActivity
import cn.bmob.kotlin.data.function.realtime.RealtimeActivity
import cn.bmob.kotlin.data.function.sms.SmsActivity
import cn.bmob.kotlin.data.function.update.VersionUpdateActivity
import kotlinx.android.synthetic.main.activity_functions.*

class FunctionsActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_access_control -> startActivity(Intent(mContext, AclActivity::class.java))
            R.id.btn_article -> startActivity(Intent(mContext, ArticleActivity::class.java))
            R.id.btn_data_listen -> startActivity(Intent(mContext, RealtimeActivity::class.java))
            R.id.btn_sms_verify -> startActivity(Intent(mContext, SmsActivity::class.java))
            R.id.btn_version_update -> startActivity(Intent(mContext, VersionUpdateActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_functions)
        btn_access_control.setOnClickListener(this)
        btn_article.setOnClickListener(this)
        btn_data_listen.setOnClickListener(this)
        btn_sms_verify.setOnClickListener(this)
        btn_version_update.setOnClickListener(this)
    }
}