package com.bandtec.finfamily.model

import java.io.Serializable

data class GroupTransResponse (
    val id : Int?,
    var name : String,
    var description : String?,
    var value : Float?,
    var payDate : String?,
    var isRecorrent : Boolean?,
    var groupId : Int?,
    var userId : Int?,
    var idRecurrenceType : Int?,
    var idExpenseCategory : Int?,
    var idReceipeCategory : Int?,
    var idTransactionType : Int?,
    var createdAt : String?,
    var updatedAt : String?,
    val goalId : Int?
) : Serializable