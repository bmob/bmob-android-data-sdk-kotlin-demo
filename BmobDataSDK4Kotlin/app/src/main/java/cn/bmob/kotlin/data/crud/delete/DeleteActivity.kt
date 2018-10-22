package cn.bmob.kotlin.data.crud.delete

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.GameScore
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.activity_delete.*


/**
 * 删除数据
 *  * Created on 2018/10/22 14:18
 * @author zhangchaozhou
 */
class DeleteActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_delete_one -> deleteOne()
            R.id.btn_delete_column -> deleteColumn()
        }
    }

    private fun deleteColumn() {

        val gameScore = GameScore()
        gameScore.objectId = "此处填写已经存在的objectId"
        gameScore.remove("score")  // 删除GameScore对象中的score字段的值
        gameScore.update(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("bmob", "成功")
                } else {
                    Log.i("bmob", "失败：" + e.message + "," + e.errorCode)
                }
            }
        })
    }


    /**
     * 删除单条数据
     */
    private fun deleteOne() {
        val gameScore = GameScore()
        gameScore.objectId = "此处填写已经存在的objectId"
        gameScore.delete(object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("DELETE", "删除成功")
                } else {
                    Log.e("DELETE", "删除失败：" + e.message + "," + e.errorCode)
                }
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        btn_delete_one.setOnClickListener(this)
        btn_delete_column.setOnClickListener(this)
    }


}