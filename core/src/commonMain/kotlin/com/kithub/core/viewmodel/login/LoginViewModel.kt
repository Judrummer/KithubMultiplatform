package com.kithub.core.viewmodel.login

import com.kithub.core.common.kreactive.KObservable
import com.kithub.core.common.kreactive.KSingleEventRelay
import com.kithub.core.data.GithubApi
import com.kithub.core.data.UserPreference
import com.kithub.core.viewmodel.KViewModel
import kotlinx.coroutines.launch

data class LoginViewState(
    val username: String = "",
    val isValid: Boolean = false,
    val loading: Boolean = false
)

class LoginViewModel(
    private val userPreference: UserPreference,
    private val githubApi: GithubApi
) : KViewModel<LoginViewState>(LoginViewState()) {

    private val loginCompleteEventRelay = KSingleEventRelay<Unit>()

    val loginCompleteEvent: KObservable<Unit> = loginCompleteEventRelay

    fun setUsername(username: String) {
        setState { copy(username = username, isValid = username.isNotBlank()) }
    }

    fun login() {
        launch {
            try {
                setState { copy(loading = true) }
                githubApi.getUser(username = state.currentValue.username)
                userPreference.username = state.currentValue.username
                setState { copy(loading = false) }
                loginCompleteEventRelay.value = Unit
            } catch (e: Throwable) {
                setState { copy(loading = false) }
                setError(e)
            }
        }
    }
}