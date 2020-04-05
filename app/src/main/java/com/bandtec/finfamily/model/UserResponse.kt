package com.bandtec.finfamily.model

data class UserResponse(
    var id: Int?,
    var fullName: String?,
    var cpf: String?,
    var birthday: String?,
    var phoneAreaCode: String?,
    var phoneAreaNumber: String?,
    var email: String?,
    var password: String?,
    var createdAt: String?,
    var updatedAt: String?,
    var nickname: String?

)