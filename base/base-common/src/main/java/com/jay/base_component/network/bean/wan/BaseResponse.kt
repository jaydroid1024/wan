package com.jay.base_component.network.bean.wan

data class BaseResponse<T>(
    /**
     * WanAndroid返回数据结构定义:
    {
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
    }

    所有的返回结构均为上述，其中errorCode如果为负数则认为错误，此时errorMsg会包含错误信息。data为Object，返回数据根据不同的接口而变化。

    errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    errorCode = -1001 代表登录失效，需要重新登录。
     */
    var data: T?,
    var results: T?,
    val errorMsg: String? = null,
    var errorCode: Int? = -1,
    var error: Boolean? = true

) {

    fun isSuccess(): Boolean {
        return errorCode == 0
    }
}