package cn.bmob.kotlin.data.guide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.crud.CrudActivity
import cn.bmob.kotlin.data.data.file.FileActivity
import cn.bmob.kotlin.data.data.location.LocationActivity
import cn.bmob.kotlin.data.function.sms.SmsActivity
import cn.bmob.kotlin.data.function.update.VersionUpdateActivity
import cn.bmob.kotlin.data.user.LoginActivity
import cn.bmob.kotlin.data.user.RegisterActivity
import cn.bmob.kotlin.data.user.sms.SmsSignUpActivity
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        var id: Int = v!!.id
        when (id) {
            R.id.btn_to_login -> toLogin()
            R.id.btn_to_register -> toRegister()
            R.id.btn_to_crud -> toCrud()
            R.id.btn_to_file -> toFile()
            R.id.btn_to_sms->toSms()
            R.id.btn_to_location->toLocation()
            R.id.btn_to_version_update ->toVersionUpdate()
            R.id.btn_to_sms_signup->toSmsSignUp()
        }
    }

    private fun toSmsSignUp() {
        startActivity(Intent(this, SmsSignUpActivity::class.java))
    }

    private fun toLocation() {
        startActivity(Intent(this, LocationActivity::class.java))
    }

    private fun toSms() {
        startActivity(Intent(this, SmsActivity::class.java))
    }

    private fun toFile() {
        startActivity(Intent(this, FileActivity::class.java))
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }


    private fun toRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }


    private fun toCrud() {
        startActivity(Intent(this, CrudActivity::class.java))
    }

    private fun toVersionUpdate() {
        startActivity(Intent(this, VersionUpdateActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        btn_to_login.setOnClickListener(this)
        btn_to_register.setOnClickListener(this)
        btn_to_crud.setOnClickListener(this)
        btn_to_file.setOnClickListener(this)
        btn_to_sms.setOnClickListener(this)
        btn_to_location.setOnClickListener(this)
        btn_to_version_update.setOnClickListener(this)
        btn_to_sms_signup.setOnClickListener(this)
    }


}
