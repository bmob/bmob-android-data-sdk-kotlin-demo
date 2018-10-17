package cn.bmob.kotlin.data

import android.app.Application
import cn.bmob.v3.Bmob

/**
 * Created on 2018/10/11 15:26
 * @author zhangchaozhou
 */
class KotlinDataApp :Application(){
    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(this,Constant.BMOB_APP_ID)
    }
}