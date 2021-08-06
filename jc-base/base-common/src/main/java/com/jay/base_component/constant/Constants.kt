package com.jay.base_component.constant

/**
 * 常用工具类
 * @author wangxuejie
 * @date 2019-12-17 14:30
 * @version 1.0
 */
object Constants {

    /* ========================================================= */
    /* ==================== SharedPreferences ================== */
    /* ========================================================= */

    object SP {

        /**
         * key:account_login
         */
        const val ACCOUNT_LOGIN = "account_login"

        /**
         * key:git_account_login
         */
        const val GIT_ACCOUNT_LOGIN = "git_account_login"

        /**
         * key:auth_github_token
         */
        const val AUTH_GITHUB_TOKEN = "auth_github_token"
    }
    /* ========================================================= */
    /* ======================= HashMap Keys ==================== */
    /* ========================================================= */

    object MapKey {

        /**
         * 通用_id
         */
        const val ID = "_id"
        const val URL: String = "_url"
        const val LINK: String = "_link"
        const val AUTHOR: String = "_author"
        const val TITLE: String = "_title"
        const val IMAGES: String = "images"
        const val INDEX: String = "index"
        const val CID: String = "cid"

    }

    /* ========================================================= */
    /* ======================= Intent Keys ===================== */
    /* ========================================================= */

    object IntentKey {

        /**
         * 用HashMap存放Intent携带数据的key
         */
        const val MAP_PARAMS = "map_params"

    }
}