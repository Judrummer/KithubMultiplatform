package com.kithub.app.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kithub.app.extension.toast
import com.kithub.core.common.kreactive.KCompositeSubscriptions
import com.kithub.core.common.kreactive.KObservable
import com.kithub.core.viewmodel.KViewModel

abstract class BaseFragment(
    @LayoutRes private val layoutId: Int = -1
) : Fragment() {


    protected val subscriptions = KCompositeSubscriptions()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = if (layoutId != -1) inflater.inflate(layoutId, container, false)
    else null

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    protected inline fun <T : Any> KObservable<T>.observe(
        crossinline onNext: (T) -> Unit
    ) {
        subscribe(subscriptions) {
            onNext(it)
        }
    }

    protected fun KObservable<Throwable>.observeError() {
        subscribe(subscriptions) {
            it.printStackTrace()
            context?.toast("Error: ${it.message}")
        }
    }

    protected inline fun <reified VM : KViewModel<*>, reified AVM : BaseAndroidViewModel<VM>> fragmentViewModel() =
        lazy {
            ViewModelProvider(this).get(AVM::class.java).viewModel
        }

    protected inline fun <reified VM : KViewModel<*>, reified AVM : BaseAndroidViewModel<VM>> activityViewModel() =
        lazy {
            ViewModelProvider(requireActivity()).get(AVM::class.java).viewModel
        }
}
