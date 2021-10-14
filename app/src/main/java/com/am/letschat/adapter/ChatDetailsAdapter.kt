package com.am.letschat.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.letschat.data.model.ChatItem
import com.am.letschat.databinding.ChatDetailsListItemBinding
import com.am.letschat.utils.MESSAGE_TYPE_IN
import com.am.letschat.utils.MESSAGE_TYPE_OUT
import com.am.letschat.utils.PARSED_DATE_FORMAT
import com.am.letschat.utils.isSameDate
import javax.inject.Inject

class ChatDetailsAdapter @Inject constructor() :
    RecyclerView.Adapter<ChatDetailsAdapter.DetailsViewHolder>() {
    private var aList = mutableListOf<ChatItem>()

    fun setItemList(aList: MutableList<ChatItem>) {
        this.aList = aList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatDetailsListItemBinding.inflate(inflater, parent, false)

        return DetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val item = aList[position]

        holder.binding.dateLayout.visibility = VISIBLE
        val parseDate = item.timestamp?.let { PARSED_DATE_FORMAT.format(it).split("/") }
        val messageDate = parseDate?.get(0) ?: "TODAY"
        val messageTime = parseDate?.get(1) ?: ""

        when {
            position == 0 -> {
                holder.binding.dateLayout.visibility = VISIBLE
                holder.binding.dateTV.text = messageDate
            }
            isSameDate(
                item.timestamp,
                aList[position - 1].timestamp
            ) -> holder.binding.dateLayout.visibility = GONE
            else -> {
                holder.binding.dateLayout.visibility = VISIBLE
                holder.binding.dateTV.text = messageDate
            }
        }
        when {
            item.direction.equals(MESSAGE_TYPE_OUT) -> {
                holder.binding.receiverLayout.visibility =
                    GONE
                holder.binding.senderLayout.visibility = VISIBLE
                val senderBind = holder.binding.includeSenderLayout
                senderBind.senderMsgTV.text = item.message
                senderBind.timeRightTV.text = messageTime
            }
            item.direction.equals(MESSAGE_TYPE_IN) -> {
                holder.binding.receiverLayout.visibility =
                    VISIBLE
                holder.binding.senderLayout.visibility = GONE
                val senderBind = holder.binding.includeReceiverLayout
                senderBind.receiverMsgTV.text = item.message
                senderBind.timeLeftTV.text = messageTime
            }
        }
    }

    override fun getItemCount(): Int {
        return aList.size
    }

    inner class DetailsViewHolder(val binding: ChatDetailsListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

