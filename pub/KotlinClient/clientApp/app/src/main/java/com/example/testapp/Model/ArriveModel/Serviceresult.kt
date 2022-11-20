package com.example.testapp.Model.ArriveModel

import com.example.testapp.Model.HttpModel.ComMsgHeader

data class Serviceresult(
    val commsgheader: ComMsgHeader,
    val msgbody: Msgbody,
    val msgheader: Msgheader
)