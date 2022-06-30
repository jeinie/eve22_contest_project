package com.example.testapp.Model.indexModel

class UserInfo {
    private var userid : String
    private var userpw : String
    private var success : String
    private var msg : String

    constructor(userid : String,userpw : String,success : String,msg : String){
        this.userid = userid
        this.userpw = userpw
        this.success = success
        this.msg = msg
    }

    fun getUserid() : String{
        return this.userid
    }

    fun getUserpw() : String{
        return this.userpw
    }

    fun getSuccess() : String{
        return this.success
    }

    fun getMsg() : String{
        return this.msg
    }
}