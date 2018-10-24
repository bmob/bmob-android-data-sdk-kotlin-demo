package cn.bmob.kotlin.data.other

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.v3.Bmob
import cn.bmob.v3.datatype.BmobTableSchema
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.QueryListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_other_operation.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created on 2018/10/24 10:29
 * @author zhangchaozhou
 */
class OtherOperationActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        var id: Int = v!!.id
        when (id) {
            R.id.btn_get_server_time -> getBmobServerTime()
            R.id.btn_get_table -> getTable("_User")
            R.id.btn_get_all_table -> getAllTable()
        }
    }


    /**
     * 获取所有表结构
     */
    private fun getAllTable() {

        Bmob.getAllTableSchema(object : QueryListListener<BmobTableSchema>() {

            override fun done(schemas: List<BmobTableSchema>?, ex: BmobException?) {
                if (ex == null && schemas != null && schemas.isNotEmpty()) {
                    Log.i("bmob", "获取所有表结构信息成功")
                } else {
                    Log.i("bmob", "获取所有表结构信息失败：" + ex!!.localizedMessage + "(" + ex.errorCode + ")")
                }
            }
        })
    }


    /**
     * 获取某张表的表结构
     */
    private fun getTable(table: String?) {
        Bmob.getTableSchema(table, object : QueryListener<BmobTableSchema>() {

            override fun done(schema: BmobTableSchema, ex: BmobException?) {
                if (ex == null) {
                    Log.i("bmob", "获取指定表的表结构信息成功：" + schema.className + "-" + schema.fields.toString())
                } else {
                    Log.i("bmob", "获取指定表的表结构信息失败:" + ex.localizedMessage + "(" + ex.errorCode + ")")
                }
            }
        })
    }


    /**
     * 获取服务器时间
     */
    private fun getBmobServerTime() {
        Bmob.getServerTime(object : QueryListener<Long>() {
            override fun done(time: Long, e: BmobException?) {
                if (e == null) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val times = formatter.format(Date(time * 1000L))
                    Snackbar.make(btn_get_server_time, "当前服务器时间为:$times", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(btn_get_server_time, "获取服务器时间失败:" + e.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_operation)
        btn_get_server_time.setOnClickListener(this)
        btn_get_table.setOnClickListener(this)
        btn_get_all_table.setOnClickListener(this)
    }
}