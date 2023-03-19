package com.example.android.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.core.WebClient
import com.example.android.item.Item

class SearchPagingSource(
    private val webClient: WebClient,
    private val query: String
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: 1

        return try {
            val searchImageItems = try {
                webClient.searchImage(query, page, params.loadSize / 2).documents.map {
                    Item(
                        imageUrl = it.thumbnailUrl,
                        datetime = it.datetime
                    )
                }
            } catch (e: Exception) {
                listOf()
            }

            val searchVClipItems = try {
                webClient.searchVClip(query, page, params.loadSize / 2).documents.map {
                    Item(
                        imageUrl = it.thumbnail,
                        datetime = it.datetime
                    )
                }
            } catch (e: Exception) {
                listOf()
            }

            val data = searchImageItems.plus(searchVClipItems).sortedByDescending {
                it.datetime
            }

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}