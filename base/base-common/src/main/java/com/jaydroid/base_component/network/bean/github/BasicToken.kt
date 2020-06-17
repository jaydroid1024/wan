package com.jaydroid.base_component.network.bean.github

import com.google.gson.annotations.SerializedName

import java.util.Arrays
import java.util.Date

/**
 * Created on 2017/8/1.
 *
 * @author ThirtyDegreesRay
 */

class BasicToken {


    var id: Int = 0
    var url: String? = null
    var token: String? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    var scopes: List<String>? = null

    //    {
    //        "id": 118469758,
    //            "url": "https://api.github.com/authorizations/118469758",
    //            "app": {
    //        "name": "OpenHub",
    //                "url": "https://github.com/ThirtyDegreesRay/OpenHub",
    //                "client_id": "2a2f29517239a22ad850"
    //    },
    //        "token": "2d6b2205ccffb76fe32cd53314bf75c47369b0f1",
    //            "hashed_token": "2eb24cf25622f32fdf26761c3d9051813c6985faed9d457d5d5278cb2bfe70a2",
    //            "token_last_eight": "7369b0f1",
    //            "note": "com.thirtydegreesray.openhub",
    //            "note_url": null,
    //            "created_at": "2017-08-01T03:46:37Z",
    //            "updated_at": "2017-08-01T03:46:37Z",
    //            "scopes": [
    //        "user",
    //                "repo",
    //                "gist",
    //                "notifications"
    //    ],
    //        "fingerprint": null
    //    }


    override fun toString(): String {
        return "BasicToken{" +
                "id=" + id +
                ", url='" + url + '\''.toString() +
                ", token='" + token + '\''.toString() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", scopes=" + scopes +
                '}'.toString()
    }

    companion object {

        fun generateFromOauthToken(oauthToken: OauthToken): BasicToken {
            val basicToken = BasicToken()
            basicToken.token = oauthToken.accessToken
            basicToken.scopes =
                Arrays.asList(*oauthToken.scope.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            return basicToken
        }
    }
}
