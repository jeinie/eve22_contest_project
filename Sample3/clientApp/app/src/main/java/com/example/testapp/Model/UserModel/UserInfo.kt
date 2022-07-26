package com.example.testapp.Model.UserModel

class UserInfo {
    private var id : String
    private var password : String
    private var nick : String
    private var name : String
    private var age : Int
    private var cardnum : String
    private var busId : String
    private var success : String
    private var msg : String

    constructor(id : String,
                password : String,
                nick : String,
                name : String,
                age : Int,
                cardnum : String = "",
                busId : String = "",
                success : String,
                msg : String){
        this.id = id
        this.password = password
        this.nick = nick
        this.name = name
        this.age = age
        this.cardnum = cardnum
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
    fun getUserAge() : Int{
        return this.age
    }
    
    //cardnum와 busid는 변동이 있을수 있음으로 setter도 생성
    fun getUserCardNum() : String{
        return this.cardnum
    }
    fun getUserBusId() : String{
        return this.busId
    }
    fun setUserCardNum(cardnum: String) : Unit{
        this.cardnum = cardnum
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