# bmob-android-data-sdk-kotlin-demo

Kotlin和Java之间具有可互操作性，可以直接使用Kotlin来调用Java写的Bmob的Android数据服务SDK。

为方便广大Bmob的Kotlin开发者，特开发此案例展示如何使用Kotlin来调用Bmob的Android数据服务SDK。


# 集成

## 仓库配置

在项目build.gradle的allprojects-repositories节点下配置：

    maven { url "https://raw.github.com/bmob/bmob-android-sdk/master" }

## 依赖配置

在应用build.gradle的dependencies节点下配置：

    implementation 'cn.bmob.android:bmob-sdk:3.6.6'

# 初始化
	
在应用主进程中进行代码初始化：

    Bmob.initialize(this,Constant.BMOB_APP_ID)





# 增删改查


## 新增数据

### 添加一行数据

    /**
     * 新增一条数据
     */
    private fun createOne() {
        var gameScore = GameScore()
        gameScore.playerName = "比目"
        gameScore.score = 89
        gameScore.isPay = false
        /**
         * 请不要给 gameScore.objectId 赋值，数据新增成功后将会自动给此条数据的objectId赋值并返回！
         */
        gameScore.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "新增数据成功：$objectId", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("CREATE", "新增数据失败：" + ex.message)
                }
            }
        })
    }

## 查询一条数据

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

## 更新一条数据

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

## 删除一条数据

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

## 查询多条数据

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


# 注册登录

## 用户名密码注册

        /**
         * bmob注册方法
         */
        var user = User()
        user.username = username
        user.setPassword(password)
        user.signUp(object : SaveListener<User>() {
            override fun done(currentUser: User?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(mContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })

## 用户名密码登录

        /**
         * bmob登录方法
         */
        var user = User()
        user.username = username
        user.setPassword(password)
        user.login(object : SaveListener<User>() {
            override fun done(currentUser: User?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(mContext,MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
                }
            }
        })

# 文件管理

## 权限
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


## 上传单个文件
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
                    Toast.makeText(mContext, "上传失败："+ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

## 关联线上文件和表字段

    /**
     * 将文件设置到表中
     */
    private fun setFileToTable(bmobFile: BmobFile) {
        var person = Person()
        person.file = bmobFile
        person.save(object : SaveListener<String>() {
            override fun done(objectId: String?, ex: BmobException?) {
                Toast.makeText(mContext, "成功将文件设置到表中", Toast.LENGTH_LONG).show()
            }
        })
    }


## 上传多个文件

	 /**
     * bmob  上传多个文件
     */
    private fun uploadMultiFile() {
        /**
         * 此处修改为你手机的文件路径
         */
        var filePaths:Array<String> = arrayOf("/storage/emulated/0/1.png", "/storage/emulated/0/2.png", "/storage/emulated/0/3.png")
        BmobFile.uploadBatch(filePaths,object : UploadBatchListener {
            override fun onError(code: Int, error: String?) {
                Toast.makeText(mContext, "上传出错：$error",Toast.LENGTH_LONG).show()
            }

            override fun onProgress(curIndex: Int, curPercent: Int, total: Int, totalPercent: Int) {
                Toast.makeText(mContext, "上传进度：$curIndex",Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(bmobFiles: MutableList<BmobFile>?, urls: MutableList<String>?) {
                if (urls != null) {
                    if (urls.size==filePaths.size)
                        Toast.makeText(mContext, "全部上传成功",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

## 删除单个文件

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


## 删除多个文件

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


# 短信

## 发送短信验证码

        /**
         * bmob发送验证码
         */
        BmobSMS.requestSMSCode(phone, "此处可填写控制台的短信模板名称，如果没有短信模板名称可填写空字符串使用默认模板", object : QueryListener<Int>() {
            override fun done(smsId: Int?, ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "发送成功：$smsId", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "发送成失败：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })

## 验证短信验证码

        /**
         * bmob验证验证码
         */
        BmobSMS.verifySmsCode(phone,code,object :UpdateListener(){
            override fun done(ex: BmobException?) {
                if (ex == null) {
                    Toast.makeText(mContext, "验证成功", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "验证失败：$ex.message", Toast.LENGTH_LONG).show()
                }
            }
        })

# 版本更新


## 版本更新设置

        //TODO 初始化，当控制台表出现后，注释掉此句
        BmobUpdateAgent.initAppVersion();
        //TODO 设置仅WiFi环境更新
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        //TODO 设置更新监听器
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                BmobException e = updateInfo.getException();
                if (e == null) {
                    updateResponse = updateInfo;
                    Toast.makeText(MainActivity.this, "检测更新返回：" + updateInfo.version + "-" + updateInfo.path, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "检测更新返回：" + e.getMessage() + "(" + e.getErrorCode() + ")", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //TODO 设置对话框监听器
        BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
                        Toast.makeText(MainActivity.this, "点击了立即更新按钮", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NotNow:
                        Toast.makeText(MainActivity.this, "点击了以后再说按钮", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Close:
                        Toast.makeText(MainActivity.this, "点击了对话框关闭按钮", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });



## 动态访问权限

    /**
     * 检查权限
     *
     * @param requestCode
     */
    public void checkStoragePermissions(int requestCode) {
        List<String> permissions = new ArrayList<>();
        int permissionCheckWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        int permissionCheckRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            String[] missions = new String[]{};
            ActivityCompat.requestPermissions(this, permissions.toArray(missions), requestCode);
        } else {
            switch (requestCode) {
                case REQUEST_AUTO:
                    BmobUpdateAgent.update(this);
                    break;
                case REQUEST_CHECK:
                    BmobUpdateAgent.forceUpdate(this);
                    break;
                case REQUEST_SILENT:
                    BmobUpdateAgent.silentUpdate(this);
                    break;
                case REQUEST_DELETE:
                    BmobUpdateAgent.deleteResponse(updateResponse);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 检查授权结果
     *
     * @param grantResults
     * @return
     */
    public boolean checkResults(int[] grantResults) {
        if (grantResults == null || grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUTO:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.update(this);
                }
                break;
            case REQUEST_CHECK:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.forceUpdate(this);
                }
                break;
            case REQUEST_SILENT:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.silentUpdate(this);
                }
                break;
            case REQUEST_DELETE:
                if (checkResults(grantResults)) {
                    BmobUpdateAgent.deleteResponse(updateResponse);
                }
                break;
            default:
                break;
        }
    }



## 清单文件设置

        <!--数据共享-->
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="cn.bmob.kotlin.data"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths"/>
        </provider>

        <activity
            android:name="cn.bmob.v3.update.UpdateDialogActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
