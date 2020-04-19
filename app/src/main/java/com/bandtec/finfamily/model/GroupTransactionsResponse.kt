package com.bandtec.finfamily.model

data class GroupTransactionsResponse (
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
    var idTransactionType : Int?,
    var createdAt : String?,
    var updatedAt : String?
)