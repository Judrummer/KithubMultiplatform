package com.kithub.core.viewmodel.repolist

import com.kithub.core.data.GithubApi
import com.kithub.core.data.UserPreference
import com.kithub.core.viewmodel.KViewModel
import kotlinx.coroutines.launch


data class RepoListViewState(
    val loading: Boolean = true,
    val username: String = "",
    val displayName: String = "",
    val avatarUrl: String = "",
    val repos: List<RepoItem> = emptyList()
)

data class RepoItem(
    val name: String,
    val descriptionText: String, // Don't use val description: String because iOS can't see it
    val starCount: Int
)

class RepoListViewModel(
    private val userPreference: UserPreference,
    private val githubApi: GithubApi
) : KViewModel<RepoListViewState>(RepoListViewState()) {
    fun refresh() {
        launch {
            try {
                setState { copy(loading = true) }

                val userEntity = githubApi.getUser(username = userPreference.username)
                val repoEntities = githubApi.getUserRepos(username = userPreference.username)

                setState {
                    copy(
                        loading = false,
                        username = userEntity.login ?: "",
                        displayName = userEntity.name.orEmpty(),
                        avatarUrl = userEntity.avatarUrl.orEmpty(),
                        repos = repoEntities.map {
                            RepoItem(
                                name = it.name.orEmpty(),
                                descriptionText = it.description.orEmpty(),
                                starCount = it.stargazersCount ?: 0
                            )
                        }
                    )
                }
            } catch (e: Throwable) {
                setState { copy(loading = false) }
                setError(e)
            }
        }
    }

    fun logout() {
        userPreference.username = ""
    }
}