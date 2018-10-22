package cn.bmob.kotlin.data.crud.update

import android.os.Bundle
import android.util.Log
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.GameScore
import cn.bmob.v3.BmobBatch
import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BatchResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListListener
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.activity_update.*
import java.lang.Boolean.FALSE


/**
 * 修改数据
 *  * Created on 2018/10/22 14:18
 * @author zhangchaozhou
 */
class UpdateActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_update_one -> updateOne()
            R.id.btn_update_batch-> updateBatch()
        }
    }


    /**
     * 批量更新数据
     */
    private fun updateBatch() {
        val gameScores = ArrayList<BmobObject>()
        val gameScore1 = GameScore()
        gameScore1.objectId = "此处填写已存在的objectId"
        val gameScore2 = GameScore()
        gameScore2.objectId = "此处填写已存在的objectId"
        gameScore2.playerName="赵大"
        gameScore2.isPay= FALSE
        val gameScore3 = GameScore()
        gameScore3.objectId = "此处填写已存在的objectId"
        gameScore3.playerName="王二"

        gameScores.add(gameScore1)
        gameScores.add(gameScore2)
        gameScores.add(gameScore3)

        /**
         * 从3.5.0版本开始提供
         */
        BmobBatch().updateBatch(gameScores).doBatch(object : QueryListListener<BatchResult>() {

            override fun done(o: List<BatchResult>, ex: BmobException?) {
                    if (ex == null) {
                    for (i in o.indices) {
                        val result = o[i]
                        val ex = result.error
                        if (ex == null) {
                            Log.e("UPDATE","第" + i + "个数据批量更新成功：" + result.updatedAt)
                        } else {
                            Log.e("UPDATE","第" + i + "个数据批量更新失败：" + ex.message + "," + ex.errorCode)
                        }
                    }
                } else {
                        Log.e("UPDATE", "失败：" + ex.message + "," + ex.errorCode)
                }
            }
        })

    }


    /**
     * 修改单条数据
     */
    private fun updateOne() {
        val gameScore = GameScore()
        gameScore.score = 77
        gameScore.update("此处填写某条数据的objectId", object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("bmob", "更新成功")
                } else {
                    Log.i("bmob", "更新失败：" + e.message + "," + e.errorCode)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        btn_update_one.setOnClickListener(this)
        btn_update_batch.setOnClickListener(this)
    }
}