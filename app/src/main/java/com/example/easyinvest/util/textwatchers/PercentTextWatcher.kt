package com.example.easyinvest.util.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.easyinvest.util.justDigits
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

class PercentTextWatcher(editText: EditText) : TextWatcher {


    private val MONEY_REAL_PREFIX = "R$"

    private val editTextWeakReference: WeakReference<EditText> = WeakReference(editText)

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        //Método não utilizado.
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //Método não utilizado.
    }

    override fun afterTextChanged(editable: Editable) {

        val editText = editTextWeakReference.get() ?: return

        editText.removeTextChangedListener(this)

        val typedText = editable.toString().justDigits()

        val parsed: BigDecimal

        parsed = if (typedText.isEmpty()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(typedText).movePointLeft(2)
        }

        val formatted = String.format("%s%%", parsed.toString())
        editText.setText(formatted)
        editText.setSelection(formatted.length-1)
        editText.addTextChangedListener(this)
    }

    fun BigDecimal.moneyFormat(): String {
        val currencyInstance = NumberFormat.getCurrencyInstance()
        val currencySymbol = currencyInstance.currency.symbol
        val numerFormatted = currencyInstance.format(this)
        return if (numerFormatted.contains("\\s".toRegex())) {
            numerFormatted
        } else {
            numerFormatted.replace(currencySymbol, currencySymbol.plus(" "))
        }
    }

    fun String.moneyFormat() =
        (NumberFormat.getInstance() as DecimalFormat)
            .apply { this.isParseBigDecimal = true }
            .parse(this.removePrefix(MONEY_REAL_PREFIX).trim()) as BigDecimal

}