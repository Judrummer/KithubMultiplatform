package com.kithub.core.viewmodel

import com.kithub.core.common.coroutine.ApplicationDispatcher
import com.kithub.core.common.kreactive.KObservable
import com.kithub.core.common.kreactive.KSingleEventRelay
import com.kithub.core.common.kreactive.KStateRelay
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class KViewModel<S:Any>(private val initialState: S) : CoroutineScope {
    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { context, err ->
        setError(err)
    }
    private val stateRelay = KStateRelay<S>().apply { value = initialState }
    private val errorRelay = KSingleEventRelay<Throwable>()
    private val attachFirstTimeRelay = KSingleEventRelay<Unit>().apply { value = Unit }

    val state: KObservable<S> = stateRelay
    val error: KObservable<Throwable> = errorRelay
    val attachFirstTime: KObservable<Unit> = attachFirstTimeRelay

    override val coroutineContext: CoroutineContext
        get() = job + ApplicationDispatcher + exceptionHandler

    fun dispose() {
        job.cancel()
    }

    protected fun setState(reducer: S.() -> S) {
        stateRelay.value = stateRelay.value.reducer()
    }

    protected fun setError(throwable: Throwable) {
        errorRelay.value = throwable
    }
}