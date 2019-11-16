package com.kithub.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetailEntity(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("git_url") val gitUrl: String? = null,
    @SerialName("ssh_url") val sshUrl: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int? = null,
    @SerialName("subscribers_count") val subscriberCount: Int? = null,
    @SerialName("updated_at") val updatedAt: String? = null //Format 2017-07-01T21:44:35Z
)


@Serializable
data class RepoEntity(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("git_url") val gitUrl: String? = null,
    @SerialName("ssh_url") val sshUrl: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int? = null
)

@Serializable
data class ContributorEntity(
    @SerialName("login") val login: String? = null,
    @SerialName("contributions") val contributions: Int? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
)

@Serializable
data class UserEntity(
    @SerialName("id") val id: Long = 0L,
    @SerialName("login") val login: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
)