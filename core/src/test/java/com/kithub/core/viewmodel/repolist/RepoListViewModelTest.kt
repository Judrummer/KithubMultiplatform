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
        //TODO: Implement RepoListViewModelTest.refresh
    }

    @Test
    fun logout() {
        //TODO: Implement RepoListViewModelTest.logout
    }
}