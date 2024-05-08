package com.shoxruxbek.wallpapers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.shoxruxbek.wallpapers.retrofit.RetrofitClient

class UserViewModel: ViewModel() {
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    val liveData = _searchQuery.switchMap { query ->
        Pager(PagingConfig(pageSize = 15)) {
            UserDataSource(RetrofitClient.apiService, "your_photo_string", query)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
