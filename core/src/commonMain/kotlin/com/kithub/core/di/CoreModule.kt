package com.kithub.core.di

import com.kithub.core.data.GithubApi
import com.kithub.core.data.GithubApiImpl
import com.kithub.core.data.UserPreference
import com.kithub.core.data.UserPreferenceImpl
import com.kithub.core.viewmodel.login.LoginViewModel
import com.kithub.core.viewmodel.main.MainViewModel
import com.kithub.core.viewmodel.repodetail.RepoDetailViewModel
import com.kithub.core.viewmodel.repolist.RepoListViewModel

//Simple DI , you can use KODEIN instead of this
object CoreModule {
    //Data
    private fun provideGithubApi(): GithubApi = GithubApiImpl()

    private fun provideUserPreference(): UserPreference = UserPreferenceImpl()


    //ViewModel
    fun provideMainViewModel(): MainViewModel = MainViewModel(provideUserPreference())

    fun provideLoginViewModel(): LoginViewModel = LoginViewModel(
        provideUserPreference(),
        provideGithubApi()
    )

    fun provideRepoListViewModel(): RepoListViewModel =
        RepoListViewModel(
            provideUserPreference(),
            provideGithubApi()
        )

    fun provideRepoDetailViewModel(): RepoDetailViewModel =
        RepoDetailViewModel(provideUserPreference(), provideGithubApi())
}