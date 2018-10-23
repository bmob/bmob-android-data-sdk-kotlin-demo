package cn.bmob.kotlin.data.user.third

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import kotlinx.android.synthetic.main.activity_third.*
import org.json.JSONObject
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.BmobUser.BmobThirdUserAuth



class ThirdActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_login_sign_up -> thirdLoginSignup("账号平台","接口调用凭证","有效时间","用户ID")
            R.id.btn_associate-> associateThird("账号平台","接口调用凭证","有效时间","用户ID")
            R.id.btn_un_associate-> unAssociateThird("账号平台")
        }
    }

    /**
     * 取消关联
     */
    private fun unAssociateThird(snsType: String) {
        var currentUser: User? = BmobUser.getCurrentUser(User::class.java)
        currentUser?.dissociateAuthData(snsType, object : UpdateListener() {

            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Log.e("dissociateAuthData", "取消关联成功")
                } else {
                    Log.e("dissociateAuthData", "取消关联失败：" + ex.message)
                }
            }
        })
    }

    /**
     * 关联第三方账号
     */
    private fun associateThird(snsType: String, accessToken: String, expiresIn: String, userId: String) {
        val authInfo = BmobThirdUserAuth(snsType, accessToken, expiresIn, userId)
        BmobUser.associateWithAuthData(authInfo, object : UpdateListener() {

            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Log.e("associateWithAuthData", "关联成功")
                } else {
                    Log.e("associateWithAuthData", "关联失败：" + ex.message)
                }
            }
        })
    }


    /**
     * 1、snsType:只能是三种取值中的一种：weibo、qq、weixin
     * 2、accessToken：接口调用凭证
     * 3、expiresIn：access_token的有效时间
     * 4、userId:用户身份的唯一标识，对应微博授权信息中的uid,对应qq和微信授权信息中的openid
     */
    private fun thirdLoginSignup(snsType: String, accessToken: String, expiresIn: String, userId: String) {
        val authInfo = BmobUser.BmobThirdUserAuth(snsType, accessToken, expiresIn, userId)
        BmobUser.loginWithAuthData(authInfo, object : LogInListener<JSONObject>() {
            override fun done(data: JSONObject?, ex: BmobException?) {
                if (ex == null) {
                    Log.e("loginWithAuthData", "登录注册成功")
                } else {
                    Log.e("loginWithAuthData", "登录注册失败：" + ex.message)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)


        btn_login_sign_up.setOnClickListener(this)
    }
}