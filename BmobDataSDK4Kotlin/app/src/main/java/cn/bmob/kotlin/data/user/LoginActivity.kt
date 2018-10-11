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
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created on 2018/10/11 14:27
 * @author zhangchaozhou
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * var 变量
     * val 常量
     */

    private var mContext: Context? = null


    override fun onClick(v: View?) {
        val id = v!!.id
        when (id) {
            R.id.btn_login -> login()
        }
    }


    /**
     * 登录
     */
    private fun login() {
        var username: String = edt_username_login.text.toString()
        var password: String = edt_password_login.text.toString()

        if (username.isNullOrBlank()) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show()
            return
        }

        if (password.isNullOrBlank()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show()
            return
        }

        /**
         * bmob登录方法
         */
        var user = User()
        user.username = username
        user.setPassword(password)
        user.login(object : SaveListener<User>() {
            override fun done(currentUser: User?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(mContext,MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }

            }
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        btn_login.setOnClickListener(this)
    }
}