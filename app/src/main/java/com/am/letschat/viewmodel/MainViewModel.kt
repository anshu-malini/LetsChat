package com.am.letschat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.letschat.data.model.UserChats
import com.am.letschat.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    var dbUserData: MutableLiveData<MutableList<UserChats>>? =
        MutableLiveData()

    fun initDB() {
        viewModelScope.launch {
            dbUserData?.postValue(repository.dBFirstInit())
        }
    }

    fun getRecord(): LiveData<MutableList<UserChats>>? {
        viewModelScope.launch {
            dbUserData?.postValue(repository.getAllData())
        }
        return dbUserData
    }
}
