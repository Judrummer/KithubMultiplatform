package com.kithub.core.viewmodel.login

import com.kithub.core.assertState
import com.kithub.core.common.coroutine.ApplicationDispatcher
import com.kithub.core.common.coroutine.TestDispatcher
import com.kithub.core.common.kreactive.test
import com.kithub.core.data.GithubApi
import com.kithub.core.data.UserEntity
import com.kithub.core.data.UserPreference
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@InternalCoroutinesApi
class LoginViewModelTest {

    val userPreference = mockk<UserPreference>(relaxed = true)
    val githubApi = mockk<GithubApi>(relaxed = true)

    val viewModel = LoginViewModel(userPreference, githubApi)

    val state = viewModel.state.test()
    val loginCompleteEvent = viewModel.loginCompleteEvent.test()
    val error = viewModel.error.test()

    init {
        ApplicationDispatcher = TestDispatcher
    }

    @Test
    fun testLoginComplete() {
        coEvery { githubApi.getUser("kotlin") } returns UserEntity()

        viewModel.setUsername("")
        viewModel.setUsername("kotlin")
        viewModel.login()

        state.assertState(LoginViewState(),
            { copy(username = "", isValid = false) },
            { copy(username = "kotlin", isValid = true) },
            { copy(loading = true) },
            { copy(loading = false) }
        )
        assertEquals(loginCompleteEvent.values, listOf(Unit))
        assertEquals(error.values, emptyList())
        verify { userPreference.username = "kotlin" }
    }


    @Test
    fun testLoginError() {
        val throwable = Throwable("Api Error")
        coEvery { githubApi.getUser("kotlin") } throws throwable

        viewModel.setUsername("")
        viewModel.setUsername("kotlin")
        viewModel.login()

        state.assertState(LoginViewState(),
            { copy(username = "", isValid = false) },
            { copy(username = "kotlin", isValid = true) },
            { copy(loading = true) },
            { copy(loading = false) }
        )
        assertEquals(loginCompleteEvent.values, emptyList())
        assertEquals(error.values, listOf(throwable))
        verify(inverse = true) { userPreference.username = "kotlin" }
    }
}