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
        //TODO: Implement RepoListViewModel.refresh
    }

    fun logout() {
        //TODO: Implement RepoListViewModel.logout
    }
}