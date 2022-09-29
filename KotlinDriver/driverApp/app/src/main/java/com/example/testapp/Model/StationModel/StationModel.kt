package com.example.testapp.Model.StationModel

import java.io.Serializable

data class StationModel (val stationNm:String, val stationId:String, val ord:String, var waitingCnt:String , var CurrentPosFlag : Boolean = false) : Serializable