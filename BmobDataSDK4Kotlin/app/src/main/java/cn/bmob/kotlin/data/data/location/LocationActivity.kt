package cn.bmob.kotlin.data.data.location

import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.User
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobGeoPoint
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_shortest -> queryShortest()
            R.id.btn_distance -> queryDistance()
            R.id.btn_rectangle -> queryRectangle()
        }
    }


    /**
     * 查询矩形范围内的用户
     */
    private fun queryRectangle() {
        val query = BmobQuery<User>()
        val southwestOfSF = BmobGeoPoint(112.934755, 24.52065)
        val northeastOfSF = BmobGeoPoint(116.627623, 40.143687)
        query.addWhereWithinGeoBox("location", southwestOfSF, northeastOfSF)
        query.findObjects(object : FindListener<User>() {
            override fun done(persons: List<User>, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "查询失败：${ex.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询指定距离范围内的用户
     */
    private fun queryDistance() {
        val query = BmobQuery<User>()
        val southwestOfSF = BmobGeoPoint(112.934755, 24.52065)
        //查询指定坐标指定半径内的用户
        query.addWhereWithinRadians("location", southwestOfSF, 10.0)
        //查询指定坐标指定公里范围内的用户
//        query.addWhereWithinKilometers("location", southwestOfSF, 10.0)
        //查询指定坐标指定英里范围内的用户
//        query.addWhereWithinMiles("location", southwestOfSF, 10.0)
        query.findObjects(object : FindListener<User>() {
            override fun done(persons: List<User>, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "查询失败：${ex.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询最接近某个坐标的用户
     */
    private fun queryShortest() {
        val query = BmobQuery<User>()
        val location = BmobGeoPoint(112.934755, 24.52065)
        query.addWhereNear("location", location)
        query.setLimit(10)
        query.findObjects(object : FindListener<User>() {
            override fun done(persons: List<User>, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "查询成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "查询失败：${ex.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        btn_shortest.setOnClickListener(this)
        btn_distance.setOnClickListener(this)
        btn_rectangle.setOnClickListener(this)
    }
}