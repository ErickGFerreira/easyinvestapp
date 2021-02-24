package com.example.easyinvest.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(func: (String) -> Unit){
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            func.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
           //dontNeed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //dontNeed
        }

    })
}