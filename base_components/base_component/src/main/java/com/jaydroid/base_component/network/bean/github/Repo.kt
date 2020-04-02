package com.jaydroid.base_component.network.bean.github

/**
 *
 * @author wangxuejie
 * @date 2019-12-27 11:08
 * @version 1.0
 */
class Repo {
    private val id: String? = null
    private val name: String? = null
    private val description: String? = null
    private val url: String? = null

    override fun toString(): String {
        return "Repo{" +
                "id='" + id + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", url='" + url + '\''.toString() +
                '}'.toString()
    }
}