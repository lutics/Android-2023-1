package com.example.android.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.item.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    val list = repository.favorites.flow

    fun toggle(
        key: String
    ) = viewModelScope.launch {
        repository.toggle(key)
    }
}