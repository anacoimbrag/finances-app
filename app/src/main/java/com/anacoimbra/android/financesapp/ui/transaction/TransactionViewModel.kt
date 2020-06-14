package com.anacoimbra.android.financesapp.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.anacoimbra.android.financesapp.helpers.emitResource
import com.anacoimbra.android.financesapp.model.Category
import com.anacoimbra.android.financesapp.network.Resource
import com.anacoimbra.android.financesapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers

class TransactionViewModel(private val repository: CategoryRepository) : ViewModel() {

    val categories: LiveData<Resource<List<Category>>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emitResource(repository.getCategories())
        }

}