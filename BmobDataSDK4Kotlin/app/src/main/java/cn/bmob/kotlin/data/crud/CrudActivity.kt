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
import cn.bmob.v3.listener.*
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
            R.id.btn_to_get -> getObject("此处替换为你需要查询的objectId")
        }
    }


    /**
     * bmob查询单条数据
     */
    private fun getObject(objectId: String?) {
        var bmobQuery: BmobQuery<Person> = BmobQuery()
        bmobQuery.getObject(objectId, object : QueryListener<Person>() {
            override fun done(person: Person?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询并删除第一条数据
     */
    private fun queryAndDeleteObject() {
        var bmobQuery: BmobQuery<Person> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Person>() {
            override fun done(persons: MutableList<Person>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (persons != null) {
                        for (person: Person in persons) {
                            Log.e("Person", person.name)
                        }
                        /**
                         * 删除查询结果的第一条数据
                         */
                        if (persons.size > 0) {
                            deleteObject(persons[0].objectId)
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 查询并修改第一条数据
     */
    private fun queryAndUpdateObject() {
        var bmobQuery: BmobQuery<Person> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<Person>() {
            override fun done(persons: MutableList<Person>?, ex: BmobException?) {

                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                    if (persons != null) {
                        for (person: Person in persons) {
                            Log.e("Person", person.name)
                        }
                        /**
                         * 更新查询结果的第一条数据
                         */
                        if (persons.size > 0) {
                            updateObject(persons[0].objectId)
                        }
                    }
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * bmob 查询数据列表
     */
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


    /**
     * bmob更新一条数据
     */
    private fun updateObject(objectId: String?) {
        var person = Person()
        person.objectId = objectId
        person.name = "更新名字+" + System.currentTimeMillis()
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


    /**
     * bmob删除一条数据
     */
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