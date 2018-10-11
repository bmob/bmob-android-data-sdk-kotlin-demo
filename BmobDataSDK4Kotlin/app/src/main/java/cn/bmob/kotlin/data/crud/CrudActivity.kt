package cn.bmob.kotlin.data.crud

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.Person
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import kotlinx.android.synthetic.main.activity_crud.*
import java.lang.Boolean.FALSE

/**
 * Created on 2018/10/11 14:25
 * @author zhangchaozhou
 */
class CrudActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id: Int = v!!.id

        when (id) {
            R.id.btn_save -> saveObject()
            R.id.btn_to_delete -> deleteObject()
            R.id.btn_to_update -> updateObject()
            R.id.btn_save -> queryObject()
        }
    }

    private fun queryObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun deleteObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * bmob新增一条数据
     */
    private fun saveObject() {
        var person = Person()
        person.name = "name"
        person.age = 11
        person.gender = FALSE
        person.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "保存成功:$objectId", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
        mContext = this
        btn_save.setOnClickListener(this)
        btn_to_delete.setOnClickListener(this)
        btn_to_update.setOnClickListener(this)
        btn_to_query.setOnClickListener(this)
    }
}