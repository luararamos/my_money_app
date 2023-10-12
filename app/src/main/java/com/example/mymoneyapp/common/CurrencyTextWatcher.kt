package com.example.mymoneyapp.common

import android.text.Editable
import android.text.TextWatcher
import java.text.NumberFormat


class CurrencyTextWatcher : TextWatcher {
    var mEditing = false

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        if (!mEditing) {
            mEditing = true
            val digits = s.toString().replace("\\D".toRegex(), "")
            val nf = NumberFormat.getCurrencyInstance()
            try {
                val formatted = nf.format(digits.toDouble() / 100)
                s.replace(0, s.length, formatted)
            } catch (nfe: NumberFormatException) {
                s.clear()
            }
            mEditing = false
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}