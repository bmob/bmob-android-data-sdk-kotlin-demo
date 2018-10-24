package cn.bmob.kotlin.data.function.update

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.base.BaseActivity
import cn.bmob.v3.update.BmobUpdateAgent
import cn.bmob.v3.update.UpdateResponse
import cn.bmob.v3.update.UpdateStatus
import kotlinx.android.synthetic.main.activity_version_update.*
import java.util.*

class VersionUpdateActivity : BaseActivity(), View.OnClickListener {

    private val REQUEST_AUTO = 1001
    private val REQUEST_CHECK = 1002
    private val REQUEST_SILENT = 1003
    private val REQUEST_DELETE = 1004

    private var updateResponse: UpdateResponse? = null
    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_auto_update->checkStoragePermissions(REQUEST_AUTO)
            R.id.btn_check_update->checkStoragePermissions(REQUEST_CHECK)
            R.id.btn_download_silent->checkStoragePermissions(REQUEST_SILENT)
            R.id.btn_delete_apk->checkStoragePermissions(REQUEST_DELETE)
        }
    }


    /**
     * 检查权限
     *
     * @param requestCode
     */
    private fun checkStoragePermissions(requestCode: Int) {
        val permissions = ArrayList<String>()
        val permissionCheckWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        val permissionCheckRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissions.size > 0) {
            val missions = arrayOf<String>()
            ActivityCompat.requestPermissions(this, permissions.toArray(missions), requestCode)
        } else {
            when (requestCode) {
                REQUEST_AUTO -> BmobUpdateAgent.update(this)
                REQUEST_CHECK -> BmobUpdateAgent.forceUpdate(this)
                REQUEST_SILENT -> BmobUpdateAgent.silentUpdate(this)
                REQUEST_DELETE -> BmobUpdateAgent.deleteResponse(updateResponse)
            }
        }
    }

    /**
     * 检查授权结果
     *
     * @param grantResults
     * @return
     */
    private fun checkResults(grantResults: IntArray?): Boolean {
        if (grantResults == null || grantResults.isEmpty()) {
            return false
        }
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_AUTO -> if (checkResults(grantResults)) {
                BmobUpdateAgent.update(this)
            }
            REQUEST_CHECK -> if (checkResults(grantResults)) {
                BmobUpdateAgent.forceUpdate(this)
            }
            REQUEST_SILENT -> if (checkResults(grantResults)) {
                BmobUpdateAgent.silentUpdate(this)
            }
            REQUEST_DELETE -> if (checkResults(grantResults)) {
                BmobUpdateAgent.deleteResponse(updateResponse)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_update)

        mContext = this
        btn_auto_update.setOnClickListener(this)
        btn_check_update.setOnClickListener(this)
        btn_download_silent.setOnClickListener(this)
        btn_delete_apk.setOnClickListener(this)


        //TODO 初始化，当控制台表出现后，注释掉此句
        BmobUpdateAgent.initAppVersion()
        //TODO 设置仅WiFi环境更新
        BmobUpdateAgent.setUpdateOnlyWifi(false)
        //TODO 设置更新监听器
        BmobUpdateAgent.setUpdateListener { updateStatus, updateInfo ->
            val e = updateInfo.getException()
            if (e == null) {
                updateResponse = updateInfo
                Toast.makeText(mContext, "检测更新返回：" + updateStatus + "-" + updateInfo.version + "-" + updateInfo.path, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(mContext, "检测更新返回：" + e.message + "(" + e.errorCode + ")", Toast.LENGTH_SHORT).show()
            }
        }
        //TODO 设置对话框监听器
        BmobUpdateAgent.setDialogListener { status ->
            when (status) {
                UpdateStatus.Update -> Toast.makeText(mContext, "点击了立即更新按钮", Toast.LENGTH_SHORT).show()
                UpdateStatus.NotNow -> Toast.makeText(mContext, "点击了以后再说按钮", Toast.LENGTH_SHORT).show()
                UpdateStatus.Close -> Toast.makeText(mContext, "点击了对话框关闭按钮", Toast.LENGTH_SHORT).show()
            }
        }
    }
}