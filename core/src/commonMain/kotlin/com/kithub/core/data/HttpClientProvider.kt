package com.kithub.core.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import kotlinx.serialization.json.Json

expect fun httpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

//TODO: Put your github token here . You can generate new token at https://github.com/settings/tokens/new and check repo and user section
val githubAuthToken = ""


fun provideHttpClient() = httpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict).apply {
            register(UserEntity.serializer())
            register(RepoDetailEntity.serializer())


            //Can't register List serializer
            //registerList(RepoEntity.serializer())
            //registerList(ContributorEntity.serializer())
        }
    }

    install("HeaderInterceptor") {
        requestPipeline.intercept(HttpRequestPipeline.Before) {
            context.header("Authorization", "token $githubAuthToken")
        }
    }

//    install(Logging) {
//        level = LogLevel.ALL
//    }
}