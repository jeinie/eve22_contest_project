package com.example.testapp.Model.UserModel

class UserInfo {
    private var id : String
    private var password : String
    private var nick : String
    private var name : String
    private var busId : String
    private var success : String
    private var msg : String

    constructor(id : String,
                password : String,
                nick : String,
                name : String,
                busId : String = "",
                success : String,
                msg : String){
        this.id = id
        this.password = password
        this.nick = nick
        this.name = name
        this.busId = busId
        this.success = success
        this.msg = msg
    }

    fun getUserid() : String{
        return this.id
    }

    fun getUserpw() : String{
        return this.password
    }

    fun getUserNick() : String{
        return this.nick
    }
    fun getUserName() : String{
        return this.name
    }
    fun getUserBusId() : String{
        return this.busId
    }
    fun setUserBusId(busId: String) : Unit{
        this.busId = busId
    }

    fun getSuccess() : String{
        return this.success
    }

    fun getMsg() : String{
        return this.msg
    }
}