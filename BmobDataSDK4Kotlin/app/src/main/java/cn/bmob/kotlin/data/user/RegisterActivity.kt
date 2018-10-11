package cn.bmob.kotlin.data.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.User
import cn.bmob.kotlin.data.main.MainActivity
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created on 2018/10/11 14:29
 * @author zhangchaozhou
 */
class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null

    override fun onClick(v: View?) {
        var id: Int = v!!.id
        when (id) {
            R.id.btn_register -> register()
        }
    }


    /**
     * 注册
     */
    private fun register() {
        var username: String = edt_username_register.text.toString()
        var password: String = edt_password_register.text.toString()
        if (username.isNullOrBlank()) {
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_LONG).show()
            return
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_LONG).show()
            return
        }


        /**
         * bmob注册方法
         */
        var user = User()
        user.username = username
        user.setPassword(password)
        user.signUp(object : SaveListener<User>() {
            override fun done(currentUser: User?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(mContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mContext = this
        btn_register.setOnClickListener(this)
    }
}