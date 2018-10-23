package cn.bmob.kotlin.data.function.realtime

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.v3.BmobRealTimeData
import cn.bmob.v3.listener.ValueEventListener
import kotlinx.android.synthetic.main.activity_realtime.*
import org.json.JSONObject


/**
 * Created on 2018/10/12 09:23
 * @author zhangchaozhou
 */
class RealtimeActivity :BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
       var id = v!!.id
       when(id){
            R.id.btn_start_listen -> startListen()
       }
    }

    private fun startListen() {
        val rtd = BmobRealTimeData()
        rtd.start(object : ValueEventListener {
            override fun onDataChange(data: JSONObject) {
                Log.d("onDataChange", "(" + data.optString("action") + ")" + "数据：" + data)
                val action = data.optString("action")
                if (action == BmobRealTimeData.ACTION_UPDATETABLE) {
                    //TODO 如果监听表更新
                    val data = data.optJSONObject("data")
                    Toast.makeText(mContext, "监听到更新：" + data.optString("name") + "-" + data.optString("content"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onConnectCompleted(ex: Exception) {
                if (ex == null) {
                    Log.i("onConnectCompleted", "连接情况：" + if (rtd.isConnected) "已连接" else "未连接")
                    if (rtd.isConnected) {
                        //TODO 如果已连接，设置监听动作为：监听Chat表的更新
                        rtd.subTableUpdate("Chat")
                    }
                } else {
                    Log.e("onConnectCompleted", "连接出错：" + ex.message)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime)

        btn_start_listen.setOnClickListener(this)
    }
}