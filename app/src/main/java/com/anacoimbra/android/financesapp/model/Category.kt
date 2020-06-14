package com.anacoimbra.android.financesapp.model

import androidx.annotation.DrawableRes

data class Category(
    var id: String?,
    var name: String?,
    @DrawableRes var icon: Int
) {
    override fun toString(): String = name.orEmpty()
}