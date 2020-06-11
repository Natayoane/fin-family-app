package com.bandtec.finfamily.model

import com.google.gson.annotations.SerializedName

class CredencialsModel (
        @SerializedName("email")
        var email: String?,
        @SerializedName("password")
        var password: String?
)