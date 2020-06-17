package com.bandtec.finfamily.model

import java.io.Serializable

class GoalsResponse(
    val id: Int?,
    val name : String?,
    val description : String?,
    val value : Float?,
    val deadline : String?,
    val groupId : Int?
) : Serializable