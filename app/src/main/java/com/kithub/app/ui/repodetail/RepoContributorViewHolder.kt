package com.kithub.app.ui.repodetail

import android.view.ViewGroup
import com.kithub.app.R
import com.kithub.app.base.BaseViewHolder
import com.kithub.app.extension.loadCircular
import com.kithub.core.viewmodel.repodetail.ContributorItem
import kotlinx.android.synthetic.main.item_contributor.*

class RepoContributorViewHolder(
    viewGroup: ViewGroup
) : BaseViewHolder<ContributorItem>(viewGroup, R.layout.item_contributor) {
    override fun bind(item: ContributorItem) {
        tvContributorName.text = item.name
        tvContributorContributions.text = item.contributions.toString()
        ivContributorImage.loadCircular(item.avatarUrl)
    }
}