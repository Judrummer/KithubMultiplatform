package com.kithub.core.viewmodel.repolist

import com.kithub.core.assertState
import com.kithub.core.common.coroutine.ApplicationDispatcher
import com.kithub.core.common.coroutine.TestDispatcher
import com.kithub.core.common.kreactive.test
import com.kithub.core.data.GithubApi
import com.kithub.core.data.RepoEntity
import com.kithub.core.data.UserEntity
import com.kithub.core.data.UserPreference
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertTrue

@UseExperimental(InternalCoroutinesApi::class)
class RepoListViewModelTest {

    val userPreference = mockk<UserPreference>(relaxed = true)
    val githubApi = mockk<GithubApi>(relaxed = true)

    val viewModel = RepoListViewModel(userPreference, githubApi)

    val state = viewModel.state.test()
    val error = viewModel.error.test()

    init {
        ApplicationDispatcher = TestDispatcher
    }

    @Test
    fun refresh() {
        val userEntity = UserEntity(
            login = "kotlin",
            name = "Kotlin Lang",
            avatarUrl = "www.google.com"
        )

        val repoEntities = listOf(
            RepoEntity(
                name = "ktor",
                description = "Http library",
                stargazersCount = 9999
            )
        )
        every { userPreference.username } returns "kotlin"
        coEvery { githubApi.getUser("kotlin") } returns userEntity
        coEvery { githubApi.getUserRepos("kotlin") } returns repoEntities

        viewModel.refresh()

        assertTrue { error.values.isEmpty() }
        state.assertState(RepoListViewState(),
            { copy(loading = true) },
            {
                copy(
                    loading = false,
                    username = "kotlin",
                    avatarUrl = "www.google.com",
                    displayName = "Kotlin Lang",
                    repos = listOf(
                        RepoItem(
                            name = "ktor",
                            descriptionText = "Http library",
                            starCount = 9999
                        )
                    )
                )
            }
        )
    }

    @Test
    fun logout() {
        viewModel.logout()

        verify { userPreference.username = "" }
    }
}