package cn.bmob.kotlin.data.crud

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.Person
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import kotlinx.android.synthetic.main.activity_crud.*
import java.lang.Boolean.FALSE

/**
 * Created on 2018/10/11 14:25
 * @author zhangchaozhou
 */
class CrudActivity : AppCompatActivity(), View.OnClickListener {

    private var mContext: Context? = null

    override fun onClick(v: View?) {
        var id: Int = v!!.id

        when (id) {
            R.id.btn_save -> saveObject()
            R.id.btn_to_delete -> queryAndDeleteObject()
            R.id.btn_to_update -> queryAndUpdateObject()
            R.id.btn_to_query -> queryObjects()
        }
    }

    private fun queryAndDeleteObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    private fun queryAndUpdateObject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    private fun queryObjects() {
        var bmobQuery: BmobQuery<Person> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Person>() {
            override fun done(persons: MutableList<Person>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (persons != null) {
                        for (person: Person in persons) {
                            Log.e("Person", person.name)
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }

        })


    }

    private fun updateObject(objectId: String?) {
        var person = Person()
        person.objectId = objectId
        person.update(object : UpdateListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private fun deleteObject(objectId: String?) {
        var person = Person()
        person.objectId = objectId
        person.delete(object : UpdateListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }

        })
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