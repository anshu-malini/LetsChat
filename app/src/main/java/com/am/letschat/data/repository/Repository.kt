package com.am.letschat.data.repository

import com.am.letschat.data.model.ChatItem
import com.am.letschat.data.model.UserChats
import com.am.letschat.data.room.MobileDataDao
import com.am.letschat.utils.MESSAGE_TYPE_IN
import com.am.letschat.utils.MESSAGE_TYPE_OUT
import com.am.letschat.utils.getCurrentTime
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val localDataSource: MobileDataDao
) {
    suspend fun getAllData() =
            localDataSource.getAllRecords()

    suspend fun getSingleData(userId: Long) =
            localDataSource.getMessages(userId)

    suspend fun insertNewMessage(
        user: UserChats,
        sentText: String
    ): MutableList<ChatItem> {

        val chat1 = ChatItem().apply {
            name = user.userName
            message = sentText
            timestamp =getCurrentTime()
            direction = MESSAGE_TYPE_OUT
            _childUserId = user._userId
        }

        val chat2 = ChatItem().apply {
            name = user.userName
            message = sentText
            timestamp = getCurrentTime()
            direction = MESSAGE_TYPE_IN
            _childUserId = user._userId
        }

        user.lastMessage = chat2.message
        user.lastMsgTimestamp = chat2.timestamp

        withContext(Dispatchers.IO) {
            user.chatList.also {
                it?.add(
                    localDataSource.getMessage(
                        localDataSource.insertNewSingleChat(
                            chat1
                        )
                    )
                )
                it?.add(
                    localDataSource.getMessage(
                        localDataSource.insertNewSingleChat(
                            chat2
                        )
                    )
                )
            }
            localDataSource.updateUserData(user)
        }
        return getSingleData(user._userId)
    }

    suspend fun dBFirstInit(): MutableList<UserChats> {
        withContext(Dispatchers.IO) {
            localDataSource.deleteUsers()
            val user1 = localDataSource.getUser(localDataSource.insertNewData(UserChats().apply {
                userName = "John"
            }))
            val user2 = localDataSource.getUser(localDataSource.insertNewData(UserChats().apply {
                userName = "Kent"
            }))
            val user3 = localDataSource.getUser(localDataSource.insertNewData(UserChats().apply {
                userName = "Sam"
            }))

            /****/
            user1.also {
                val chat1 = ChatItem().apply {
                    name = it.userName
                    message = "Hi"
                    timestamp = getCurrentTime()
                    direction = MESSAGE_TYPE_IN
                    _childUserId = it._userId
                }
                val chat2 = ChatItem().apply {
                    name = it.userName
                    message = "Hey hi there"
                    timestamp = getCurrentTime()
                    direction = MESSAGE_TYPE_OUT
                    _childUserId = it._userId
                }
                val chat3 = ChatItem().apply {
                    name = it.userName
                    message = "How are you"
                    timestamp =getCurrentTime()
                    direction = MESSAGE_TYPE_IN
                    _childUserId = it._userId
                }
                it.chatList =
                    mutableListOf(
                        localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat1
                            )
                        ), localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat2
                            )
                        ), localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat3
                            )
                        )
                    )
                it.lastMessage = chat3.message
                it.lastMsgTimestamp = chat3.timestamp
            }
            user2.also {
                val chat1 = ChatItem().apply {
                    name = it.userName
                    message = "Hi"
                    timestamp =getCurrentTime()
                    direction = MESSAGE_TYPE_IN
                    _childUserId = it._userId
                }
                val chat2 = ChatItem().apply {
                    name = it.userName
                    message = "Hello"
                    timestamp =getCurrentTime()
                    direction = MESSAGE_TYPE_OUT
                    _childUserId = it._userId
                }
                it.chatList =
                    mutableListOf(
                        localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat1
                            )
                        ), localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat2
                            )
                        )
                    )
                it.lastMessage = chat2.message
                it.lastMsgTimestamp = chat2.timestamp
            }
            user3.also {
                val chat1 = ChatItem().apply {
                    name = it.userName
                    message = "Hi, how are you"
                    timestamp = getCurrentTime()
                    direction = MESSAGE_TYPE_IN
                    _childUserId = it._userId
                }
                val chat2 = ChatItem().apply {
                    name = it.userName
                    message = "Hello.. I'm good.."
                    timestamp = getCurrentTime()
                    direction = MESSAGE_TYPE_OUT
                    _childUserId = it._userId
                }
                it.chatList =
                    mutableListOf(
                        localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat1
                            )
                        ), localDataSource.getMessage(
                            localDataSource.insertNewSingleChat(
                                chat2
                            )
                        )
                    )
                it.lastMessage = chat2.message
                it.lastMsgTimestamp = chat2.timestamp
            }
            /****/
            localDataSource.updateUserData(user1)
            localDataSource.updateUserData(user2)
            localDataSource.updateUserData(user3)
            /****/

        }
        return localDataSource.getAllRecords()
    }
}
