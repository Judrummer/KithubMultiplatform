package com.kithub.app.ui.repolist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.github.judrummer.jxadapter.JxAdapter
import com.kithub.app.R
import com.kithub.app.base.BaseAndroidViewModel
import com.kithub.app.base.BaseFragment
import com.kithub.app.extension.load
import com.kithub.core.di.CoreModule
import com.kithub.core.viewmodel.repolist.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.layout_user_profile.*

class RepoListAndroidViewModel : BaseAndroidViewModel<RepoListViewModel>() {
    override val viewModel = CoreModule.provideRepoListViewModel()
}

class RepoListFragment : BaseFragment(R.layout.fragment_repo_list) {

    val viewModel by fragmentViewModel<RepoListViewModel, RepoListAndroidViewModel>()

    val jxAdapter = JxAdapter {
        viewHolder {
            RepoItemViewHolder(it) { repoItem ->
                findNavController().navigate(
                    RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(
                        repoItem.name
                    )
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRepoList.adapter = jxAdapter

        btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_global_to_loginFragment)
        }

        viewModel.state.observe { state ->
            contentView.visibility = if (state.loading) View.GONE else View.VISIBLE
            loadingView.visibility = if (state.loading) View.VISIBLE else View.GONE
            ivUserAvatar.load(state.avatarUrl)
            tvUsername.text = state.username
            tvDisplayName.text = state.displayName
            jxAdapter.items = state.repos
        }
        viewModel.error.observeError()

        viewModel.attachFirstTime.observe {
            viewModel.refresh()
        }
    }
}