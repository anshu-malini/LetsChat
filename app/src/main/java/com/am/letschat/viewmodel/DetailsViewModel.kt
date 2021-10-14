package com.am.letschat.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.am.letschat.data.model.ChatItem
import com.am.letschat.data.model.UserChats
import com.am.letschat.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var chatsList: MutableLiveData<MutableList<ChatItem>>? = MutableLiveData()

    fun getChats(userId: Long): LiveData<MutableList<ChatItem>>? {
        viewModelScope.launch {
            chatsList?.postValue(repository.getSingleData(userId))
        }
        return chatsList
    }


    fun sendMessage(user: UserChats, newMessage: String) {
        viewModelScope.launch {
            chatsList?.postValue(repository.insertNewMessage(user, newMessage))
        }
    }
}