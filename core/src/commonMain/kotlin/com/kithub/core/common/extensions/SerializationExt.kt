package com.kithub.core.common.extensions

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

inline fun <reified T> KSerializer<T>.parse(jsonString: String) = Json.nonstrict.parse(this, jsonString)

inline fun <reified T> KSerializer<T>.stringify(obj: T) = Json.nonstrict.stringify(this, obj)