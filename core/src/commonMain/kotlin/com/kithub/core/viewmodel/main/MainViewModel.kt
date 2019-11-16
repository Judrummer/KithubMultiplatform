package com.kithub.core.viewmodel.main

import com.kithub.core.common.kreactive.KObservable
import com.kithub.core.common.kreactive.KSingleEventRelay
import com.kithub.core.data.UserPreference
import com.kithub.core.data.UserPreferenceImpl
import com.kithub.core.viewmodel.KViewModel

object MainViewState

sealed class MainRootNavigationEvent {
    object Login : MainRootNavigationEvent()
    object RepoList : MainRootNavigationEvent()
}

class MainViewModel(
    private val userPreference: UserPreference = UserPreferenceImpl()
) : KViewModel<MainViewState>(MainViewState) {
    private val rootNavigationEventRelay = KSingleEventRelay<MainRootNavigationEvent>()

    val rootNavigationEvent: KObservable<MainRootNavigationEvent> = rootNavigationEventRelay

    fun initialize() {
        if (userPreference.username.isNotBlank()) {
            rootNavigationEventRelay.value = MainRootNavigationEvent.RepoList
        } else {
            rootNavigationEventRelay.value = MainRootNavigationEvent.Login
        }
    }
}