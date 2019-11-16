package com.kithub.app.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kithub.core.common.kreactive.KCompositeSubscriptions
import com.kithub.core.common.kreactive.KObservable
import com.kithub.core.viewmodel.KViewModel


abstract class BaseActivity : AppCompatActivity() {

    val subscriptions = KCompositeSubscriptions()

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }

    protected inline fun <reified VM : KViewModel<*>, reified AVM : BaseAndroidViewModel<VM>> activityViewModel() =
        lazy {
            ViewModelProvider(this).get(AVM::class.java).viewModel
        }

    protected inline fun <T : Any> KObservable<T>.observe(
        crossinline onNext: (T) -> Unit
    ) {
        subscribe(subscriptions) {
            onNext(it)
        }
    }
}