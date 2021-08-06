package com.jay.base_lib.app.appdelegate

import androidx.annotation.StringDef


/**
 * APP分发优先级
 * @author wangxuejie
 * @version 1.0
 * @date 2020/3/31
 */
@StringDef(
    PriorityLevel.LOW,
    PriorityLevel.MEDIUM,
    PriorityLevel.HIGH
)
//表示注解所存活的时间在运行时,而不会存在 .class 文件中
@Retention(AnnotationRetention.SOURCE)
annotation class PriorityLevel {
    companion object {
        /**
         * 低优先级
         */
        const val LOW = "LOW"
        /**
         * 中优先级
         */
        const val MEDIUM = "MEDIUM"
        /**
         * 高优先级
         */
        const val HIGH = "HIGH"
    }
}