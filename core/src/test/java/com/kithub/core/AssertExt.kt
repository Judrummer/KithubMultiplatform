package com.kithub.core


import com.kithub.core.common.kreactive.TestKObserver
import kotlin.test.assertEquals

fun <T : Any> TestKObserver<T>.assertState(initialState: T, vararg nextStateReducers: T.() -> T) {
    val expected =
        nextStateReducers.fold(listOf(initialState)) { currentStates, reducer -> currentStates + currentStates.last().reducer() }

    values.zip(expected) { v, e -> v to e }.forEachIndexed { index, (actual, expected) ->
        assertEquals(expected, actual, "State[$index] not matches $actual != $expected")
    }
}

fun <T : Any> TestKObserver<T>.assertState(initialState: T, nextStateReducers: List<T.() -> T>) {
    val expected =
        nextStateReducers.fold(listOf(initialState)) { currentStates, reducer -> currentStates + currentStates.last().reducer() }

    values.zip(expected) { v, e -> v to e }.forEachIndexed { index, (actual, expected) ->
        assertEquals(expected, actual, "State[$index] not matches $actual != $expected")
    }
}