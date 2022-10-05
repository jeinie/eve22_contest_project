package com.example.myapplication.PayInfo

import java.io.Serializable

// 보내야 할 데이터가
// 요금, 승객 ID(=주문 ID), 카드번호, 카드유효기간(년,월), 카드 비밀번호(2자리), customerIdentityNumber
data class PayInfo(
    val amount:Int,
    val passengerId:String,
    val cardNumber:String,
    val cardExpirationYear:String,
    val cardExpirationMonth:String,
    val cardPassword:String,
    val customerIdentityNumber:String
    ) : Serializable
