package com.jaydroid.base_component.network.bean.github

import com.google.gson.annotations.SerializedName

import java.util.Date

/**
 * Created on 2017/7/14.
 *
 * @author ThirtyDegreesRay
 */

class GitUser {

    var login: String? = null
    var id: String? = null
    var name: String? = null
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    var type: UserType? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var email: String? = null
    var bio: String? = null

    @SerializedName("public_repos")
    var publicRepos: Int = 0
    @SerializedName("public_gists")
    var publicGists: Int = 0
    var followers: Int = 0
    var following: Int = 0
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null


    enum class UserType {
        User, Organization
    }

    override fun toString(): String {
        return "GitUser(login=$login, id=$id, name=$name, avatarUrl=$avatarUrl, htmlUrl=$htmlUrl, type=$type, company=$company, blog=$blog, location=$location, email=$email, bio=$bio, publicRepos=$publicRepos, publicGists=$publicGists, followers=$followers, following=$following, createdAt=$createdAt, updatedAt=$updatedAt)"
    }


}
