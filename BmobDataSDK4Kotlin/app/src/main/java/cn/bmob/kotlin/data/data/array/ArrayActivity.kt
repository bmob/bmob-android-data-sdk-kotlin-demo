package cn.bmob.kotlin.data.data.array

import android.os.Bundle
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import kotlinx.android.synthetic.main.activity_array.*

class ArrayActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id = v!!.id
        when(id){
            R.id.btn_array_add-> addArray()
            R.id.btn_array_query-> queryArray()
            R.id.btn_array_update-> updateArray()
            R.id.btn_array_delete-> deleteArray()
        }
    }

    private fun deleteArray() {


    }

    private fun updateArray() {


    }

    private fun queryArray() {


    }

    private fun addArray() {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array)
        btn_array_add.setOnClickListener(this)
        btn_array_query.setOnClickListener(this)
        btn_array_delete.setOnClickListener(this)
        btn_array_update.setOnClickListener(this)
    }
}