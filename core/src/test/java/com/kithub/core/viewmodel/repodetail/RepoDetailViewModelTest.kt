package com.kithub.core.viewmodel.repodetail

import com.kithub.core.assertState
import com.kithub.core.common.coroutine.ApplicationDispatcher
import com.kithub.core.common.coroutine.TestDispatcher
import com.kithub.core.common.kreactive.test
import com.kithub.core.data.ContributorEntity
import com.kithub.core.data.GithubApi
import com.kithub.core.data.RepoDetailEntity
import com.kithub.core.data.UserPreference
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import kotlin.test.assertTrue


@UseExperimental(InternalCoroutinesApi::class)
class RepoDetailViewModelTest {
    val userPreference = mockk<UserPreference>(relaxed = true)
    val githubApi = mockk<GithubApi>(relaxed = true)

    val viewModel = RepoDetailViewModel(userPreference, githubApi)

    val state = viewModel.state.test()
    val error = viewModel.error.test()

    init {
        ApplicationDispatcher = TestDispatcher
    }

    @Test
    fun refresh() {
        val repoDetailEntity = RepoDetailEntity(
            name = "ktor",
            description = "http library",
            language = "kotlin",
            sshUrl = "ssh.url",
            stargazersCount = 2000
        )

        val contributorEntities = listOf(
            ContributorEntity(
                login = "alice",
                contributions = 120,
                avatarUrl = "avatar.url"
            )
        )

        every { userPreference.username } returns "kotlin"
        coEvery { githubApi.getContributors("kotlin", "ktor") } returns contributorEntities
        coEvery { githubApi.getUserRepoDetail("kotlin", "ktor") } returns repoDetailEntity

        viewModel.refresh("ktor")

        assertTrue { error.values.isEmpty() }
        state.assertState(
            RepoDetailViewState(),
            { copy(loading = true) },
            {
                copy(
                    loading = false,
                    name = "ktor",
                    descriptionText = "http library",
                    starCount = 2000,
                    sshUrl = "ssh.url",
                    language = "kotlin",
                    contributors = listOf(
                        ContributorItem(
                            name = "alice",
                            contributions = 120,
                            avatarUrl = "avatar.url"
                        )
                    )
                )
            }
        )
    }
}