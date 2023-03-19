package com.example.android.item

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.component.UserSettingsModel
import com.example.android.core.WebClient
import com.example.android.search.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val webClient: WebClient
) {

    val favorites = UserSettingsModel.favorites

    fun search(
        query: String
    ): Flow<PagingData<Item>> = Pager(
        PagingConfig(pageSize = 10)
    ) {
        SearchPagingSource(webClient, query)
    }.flow

    suspend fun toggle(key: String) {
        val list = UserSettingsModel.favorites.value.toMutableList()

        if (list.contains(key)) {
            list.remove(key)
        } else {
            list.add(key)
        }

        favorites.update(
            list.toSet()
        )
    }
}