package cn.bmob.kotlin.data.user.sms

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.User
import cn.bmob.kotlin.data.user.login.LoginActivity
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.QueryListener
import kotlinx.android.synthetic.main.activity_sms_sign_up.*


/**
 * 短信注册
 */
class SmsSignUpActivity : AppCompatActivity(), View.OnClickListener {

    private var mContext: Context? = null
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_send_code -> sendCode()
            R.id.btn_signup -> signUp()
            R.id.link_login -> linkSign()
        }
    }

    private fun linkSign() {
        startActivity(Intent(mContext, LoginActivity::class.java))
        finish()
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    private fun signUp() {
        var phone = input_mobile.text.toString()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "输入手机号码", Toast.LENGTH_LONG).show()
            return
        }
        var code = input_code.text.toString()
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "输入验证码", Toast.LENGTH_LONG).show()
            return
        }
        var password = input_password.text.toString()
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "输入密码", Toast.LENGTH_LONG).show()
            return
        }

        var confirm = input_reEnterPassword.text.toString()
        if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(mContext, "确认密码", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirm) {
            Toast.makeText(mContext, "密码不匹配", Toast.LENGTH_LONG).show()
            return
        }

        var user = User()
        user.username = phone
        user.mobilePhoneNumber =phone
        user.mobilePhoneNumberVerified=true
        user.setPassword(password)
        BmobUser.signOrLoginByMobilePhone(phone, code, object : LogInListener<User>() {
            override fun done(user: User?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "短信验证码注册成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "短信验证码注册失败：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun sendCode() {
        var phone = input_mobile.text.toString()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "输入手机号码", Toast.LENGTH_LONG).show()
            return
        }
        /**
         * bmob发送验证码
         */
        BmobSMS.requestSMSCode(phone, "此处可填写控制台的短信模板名称，如果没有短信模板名称可填写空字符串使用默认模板", object : QueryListener<Int>() {
            override fun done(smsId: Int?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "发送成功：$smsId", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "发送成失败：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_sign_up)
        mContext = this
        btn_send_code.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        link_login.setOnClickListener(this)


        var typeface=  Typeface.createFromAsset(assets,"fonts/wending.ttf")
        link_login.typeface = typeface

    }

}
