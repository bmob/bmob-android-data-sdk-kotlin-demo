package cn.bmob.kotlin.data.crud.retrieve.bql

import android.os.Bundle
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobQueryResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SQLQueryListener

class BqlActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bql)
        mContext = this
    }


    private fun querySql() {
        val sql = "select distinct nick from AllenBean"
        val query = BmobQuery<Post>()
        query.doSQLQuery(sql, object : SQLQueryListener<Post>() {
            override fun done(result: BmobQueryResult<Post>, ex: BmobException?) {
                if (ex == null) {
                    val list = result.results as List<Post>
                    if (list != null && list.isNotEmpty()) {
                        for (i in list.indices) {
                            val p = list[i]
                        }
                        Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}