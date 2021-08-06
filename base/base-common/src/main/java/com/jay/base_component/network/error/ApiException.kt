package com.jay.base_component.network.error

data class ApiException(var errCode: Int, var errMsg: String) : Exception()