package com.anacoimbra.android.financesapp.ui.transaction.adapter

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.anacoimbra.android.financesapp.model.Category

class CategoryAdapter(textView: AutoCompleteTextView) :
    ArrayAdapter<Category>(textView.context, android.R.layout.simple_dropdown_item_1line) {
    private var selectedPosition: Int = -1

    init {
        textView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
        }
    }

    fun setItems(items: List<Category>) =
        this.addAll(items)

    fun getSelected() = getItem(selectedPosition)
}