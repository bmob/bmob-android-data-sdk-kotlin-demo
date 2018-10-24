package cn.bmob.kotlin.data.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * 基础活动类
 */
open class BaseActivity :AppCompatActivity() {
    var mContext : Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
    }



}

