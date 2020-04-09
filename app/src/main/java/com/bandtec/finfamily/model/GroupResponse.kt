package com.bandtec.finfamily.model

data class GroupResponse(
    var id: Int?,
    var groupName: String?,
    var groupType: Int?,
    var groupOwner: Int?,
    var externalGroupId: String?
)