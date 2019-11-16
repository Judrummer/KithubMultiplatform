package com.kithub.core.viewmodel.repodetail

import com.kithub.core.data.GithubApi
import com.kithub.core.data.UserPreference
import com.kithub.core.viewmodel.KViewModel
import kotlinx.coroutines.launch

data class RepoDetailViewState(
    val loading: Boolean = true,
    val name: String = "",
    val descriptionText: String = "",
    val language: String = "",
    val sshUrl: String = "",
    val starCount: Int = 0,
    val contributors: List<ContributorItem> = emptyList()
)

data class ContributorItem(
    val name: String = "",
    val contributions: Int = 0,
    val avatarUrl: String = ""
)

class RepoDetailViewModel(
    private val userPreference: UserPreference,
    private val githubApi: GithubApi
) : KViewModel<RepoDetailViewState>(RepoDetailViewState()) {
    fun refresh(repoName: String) {
        launch {
            try {
                setState { copy(loading = true) }
                val contributors = githubApi.getContributors(userPreference.username, repoName)
                val repoDetail = githubApi.getUserRepoDetail(userPreference.username, repoName)

                setState {
                    copy(loading = false,
                        name = repoDetail.name.orEmpty(),
                        descriptionText = repoDetail.description.orEmpty(),
                        language = repoDetail.language.orEmpty(),
                        starCount = repoDetail.stargazersCount ?: 0,
                        sshUrl = repoDetail.sshUrl.orEmpty(),
                        contributors = contributors.map {
                            ContributorItem(
                                name = it.login.orEmpty(),
                                avatarUrl = it.avatarUrl.orEmpty(),
                                contributions = it.contributions ?: 0
                            )
                        })
                }

            } catch (e: Throwable) {
                setState { copy(loading = false) }
                setError(e)
            }
        }
    }
}