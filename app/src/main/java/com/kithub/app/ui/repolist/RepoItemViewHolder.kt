package com.kithub.app.ui.repolist

import android.view.ViewGroup
import com.kithub.app.R
import com.kithub.app.base.BaseViewHolder
import com.kithub.core.viewmodel.repolist.RepoItem
import kotlinx.android.synthetic.main.item_repo.*

class RepoItemViewHolder(parent: ViewGroup,
                         private val onClick:(RepoItem)->Unit) : BaseViewHolder<RepoItem>(parent, R.layout.item_repo) {

    override fun bind(item: RepoItem) {
        tvRepoName.text = item.name
        tvRepoStar.text = item.starCount.toString()
        tvRepoDescription.text = item.descriptionText
        itemView.setOnClickListener { onClick(item) }
    }
}