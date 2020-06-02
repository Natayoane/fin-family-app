package com.bandtec.finfamily.model

import java.io.Serializable

data class GroupResponse(
    var id: Int?,
    var groupName: String?,
    var groupType: Int?,
    var groupOwner: Int?,
    var externalGroupId: String?
) : Serializable