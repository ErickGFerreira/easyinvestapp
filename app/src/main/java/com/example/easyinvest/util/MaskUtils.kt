package com.example.easyinvest.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object MaskUtils {

    const val DATE_MASK: String = "##/##/####"
    const val PERCENTAGE_MASK: String = "#%"

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
            .replace("[)]".toRegex(), "").replace(" ".toRegex(), "")
            .replace(",".toRegex(), "")
    }

    fun isASign(c: Char): Boolean {
        return c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' '
    }

    fun mask(mask: String, text: String): String {
        var i = 0
        var mascara = ""
        for (m in mask.toCharArray()) {
            if (m != '#') {
                mascara += m
                continue
            }
            mascara += try {
                text[i]
            } catch (e: Exception) {
                break
            }
            i++
        }
        return mascara
    }

    fun insert(mask: String, ediTxt: EditText?): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var index = 0
                for (i in 0 until mask.length) {
                    val m = mask[i]
                    if (m != '#') {
                        if (index == str.length && str.length < old.length) {
                            continue
                        }
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[index]
                    } catch (e: Exception) {
                        break
                    }
                    index++
                }
                if (mascara.length > 0) {
                    var last_char = mascara[mascara.length - 1]
                    var hadSign = false
                    while (isASign(last_char) && str.length == old.length) {
                        mascara = mascara.substring(0, mascara.length - 1)
                        last_char = mascara[mascara.length - 1]
                        hadSign = true
                    }
                    if (mascara.length > 0 && hadSign) {
                        mascara = mascara.substring(0, mascara.length - 1)
                    }
                }
                isUpdating = true
                ediTxt?.setText(mascara)
                ediTxt?.setSelection(mascara.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        }
    }
}