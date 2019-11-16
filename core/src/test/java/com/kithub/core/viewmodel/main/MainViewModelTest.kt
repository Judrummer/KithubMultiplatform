package com.kithub.core.viewmodel.main

import com.kithub.core.common.kreactive.test
import com.kithub.core.data.UserPreference
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals


class MainViewModelTest {
    val userPreference = mockk<UserPreference>(relaxed = true)

    val viewModel = MainViewModel(userPreference)
    val rootNavigationEvent = viewModel.rootNavigationEvent.test()

    @Test
    fun initializeNotLogin() {
        every { userPreference.username } returns ""

        viewModel.initialize()

        assertEquals(rootNavigationEvent.values, listOf(MainRootNavigationEvent.Login))
    }


    @Test
    fun initializeLogin() {
        every { userPreference.username } returns "kotlin"

        viewModel.initialize()

        assertEquals(rootNavigationEvent.values, listOf(MainRootNavigationEvent.RepoList))
    }

}