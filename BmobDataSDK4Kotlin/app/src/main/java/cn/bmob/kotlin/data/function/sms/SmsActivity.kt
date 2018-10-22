package cn.bmob.kotlin.data.function.sms

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.kotlin.data.R
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.activity_sms.*

class SmsActivity : AppCompatActivity(), View.OnClickListener {
    var mContext: Context? = null

    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_send -> sendCode()
            R.id.btn_verify -> verifyCode()
        }
    }

    /**
     * 验证验证码
     */
    private fun verifyCode() {
        var phone = edt_phone.text.toString()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "输入手机号码", Toast.LENGTH_LONG).show()
            return
        }
        var code = edt_code.text.toString()
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "输入验证码", Toast.LENGTH_LONG).show()
            return
        }
        /**
         * bmob验证验证码
         */
        BmobSMS.verifySmsCode(phone,code,object :UpdateListener(){
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "验证成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "验证失败：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })

    }


    /**
     * 发送验证码
     */
    private fun sendCode() {
        var phone = edt_phone.text.toString()
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
        setContentView(R.layout.activity_sms)
        mContext = this
        btn_send.setOnClickListener(this)
        btn_verify.setOnClickListener(this)
    }
}