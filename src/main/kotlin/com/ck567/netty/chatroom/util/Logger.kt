package com.ck567.netty.chatroom.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * # Module Logger
 * This is a [logger factory](https://www.reddit.com/r/Kotlin/comments/8gbiul/slf4j_loggers_in_3_ways/).
 */

/**
 * usage: ``val logger = logger(TheClass::class.java)`` or ``val logger = logger<TheClass>()``
 */
inline fun <reified T> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

/**
 * usage: val logger = logger(this)
 */
inline fun <reified T> loggerFrom(from: T): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

/**
 * usage:    companion object : Log {}
 */
interface Log {
    fun logger(): Logger = LoggerFactory.getLogger(this.javaClass)
}


/**
 * usage: val logger by LoggerDelegate()
 */
class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {

    companion object {
        private fun <T>createLogger(clazz: Class<T>) : Logger {
            return LoggerFactory.getLogger(clazz)
        }
    }

    private var logger: Logger? = null

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger {
        if (logger == null) {
            logger = createLogger(thisRef!!.javaClass)
        }
        return logger!!
    }
}
