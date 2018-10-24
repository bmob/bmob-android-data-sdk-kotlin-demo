package cn.bmob.kotlin.data.guide

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.correlation.CorrelationActivity
import cn.bmob.kotlin.data.crud.CrudActivity
import cn.bmob.kotlin.data.data.DataTypeActivity
import cn.bmob.kotlin.data.function.FunctionsActivity
import cn.bmob.kotlin.data.other.OtherOperationActivity
import cn.bmob.kotlin.data.user.UserManagerActivity
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id: Int = v!!.id
        when (id) {
            R.id.btn_user_manager -> startActivity(Intent(this, UserManagerActivity::class.java))
            R.id.btn_data_operation -> startActivity(Intent(this, CrudActivity::class.java))
            R.id.btn_correlation_operation -> startActivity(Intent(this, CorrelationActivity::class.java))
            R.id.btn_app_function -> startActivity(Intent(this, FunctionsActivity::class.java))
            R.id.btn_data_type -> startActivity(Intent(this, DataTypeActivity::class.java))
            R.id.btn_other_operation->startActivity(Intent(this, OtherOperationActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        btn_user_manager.setOnClickListener(this)
        btn_data_operation.setOnClickListener(this)
        btn_correlation_operation.setOnClickListener(this)
        btn_app_function.setOnClickListener(this)
        btn_data_type.setOnClickListener(this)
        btn_other_operation.setOnClickListener(this)
    }


}
