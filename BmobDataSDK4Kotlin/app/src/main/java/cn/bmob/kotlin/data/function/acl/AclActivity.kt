package cn.bmob.kotlin.data.function.acl

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.kotlin.data.bean.User
import cn.bmob.kotlin.data.bean.Wage
import cn.bmob.v3.BmobUser
import kotlinx.android.synthetic.main.activity_acl.*
import cn.bmob.v3.BmobACL
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.BmobRole




class AclActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        var id = v!!.id
        when (id) {
            R.id.btn_acl_access -> aclAccess()
            R.id.btn_role_access -> roleAccess()
        }

    }


    /**
     * BmobRole：角色访问管理权限
     */
    private fun roleAccess() {
        //创建公司某用户的工资对象
        val wage = Wage()
        wage.wage= 10000.0

        //这里创建四个用户对象，分别为老板、人事小张、出纳小谢和自己
        val boss: BmobUser? =null
        val hr_zhang: BmobUser? =null
        val hr_luo: BmobUser? =null
        val cashier_xie: BmobUser? =null
        val me: BmobUser? =null

        //创建HR和Cashier两个用户角色（这里为了举例BmobRole的使用，将这段代码写在这里，正常情况下放在员工管理界面会更合适）
        val hr = BmobRole("HR")
        val cashier = BmobRole("Cashier")

        //将hr_zhang和hr_luo归属到hr角色中
        hr.users.add(hr_zhang)
        hr.users.add(hr_luo)
        //保存到云端角色表中（web端可以查看Role表）
        hr.save(object :SaveListener<String> (){
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Log.e("ROLE", "保存成功")
                } else {
                    Log.e("ROLE", "保存失败：" + ex.message)
                }
            }
        })

        //将cashier_xie归属到cashier角色中
        cashier.users.add(cashier_xie)
        //保存到云端角色表中（web端可以查看Role表）
        cashier.save(object :SaveListener<String> (){
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Log.e("ROLE", "保存成功")
                } else {
                    Log.e("ROLE", "保存失败：" + ex.message)
                }
            }
        })

        //创建ACL对象
        val acl = BmobACL()
        acl.setReadAccess(boss, true) // 假设老板只有一个, 设置读权限
        acl.setReadAccess(me, true) // 给自己设置读权限
        acl.setRoleReadAccess(hr, true) // 给hr角色设置读权限
        acl.setRoleReadAccess(cashier, true) // 给cashier角色设置读权限

        acl.setWriteAccess(boss, true) // 设置老板拥有写权限
        acl.setRoleWriteAccess(hr, true) // 设置hr角色拥有写权限

        //设置工资对象的ACL
        wage.acl = acl
        wage.save(object :SaveListener<String>(){
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Log.e("ROLE", "保存成功")
                } else {
                    Log.e("ROLE", "保存失败：" + ex.message)
                }
            }
        })
    }


    /**
     * ACL控制一条数据的访问权限
     */
    private fun aclAccess() {
        val user = BmobUser.getCurrentUser(User::class.java)
        if (user==null){
            Toast.makeText(mContext,"请先登录",Toast.LENGTH_LONG).show()
            return
        }


        val acl = BmobACL()    //创建一个ACL对象
        acl.setPublicReadAccess(true)  // 设置所有人可读的权限
        acl.setWriteAccess(user, true)   // 设置当前用户可写的权限

        val post = Post()
        post.author = user
        post.title="ACL"
        post.content="ACL控制访问权限"
        post.save(object : SaveListener<String>(){
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Log.e("ACL", "保存成功")
                } else {
                    Log.e("ACL", "保存失败：" + ex.message)
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acl)

        btn_acl_access.setOnClickListener(this)
        btn_role_access.setOnClickListener(this)
    }
}