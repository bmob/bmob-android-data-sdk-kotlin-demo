package cn.bmob.kotlin.data.data

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.data.array.ArrayActivity
import cn.bmob.kotlin.data.data.file.FileActivity
import cn.bmob.kotlin.data.data.installation.InstallationActivity
import cn.bmob.kotlin.data.data.location.LocationActivity
import kotlinx.android.synthetic.main.activity_data_type.*

class DataTypeActivity :BaseActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_array -> startActivity(Intent(mContext, ArrayActivity::class.java))
            R.id.btn_file -> startActivity(Intent(mContext, FileActivity::class.java))
            R.id.btn_installation -> startActivity(Intent(mContext, InstallationActivity::class.java))
            R.id.btn_location -> startActivity(Intent(mContext, LocationActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_type)
        btn_array.setOnClickListener(this)
        btn_file.setOnClickListener(this)
        btn_installation.setOnClickListener(this)
        btn_location.setOnClickListener(this)
    }
}