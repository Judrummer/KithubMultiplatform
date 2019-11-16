package com.kithub.core.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

actual fun httpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(block)