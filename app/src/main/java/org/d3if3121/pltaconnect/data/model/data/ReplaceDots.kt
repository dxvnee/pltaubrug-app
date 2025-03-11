package org.d3if3121.pltaconnect.data.model.data

import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> T.replaceDotsWithCommas(): T {
    val constructor = T::class.primaryConstructor ?: return this

    val updatedParams = constructor.parameters.associateWith { param ->
        val value = this::class.memberProperties
            .firstOrNull { it.name == param.name }
            ?.getter?.call(this)

        when (value) {
            is String -> value.replace(".", ",")
            else -> value
        }
    }

    return constructor.callBy(updatedParams)
}

inline fun <reified T : Any> T.replaceCommasWithDots(): T {
    val constructor = T::class.primaryConstructor ?: return this

    val updatedParams = constructor.parameters.associateWith { param ->
        val value = this::class.memberProperties
            .firstOrNull { it.name == param.name }
            ?.getter?.call(this)

        when (value) {
            is String -> value.replace(",", ".")
            else -> value
        }
    }

    return constructor.callBy(updatedParams)
}

