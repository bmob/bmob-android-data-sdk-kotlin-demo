package cn.bmob.kotlin.data.user.email

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.activity_email.*


class EmailActivity :BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when(id){
            R.id.btn_send_verify->sendEmailVerify()
            R.id.btn_send_reset->sendEmailReset()
            R.id.btn_login_email_password->loginEmailPassword()
        }
    }


    /**
     * 邮箱+密码登录
     */
    private fun loginEmailPassword() {
        val email = input_email.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(mContext,"请输入邮箱",Toast.LENGTH_LONG).show()
            return
        }

        val password = input_password.text.toString()
        if (TextUtils.isEmpty(password)){
            Toast.makeText(mContext,"请输入邮箱",Toast.LENGTH_LONG).show()
            return
        }
        BmobUser.loginByAccount(email, password, object : LogInListener<User>() {
            override fun done(user: User?, ex: BmobException) {
                if (ex == null) {
                    Log.e("loginByAccount","登录成功")
                } else {
                    Log.e("loginByAccount","登录失败："+ex.message)
                }
            }
        })
    }


    /**
     * 发送重置密码邮箱
     */
    private fun sendEmailReset() {
        val email = input_email.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(mContext,"请输入邮箱",Toast.LENGTH_LONG).show()
            return
        }
        BmobUser.resetPasswordByEmail(email, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.e("sendEmailReset","重置密码邮件成功，请到" + email + "邮箱中进行重置。")
                } else {
                    Log.e("sendEmailReset","失败:" + e.message)
                }
            }
        })
    }


    /**
     * 发送验证激活邮箱
     */
    private fun sendEmailVerify() {
        val email = input_email.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(mContext,"请输入邮箱",Toast.LENGTH_LONG).show()
            return
        }
        BmobUser.requestEmailVerify(email, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.e("sendEmailVerify","请求验证邮件成功，请到" + email + "邮箱中进行激活。")
                } else {
                    Log.e("sendEmailVerify","请求验证邮件失败:" + e.message)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        btn_send_verify.setOnClickListener(this)
        btn_send_reset.setOnClickListener(this)
        btn_login_email_password.setOnClickListener(this)
    }
}