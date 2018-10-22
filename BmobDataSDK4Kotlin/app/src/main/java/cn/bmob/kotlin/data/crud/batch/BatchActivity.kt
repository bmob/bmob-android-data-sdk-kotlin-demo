package cn.bmob.kotlin.data.crud.batch

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
import kotlinx.android.synthetic.main.activity_batch.*
import java.lang.Boolean


class BatchActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        var id = v!!.id
        when (id) {
            R.id.btn_batch -> doBatch()
            R.id.btn_batch_add -> createBatch()
            R.id.btn_batch_update -> updateBatch()
            R.id.btn_batch_delete -> deleteBatch()
        }
    }

    private fun deleteBatch() {
        val gameScores = ArrayList<BmobObject>()
        val gameScore1 = GameScore()
        gameScore1.objectId = "此处填写已存在的objectId"
        val gameScore2 = GameScore()
        gameScore2.objectId = "此处填写已存在的objectId"
        val gameScore3 = GameScore()
        gameScore3.objectId = "此处填写已存在的objectId"

        gameScores.add(gameScore1)
        gameScores.add(gameScore2)
        gameScores.add(gameScore3)


        BmobBatch().deleteBatch(gameScores).doBatch(object : QueryListListener<BatchResult>() {

            override fun done(o: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in o.indices) {
                        val result = o[i]
                        val ex = result.error
                        if (ex == null) {
                            Log.e("DELETE BATCH","第" + i + "个数据批量删除成功")
                        } else {
                            Log.e("DELETE BATCH","第" + i + "个数据批量删除失败：" + ex.message + "," + ex.errorCode)
                        }
                    }
                } else {
                    Log.e("DELETE BATCH","失败：" + e.message + "," + e.errorCode)
                }
            }
        })
    }

    /**
     * 批量新增数据
     */
    private fun createBatch() {
        val gameScores = ArrayList<BmobObject>()
        for (i in 0..2) {
            val gameScore = GameScore()
            gameScore.playerName= "运动员$i"
            gameScores.add(gameScore)
        }

        /**
         * 3.5.0版本开始提供
         */
        BmobBatch().insertBatch(gameScores).doBatch(object : QueryListListener<BatchResult>() {
            override fun done(o: List<BatchResult>, e: BmobException?) {
                if (e == null) {
                    for (i in o.indices) {
                        val result = o[i]
                        val ex = result.error
                        if (ex == null) {
                            Log.e("CREATE BATCH","第" + i + "个数据批量添加成功：" + result.createdAt + "," + result.objectId + "," + result.updatedAt)
                        } else {
                            Log.e("CREATE BATCH","第" + i + "个数据批量添加失败：" + ex.message + "," + ex.errorCode)
                        }
                    }
                } else {
                    Log.e("CREATE BATCH", "失败：" + e.message + "," + e.errorCode)
                }
            }
        })
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
        gameScore2.playerName = "赵大"
        gameScore2.isPay = Boolean.FALSE
        val gameScore3 = GameScore()
        gameScore3.objectId = "此处填写已存在的objectId"
        gameScore3.playerName = "王二"

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
                            Log.e("UPDATE", "第" + i + "个数据批量更新成功：" + result.updatedAt)
                        } else {
                            Log.e("UPDATE", "第" + i + "个数据批量更新失败：" + ex.message + "," + ex.errorCode)
                        }
                    }
                } else {
                    Log.e("UPDATE", "失败：" + ex.message + "," + ex.errorCode)
                }
            }
        })

    }
    private fun doBatch() {

        val batch = BmobBatch()


        //批量添加
        val gameScores = ArrayList<BmobObject>()
        val gameScore = GameScore()
        gameScore.playerName = "张三"
        gameScores.add(gameScore)
        batch.insertBatch(gameScores)

        //批量更新
        val gameScores1 = ArrayList<BmobObject>()
        val gameScore1 = GameScore()
        gameScore1.objectId = "此处填写已经存在的objectId"
        gameScore1.playerName = "李四"
        gameScores1.add(gameScore1)
        batch.updateBatch(gameScores1)

        //批量删除
        val gameScores2 = ArrayList<BmobObject>()
        val gameScore2 = GameScore()
        gameScore2.objectId = "此处填写已经存在的objectId"
        gameScores2.add(gameScore2)
        batch.deleteBatch(gameScores2)

        //执行批量操作
        batch.doBatch(object : QueryListListener<BatchResult>() {

            override fun done(results: List<BatchResult>, ex: BmobException?) {
                if (ex == null) {
                    //返回结果的results和上面提交的顺序是一样的，请一一对应
                    for (i in results.indices) {
                        val result = results[i]
                        if (result.isSuccess) {//只有批量添加才返回objectId
                            Log.e("BATCH", "第" + i + "个成功：" + result.objectId + "," + result.updatedAt)
                        } else {
                            val error = result.error
                            Log.e("BATCH", "第" + i + "个失败：" + error.errorCode + "," + error.message)
                        }
                    }
                } else {
                    Log.e("BATCH", "失败：" + ex.message + "," + ex.errorCode)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batch)

        btn_batch.setOnClickListener(this)
        btn_batch_add.setOnClickListener(this)
        btn_batch_update.setOnClickListener(this)
        btn_batch_delete.setOnClickListener(this)
    }
}