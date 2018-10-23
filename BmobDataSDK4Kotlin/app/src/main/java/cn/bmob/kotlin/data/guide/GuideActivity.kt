package cn.bmob.kotlin.data.guide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.correlation.CorrelationActivity
import cn.bmob.kotlin.data.correlation.post.PostsActivity
import cn.bmob.kotlin.data.crud.CrudActivity
import cn.bmob.kotlin.data.data.DataTypeActivity
import cn.bmob.kotlin.data.data.file.FileActivity
import cn.bmob.kotlin.data.data.location.LocationActivity
import cn.bmob.kotlin.data.function.FunctionsActivity
import cn.bmob.kotlin.data.function.sms.SmsActivity
import cn.bmob.kotlin.data.function.update.VersionUpdateActivity
import cn.bmob.kotlin.data.user.UserManagerActivity
import cn.bmob.kotlin.data.user.login.LoginActivity
import cn.bmob.kotlin.data.user.register.RegisterActivity
import cn.bmob.kotlin.data.user.sms.SmsSignUpActivity
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id: Int = v!!.id
        when (id) {
            R.id.btn_user_manager -> startActivity(Intent(this, UserManagerActivity::class.java))
            R.id.btn_data_operation -> startActivity(Intent(this, CrudActivity::class.java))
            R.id.btn_correlation_operation -> startActivity(Intent(this, CorrelationActivity::class.java))
            R.id.btn_app_function -> startActivity(Intent(this, FunctionsActivity::class.java))
            R.id.btn_data_type -> startActivity(Intent(this, DataTypeActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        btn_user_manager.setOnClickListener(this)
        btn_data_operation.setOnClickListener(this)
        btn_correlation_operation.setOnClickListener(this)
        btn_app_function.setOnClickListener(this)
        btn_data_type.setOnClickListener(this)
    }


}
