package com.kithub.app.ui.repodetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.github.judrummer.jxadapter.JxAdapter
import com.kithub.app.R
import com.kithub.app.base.BaseAndroidViewModel
import com.kithub.app.base.BaseFragment
import com.kithub.core.di.CoreModule
import com.kithub.core.viewmodel.repodetail.RepoDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailAndroidViewModel : BaseAndroidViewModel<RepoDetailViewModel>() {
    override val viewModel = CoreModule.provideRepoDetailViewModel()
}

class RepoDetailFragment : BaseFragment(R.layout.fragment_repo_detail) {

    val args by navArgs<RepoDetailFragmentArgs>()
    val jxAdapter = JxAdapter {
        viewHolder { RepoContributorViewHolder(it) }
    }

    val viewModel by fragmentViewModel<RepoDetailViewModel, RepoDetailAndroidViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRepoContributorList.adapter = jxAdapter

        viewModel.state.observe { state ->
            contentView.visibility = if (state.loading) View.GONE else View.VISIBLE
            loadingView.visibility = if (state.loading) View.VISIBLE else View.GONE

            tvRepoName.text = state.name

            tvRepoDescription.text = state.descriptionText

            tvRepoLanguage.text = state.language
            tvRepoLanguage.visibility = if (state.language.isNotBlank()) View.VISIBLE else View.GONE

            tvRepoSSHUrl.text = state.sshUrl
            tvRepoStar.text = state.starCount.toString()
            jxAdapter.items = state.contributors
        }

        viewModel.attachFirstTime.observe {
            viewModel.refresh(args.repoName)
        }
    }
}