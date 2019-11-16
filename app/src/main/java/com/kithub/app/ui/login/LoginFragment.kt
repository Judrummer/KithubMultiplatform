package com.kithub.app.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kithub.app.R
import com.kithub.app.base.BaseAndroidViewModel
import com.kithub.app.base.BaseFragment
import com.kithub.app.extension.listenAfterTextChange
import com.kithub.core.di.CoreModule
import com.kithub.core.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginAndroidViewModel : BaseAndroidViewModel<LoginViewModel>() {
    override val viewModel = CoreModule.provideLoginViewModel()
}

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    val viewModel by fragmentViewModel<LoginViewModel, LoginAndroidViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etUsername.listenAfterTextChange { viewModel.setUsername(it) }
        btnLogin.setOnClickListener {
            viewModel.login()
        }

        viewModel.state.observe { state ->
            contentView.visibility = if (state.loading) View.GONE else View.VISIBLE
            loadingView.visibility = if (state.loading) View.VISIBLE else View.GONE

            btnLogin.isEnabled = state.isValid
        }
        viewModel.loginCompleteEvent.observe {
            findNavController().navigate(R.id.action_loginFragment_to_repoListFragment)
        }
        viewModel.error.observeError()
    }

    override fun onResume() {
        super.onResume()
        etUsername.setText(viewModel.state.currentValue.username)
    }
}