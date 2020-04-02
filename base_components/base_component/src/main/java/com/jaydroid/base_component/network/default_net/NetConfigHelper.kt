package com.jaydroid.base_component.network.default_net

/**
 *
 * @author wangxuejie
 * @date 2020-01-15 15:07
 * @version 1.0
 */
object NetConfigHelper {

    fun getNetConfigMap(): HashMap<String, String> {
        val configMap = HashMap<String, String>()
        configMap["config"] = "config"
        return configMap
    }
}