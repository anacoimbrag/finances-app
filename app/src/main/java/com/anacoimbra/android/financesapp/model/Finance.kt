package com.anacoimbra.android.financesapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Transaction(
    @SerializedName("type")
    var type: TransactionType?,
    @SerializedName("value")
    var value: Double?,
    @SerializedName("category")
    var category: Category?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("date")
    var date: Date?
)

enum class TransactionType {
    @SerializedName("INCOME")
    INCOME,

    @SerializedName("EXPENSE")
    EXPANSE
}