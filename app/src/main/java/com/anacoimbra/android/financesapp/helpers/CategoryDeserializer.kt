package com.anacoimbra.android.financesapp.helpers

import com.anacoimbra.android.financesapp.R
import com.anacoimbra.android.financesapp.model.Category
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CategoryDeserializer : JsonDeserializer<Category> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Category {
        val jsonObject = json?.asJsonObject
        val icon = jsonObject?.get("image")?.asString
        return Category(
            id = jsonObject?.get("id")?.asString,
            name = jsonObject?.get("name")?.asString,
            icon = icon.getResId(R.drawable::class.java)
        )
    }
}