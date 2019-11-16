package com.kithub.app

import android.os.Bundle
import androidx.navigation.findNavController
import com.kithub.app.base.BaseActivity
import com.kithub.app.base.BaseAndroidViewModel
import com.kithub.core.di.CoreModule
import com.kithub.core.viewmodel.main.MainRootNavigationEvent
import com.kithub.core.viewmodel.main.MainViewModel


class MainAndroidViewModel : BaseAndroidViewModel<MainViewModel>() {
    override val viewModel: MainViewModel = CoreModule.provideMainViewModel()
}

class MainActivity : BaseActivity() {

    val viewModel by activityViewModel<MainViewModel, MainAndroidViewModel>()
    val navController by lazy { findNavController(R.id.navFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.rootNavigationEvent.observe { rootNavigationEvent ->
            when (rootNavigationEvent) {
                MainRootNavigationEvent.Login -> navController.navigate(R.id.action_global_to_loginFragment)
                MainRootNavigationEvent.RepoList -> navController.navigate(R.id.action_global_to_repoListFragment)
            }
        }
        viewModel.attachFirstTime.observe {
            if (savedInstanceState == null) viewModel.initialize()
        }
    }
}