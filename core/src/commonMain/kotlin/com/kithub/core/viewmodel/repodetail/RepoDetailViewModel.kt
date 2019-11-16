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
        //TODO: Implement RepoDetailViewModel.refresh
    }
}