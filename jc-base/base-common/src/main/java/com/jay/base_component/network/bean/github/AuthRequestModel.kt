package com.jay.base_component.network.bean.github

import com.google.gson.annotations.SerializedName

/**
 * Created on 2017/8/1.
 *
 * @author ThirtyDegreesRay
 */

class AuthRequestModel {

    var scopes: List<String>? = null
        set
    var note: String? = null
        set
    var noteUrl: String? = null
        set
    @SerializedName("client_id")
    var clientId: String? = null
        set
    @SerializedName("client_secret")
    var clientSecret: String? = null
        set

}
