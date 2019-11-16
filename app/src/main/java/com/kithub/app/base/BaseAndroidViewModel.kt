package com.kithub.app.base

import com.kithub.core.viewmodel.KViewModel
import androidx.lifecycle.ViewModel as AndroidViewModel

abstract class BaseAndroidViewModel<VM : KViewModel<*>> : AndroidViewModel() {
    abstract val viewModel: VM

    override fun onCleared() {
        viewModel.dispose()
        super.onCleared()
    }
}