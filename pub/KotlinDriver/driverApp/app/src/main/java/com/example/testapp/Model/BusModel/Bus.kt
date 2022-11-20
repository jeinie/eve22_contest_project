package com.example.testapp.Model.BusModel

import com.example.testapp.Model.HttpModel.*

data class Bus (
    val comMsgHeader: ComMsgHeader,
    val msgHeader: MsgHeader,
    val msgBody: MsgBody<Item>
)