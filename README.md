# bmob-android-data-sdk-kotlin-demo

Kotlin和Java之间具有可互操作性，可以直接使用Kotlin来调用Java写的Bmob的Android数据服务SDK。

为方便广大Bmob的Kotlin开发者，特开发此案例展示如何使用Kotlin来调用Bmob的Android数据服务SDK。


# 初始化

    Bmob.initialize(this,Constant.BMOB_APP_ID)

# 增删改查

## 添加一行数据

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

