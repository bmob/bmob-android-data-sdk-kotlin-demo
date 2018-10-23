package cn.bmob.kotlin.data.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.user.email.EmailActivity
import cn.bmob.kotlin.data.user.login.LoginActivity
import cn.bmob.kotlin.data.user.register.RegisterActivity
import cn.bmob.kotlin.data.user.sms.SmsSignUpActivity
import cn.bmob.kotlin.data.user.third.ThirdActivity
import kotlinx.android.synthetic.main.activity_user_manager.*

class UserManagerActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when(id){

            R.id.btn_account_password_register-> startActivity(Intent(mContext,RegisterActivity::class.java))
            R.id.btn_account_password_login-> startActivity(Intent(mContext,LoginActivity::class.java))
            R.id.btn_email-> startActivity(Intent(mContext,EmailActivity::class.java))
            R.id.btn_sms-> startActivity(Intent(mContext,SmsSignUpActivity::class.java))
            R.id.btn_third-> startActivity(Intent(mContext,ThirdActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_manager)
        btn_account_password_register.setOnClickListener(this)
        btn_account_password_login.setOnClickListener(this)
        btn_email.setOnClickListener(this)
        btn_sms.setOnClickListener(this)
        btn_third.setOnClickListener(this)
    }
}