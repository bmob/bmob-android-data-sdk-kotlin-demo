package cn.bmob.kotlin.data.user.reset

import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_reset.*


/**
 * Created on 2018/10/24 10:59
 * @author zhangchaozhou
 */
class ResetActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        var id = v!!.id
        when (id) {
            R.id.btn_reset -> resetPassword()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)



        btn_reset.setOnClickListener(this)
    }

    /**
     * 修改密码，必须先登录
     */
    private fun resetPassword() {
        BmobUser.updateCurrentUserPassword("旧密码", "新密码", object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_reset, "密码修改成功，可以用新密码进行登录啦", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_reset, "密码修改失败：${e.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}