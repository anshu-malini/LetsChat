package com.am.letschat.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.am.letschat.R
import com.am.letschat.adapter.ChatDetailsAdapter
import com.am.letschat.data.model.UserChats
import com.am.letschat.data.repository.Repository
import com.am.letschat.databinding.ActionbarTitleTvBinding
import com.am.letschat.databinding.ChatDetailsLayoutBinding
import com.am.letschat.utils.INTENT_USER
import com.am.letschat.viewmodel.DetailsViewModel
import com.am.letschat.viewmodel.DetailsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ChatDetailsLayoutBinding
    lateinit var viewModel: DetailsViewModel
    private val adapter = ChatDetailsAdapter()
    private var user: UserChats? = null
    private var actionBar: ActionBar? = null

    @Inject
    lateinit var repository: Repository
    var userId: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setBackgroundDrawableResource(R.mipmap.bg);
        binding.chatRecyclerView.adapter = adapter
        getIntentExtraValues()
        setToolbar()
        initViewModel()
        setObserver()
        binding.sendImg.setOnClickListener {
            setSendTextListener()
        }
    }

    private fun setToolbar() {
        actionBar = supportActionBar
        actionBar?.apply {
            val view = ActionbarTitleTvBinding.inflate(layoutInflater)
            view.toolbarTV.text = user?.userName
            customView = view.root
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getIntentExtraValues() {
        if (intent.extras != null) {
            user = intent.getParcelableExtra(INTENT_USER)
            userId = user?._userId
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            DetailsViewModelFactory(repository)
        ).get(DetailsViewModel::class.java)
    }

    private fun setObserver() {
        userId?.let {
            viewModel.getChats(it)?.observe(this, Observer { chatList ->
                chatList?.let {
                    adapter.setItemList(chatList)
                    binding.chatRecyclerView.layoutManager?.scrollToPosition(chatList.size - 1)
                }
            })
        }
    }

    private fun setSendTextListener() {
        val textMessage = binding.typeMsgET.text?.trim()
        binding.typeMsgET.text.clear()
        if (!textMessage.isNullOrEmpty()) {
            user?.let {
                viewModel.sendMessage(it, textMessage.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}