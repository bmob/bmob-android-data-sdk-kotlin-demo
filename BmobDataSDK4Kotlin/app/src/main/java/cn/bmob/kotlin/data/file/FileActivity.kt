package cn.bmob.kotlin.data.file

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UploadFileListener
import kotlinx.android.synthetic.main.activity_file.*
import java.io.File




/**
 * Created on 2018/10/11 17:03
 * @author zhangchaozhou
 */
class FileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mContext: Context
    private val REQUEST_WRITE_CODE: Int = 1001
    private val REQUEST_SELECT_CODE: Int = 1002


    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_upload_single -> chooseSingleFile()
            R.id.btn_upload_multi -> uploadMulti()
            R.id.btn_delete_single -> deleteSingle()
        }
    }

    private fun deleteSingle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    private fun uploadMulti() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }


    /**
     * 通过文件路径上传单个文件
     */
    private fun uploadSingle(path: String?) {
        var file = File(path)
        var bmobFile = BmobFile(file)
        bmobFile.upload(object : UploadFileListener() {
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mContext, "上传失败："+ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * 通过系统文件管理器选择单个文件
     */
    private fun chooseSingleFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_SELECT_CODE)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "no file manager", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_CODE && resultCode == RESULT_OK) {
            val uri = data!!.data
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = managedQuery(uri, projection, null, null, null)
            val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val path = cursor.getString(index)
            uploadSingle(path)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        mContext = this
        btn_upload_single.setOnClickListener(this)
        btn_upload_multi.setOnClickListener(this)
        btn_delete_single.setOnClickListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // android 6.0 开始需要动态获取访问文件权限
            requestPermission()
        }
    }


    /**
     * 适配android6.0 动态申请访问文件权限
     */
    private fun requestPermission() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_CODE)
        }
    }


    /**
     * 权限申请回调结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}