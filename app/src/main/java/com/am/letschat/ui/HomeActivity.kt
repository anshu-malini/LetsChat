package com.am.letschat.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.am.letschat.adapter.HomeAdapter
import com.am.letschat.data.repository.Repository
import com.am.letschat.databinding.ActionbarTitleTvBinding
import com.am.letschat.databinding.HomeActivityBinding
import com.am.letschat.utils.*
import com.am.letschat.viewmodel.MainViewModel
import com.am.letschat.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding
    private lateinit var viewModel: MainViewModel

    private var actionBar: ActionBar? = null

    @Inject
    lateinit var repository: Repository

    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        setToolbar()

        initViewModel()
        initDB()
        setListener()
    }

    private fun initViewModel() {
        val vmFactory = ViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)
    }


    private fun initDB() {

        val isFirstInit: Boolean = getPref(PREFS_IS_FIRST_TIME) ?: true
        if (isFirstInit) {
            viewModel.initDB()
            savePref(PREFS_IS_FIRST_TIME, false)
        }
        setObserver()
    }

    private fun setToolbar() {
        actionBar = supportActionBar
        actionBar?.apply {
            customView = ActionbarTitleTvBinding.inflate(layoutInflater).root
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        }
    }

    private fun setListener() {
        adapter.onItemClick =
            { user ->
                startActivity(
                    Intent(this, DetailsActivity::class.java)
                        .putExtra(INTENT_USER, user)
                )
            }
    }

    private fun setObserver() {
        viewModel.getRecord()?.observe(this, Observer { usersChat ->
            usersChat?.let {
                adapter.setItemList(usersChat)
            }
        })
    }
}