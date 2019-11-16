package com.kithub.app.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

inline fun EditText.listenAfterTextChange(crossinline block: (String) -> Unit): TextWatcher = object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        block(s?.toString() ?: "")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}.also {
    addTextChangedListener(it)
}

fun EditText.onImeAction(vararg imeActions: Int, callback: () -> Unit) {
    setOnEditorActionListener { textView, i, keyEvent ->
        if (i in imeActions) {
            callback()
            true
        } else false
    }
}