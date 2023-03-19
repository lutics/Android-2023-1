package com.example.android.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.android.component.UserSettingsModel
import com.example.android.item.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    val result = query.flatMapLatest { repository.search(it) }.cachedIn(viewModelScope)

    fun search(
        keyword: String
    ) {
        query.value = keyword
    }

    fun toggle(
        key: String,
        function: () -> Unit,
    ) = viewModelScope.launch {
        repository.toggle(key)

        function()
    }
}