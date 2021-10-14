package com.am.letschat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.letschat.data.model.UserChats
import com.am.letschat.databinding.HomeListItemBinding
import com.am.letschat.utils.PARSED_DATE_FORMAT
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {
    var onItemClick: ((UserChats?) -> Unit)? = null
    var aList = mutableListOf<UserChats>()

    fun setItemList(aList: MutableList<UserChats>) {
        this.aList = aList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeListItemBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = aList[position]
        holder.binding.name.text = "${item.userName}"
        holder.binding.message.text = "${item.lastMessage}"
        val timeStr = item.lastMsgTimestamp?.let { PARSED_DATE_FORMAT.format(it).split("/") }
        holder.binding.timeTV.text = timeStr?.get(1) ?: ""
    }

    override fun getItemCount(): Int {
        return aList.size
    }

    inner class MainViewHolder(val binding: HomeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemRoot.setOnClickListener {
                onItemClick?.invoke(
                    aList[adapterPosition]
                )
            }
        }
    }
}

