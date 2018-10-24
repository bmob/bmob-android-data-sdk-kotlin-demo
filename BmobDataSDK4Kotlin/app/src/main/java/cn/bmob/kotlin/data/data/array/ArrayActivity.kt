package cn.bmob.kotlin.data.data.array

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_array.*
import java.util.*



class ArrayActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_array_add -> addArray()
            R.id.btn_array_query -> queryArray()
            R.id.btn_array_update -> updateArray()
            R.id.btn_array_delete -> deleteArray()
        }
    }


    /**
     * 删除数组
     */
    private fun deleteArray() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_array_add, "请先登录", Snackbar.LENGTH_LONG).show()
            return
        }
        user.removeAll("hobbies", Arrays.asList("阅读", "唱歌", "游泳"))
        user.update(object : UpdateListener() {

            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("bmob", "成功")
                } else {
                    Log.i("bmob", "失败：" + e.message)
                }
            }
        })
    }


    /**
     * 更新数组
     */
    private fun updateArray() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_array_add, "请先登录", Snackbar.LENGTH_LONG).show()
            return
        }
        user.setValue("hobbies.0", "爬山")
        user.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_array_add, "更新成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_array_add, "更新失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun queryArray() {


    }


    /**
     * 添加数组
     */
    private fun addArray() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user == null) {
            Snackbar.make(btn_array_add, "请先登录", Snackbar.LENGTH_LONG).show()
            return
        }
        user.add("hobbies", "唱歌")
        user.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Snackbar.make(btn_array_add, "更新成功", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_array_add, "更新失败：" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array)
        btn_array_add.setOnClickListener(this)
        btn_array_query.setOnClickListener(this)
        btn_array_delete.setOnClickListener(this)
        btn_array_update.setOnClickListener(this)
    }
}