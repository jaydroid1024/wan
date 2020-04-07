package com.jaydroid.base_component.network.bean.wan.user

class LoginResponse(
    val admin: Boolean,
    val collectionIds: ArrayList<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)