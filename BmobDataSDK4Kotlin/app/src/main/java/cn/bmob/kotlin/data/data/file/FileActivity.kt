package cn.bmob.kotlin.data.data.file

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cn.bmob.kotlin.data.R
import cn.bmob.kotlin.data.bean.Post
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.*
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
    private val REQUEST_SELECT_AVATAR_CODE: Int = 1003


    override fun onClick(v: View?) {
        var id = v!!.id
        when (id) {
            R.id.btn_upload_single -> chooseSingleFile()
            R.id.btn_upload_avatar -> chooseAvatar()
            R.id.btn_upload_multi -> uploadMultiFile()
            R.id.btn_delete_single -> deleteSingleFile()
            R.id.btn_delete_multi -> deleteMultiFile()
        }
    }

    private fun chooseAvatar() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_SELECT_AVATAR_CODE)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "no file manager", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteMultiFile() {
        var  bmobQuery =BmobQuery<Post>()
        bmobQuery.findObjects(object : FindListener<Post>(){
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {
                if (ex==null){

                    var urls: Array<String> = arrayOf()
                    if (posts != null) {
                        var index=0
                        for (post:Post in posts){
                            urls[index]= post.image!!.fileUrl
                        }
                        deleteMultiFiles(urls)
                    }
                }else{
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 删除多个文件
     */
    private fun deleteMultiFiles(urls: Array<String>) {
        BmobFile.deleteBatch(urls,object :DeleteBatchListener(){
            override fun done(deleteUrls: Array<out String>?, ex: BmobException?) {
                if (ex==null){
                    if (urls.size== deleteUrls!!.size){
                        Toast.makeText(mContext, "全部删除成功", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * 查询并删除第一条数据关联的文件
     */
    private fun deleteSingleFile() {
        var  bmobQuery =BmobQuery<Post>()
        bmobQuery.findObjects(object : FindListener<Post>(){
            override fun done(posts: MutableList<Post>?, ex: BmobException?) {
                if (ex==null){
                    var file = posts!![0].image
                    deleteSingleFile(file)
                }else{
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    /**
     * 删除单个文件
     */
    private fun deleteSingleFile(bmobFile: BmobFile?) {
        bmobFile!!.delete(object :UpdateListener(){
            override fun done(ex: BmobException?) {
                if (ex==null){
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    /**
     * bmob  上传多个文件
     */
    private fun uploadMultiFile() {
        /**
         * 此处修改为你手机的文件路径
         */
        var filePaths: Array<String> = arrayOf("/storage/emulated/0/1.png", "/storage/emulated/0/2.png", "/storage/emulated/0/3.png")
        BmobFile.uploadBatch(filePaths, object : UploadBatchListener {
            override fun onError(code: Int, error: String?) {
                Toast.makeText(mContext, "上传出错：$error", Toast.LENGTH_LONG).show()
            }

            override fun onProgress(curIndex: Int, curPercent: Int, total: Int, totalPercent: Int) {
                Toast.makeText(mContext, "上传进度：$curIndex", Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(bmobFiles: MutableList<BmobFile>?, urls: MutableList<String>?) {
                if (urls != null) {
                    if (urls.size == filePaths.size)
                        Toast.makeText(mContext, "全部上传成功", Toast.LENGTH_LONG).show()
                }
            }
        })
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
                    setFileToTable(bmobFile)
                } else {
                    Toast.makeText(mContext, "上传失败：" + ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * 将文件设置到表中
     */
    private fun setFileToTable(bmobFile: BmobFile) {
        var post = Post()
        post.image = bmobFile
        post.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                Toast.makeText(mContext, "成功将文件设置到表中", Toast.LENGTH_LONG).show()
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


    /**
     * 文件选择回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_CODE ){
                val uri = data!!.data
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = managedQuery(uri, projection, null, null, null)
                val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                val path = cursor.getString(index)
                uploadSingle(path)
            }else{

            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        mContext = this
        btn_upload_single.setOnClickListener(this)
        btn_upload_multi.setOnClickListener(this)
        btn_delete_single.setOnClickListener(this)
        btn_delete_multi.setOnClickListener(this)
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