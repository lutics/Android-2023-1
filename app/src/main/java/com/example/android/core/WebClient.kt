package com.example.android.core

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WebClient {

    class Response {

        class Search {

            data class Image(
                val documents: List<Document>,
                val meta: Meta
            ) {

                data class Document(
                    val thumbnailUrl: String,
                    val datetime: Date
                )
            }

            data class VClip(
                val documents: List<Document>,
                val meta: Meta
            ) {

                data class Document(
                    val thumbnail: String,
                    val datetime: Date
                )
            }

            data class Meta(
                val isEnd: Boolean,
                val pageableCount: Int,
                val totalCount: Int
            )
        }
    }

    @GET("/v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response.Search.Image

    @GET("/v2/search/vclip")
    suspend fun searchVClip(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response.Search.VClip
}