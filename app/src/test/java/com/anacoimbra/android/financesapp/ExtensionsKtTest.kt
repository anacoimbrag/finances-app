package com.anacoimbra.android.financesapp

import com.anacoimbra.android.financesapp.helpers.toMoneyString
import com.anacoimbra.android.financesapp.helpers.toShortDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

internal class ExtensionsKtTest {

    @Test
    fun `when toMoneyString is called with a valid double, then it should return BRL currency indicator with the value with comma as decimal separator`() {
        val value = 10.0
        assertEquals("R$ 10,00", value.toMoneyString())
    }

    @Test
    fun `when toMoneyString is called with a null double, then it should return empty string`() {
        val value: Double? = null
        assertEquals("", value.toMoneyString())
    }

    @Test
    fun `when toShortDate is called with a valid date, then it should return a string with short date format`() {
        val date = Date.from(Instant.parse("2020-01-01T10:15:30.00Z"))
        assertEquals("01/01/20", date.toShortDate())
    }

    @Test
    fun `when toShortDate is called with a null date, then it should return empty string`() {
        val value: Date? = null
        assertEquals("", value.toShortDate())
    }
}