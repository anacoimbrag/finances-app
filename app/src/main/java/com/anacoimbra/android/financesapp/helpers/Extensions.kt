package com.anacoimbra.android.financesapp.helpers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveDataScope
import com.anacoimbra.android.financesapp.network.Resource
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val defaultDateFormat: DateFormat by lazy {
    SimpleDateFormat.getDateInstance(
        SimpleDateFormat.SHORT,
        Locale.forLanguageTag("pt-BR")
    )
}

val defaultDatePattern by lazy {
    (defaultDateFormat as SimpleDateFormat).toLocalizedPattern()
        .replace(Regex("[dMy]"), "#")
        .plus("###")
}

suspend fun <T> LiveDataScope<Resource<T>>.emitResource(resource: Resource<T>) {
    emit(Resource.Loading())
    emit(resource)
}

fun Double?.toMoneyString(): String =
    this?.let { NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(this) }
        .orEmpty()

fun Date?.toShortDate() =
    this?.let { defaultDateFormat.format(it) }.orEmpty()

fun String?.getResId(c: Class<*>): Int {
    return try {
        val idField: Field = c.getDeclaredField(this.orEmpty())
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

fun EditText.setMoneyMask() {
    var current = ""
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                removeTextChangedListener(this)

                val cleanString = s.toString().replace(Regex("[R$,.]"), "").trim()

                val parsed = cleanString.toDouble()
                val formatted = parsed.div(100).toMoneyString()

                current = formatted
                setText(formatted)
                setSelection(formatted.length)

                addTextChangedListener(this)
            }
        }

    })
}

fun EditText.getMoneyValue() =
    text.toString().replace(Regex("[R$,.]"), "").trim().toDoubleOrNull()?.div(100.0)

fun EditText.setMask(mask: String) {
    this.addTextChangedListener(object : TextWatcher {
        private var isUpdating = false
        private var current = ""

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                val clean: String = s.toString().replace(Regex("[^\\d.]|\\."), "")
                var placeholder = ""
                if (isUpdating) {
                    current = clean
                    isUpdating = false
                    return
                }

                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && clean.length > current.length || m != '#' && clean.length < current.length && clean.length != i) {
                        placeholder += m
                        continue
                    }

                    try {
                        placeholder += clean[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                setText(placeholder)
                setSelection(placeholder.length)
            }
        }

    })
}

fun EditText.getDate(): Date? = defaultDateFormat.parse(this.text.toString())
