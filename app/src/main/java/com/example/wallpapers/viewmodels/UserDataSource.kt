package com.example.wallpapers.viewmodels

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpapers.models.Hit
import com.example.wallpapers.retrofit.RetrofitService

class UserDataSource(
    private val apiService: RetrofitService,
    private val photo: String,
    private val searchQuery: String
) : PagingSource<Int, Hit>() {

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        // Since we don't have a way to determine the key for refreshing,
        // returning null indicates that the PagingSource cannot currently support refreshing.
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        try {
            val nextPageNumber = params.key ?: 1
            val usersData = apiService.getListPhotos(
                "27240519-6e85e045b4edde1049de33f01",
                searchQuery,
                "photo",
                true,
                nextPageNumber,
                params.loadSize
            )
            return LoadResult.Page(usersData.hits, null, nextPageNumber + 1)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    // Rest of the code...
}
