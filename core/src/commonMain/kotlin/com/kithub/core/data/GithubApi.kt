package com.kithub.core.data

import com.kithub.core.common.extensions.parse
import io.ktor.client.request.get
import kotlinx.serialization.list

interface GithubApi {
    suspend fun getUser(username: String): UserEntity
    suspend fun getUserRepos(username: String): List<RepoEntity>
    suspend fun getUserRepoDetail(username: String, repoName: String): RepoDetailEntity
    suspend fun getContributors(username: String, repoName: String): List<ContributorEntity>
}

class GithubApiImpl : GithubApi {

    private val url = "https://api.github.com/"

    override suspend fun getUser(username: String): UserEntity =
        provideHttpClient().get("${url}users/$username")

    override suspend fun getUserRepos(username: String): List<RepoEntity> =
        provideHttpClient().get<String>("${url}users/$username/repos?page=0&per_page=10")
            .let { RepoEntity.serializer().list.parse(it) }
    //Because ktor can't register List serializer more than one


    override suspend fun getUserRepoDetail(username: String, repoName: String): RepoDetailEntity =
        provideHttpClient().get("${url}repos/$username/$repoName")

    override suspend fun getContributors(
        username: String,
        repoName: String
    ): List<ContributorEntity> =
        provideHttpClient().get<String>("${url}repos/$username/$repoName/contributors?page=0&per_page=10")
            .let { ContributorEntity.serializer().list.parse(it) }
    //Because ktor can't register List serializer more than one

}