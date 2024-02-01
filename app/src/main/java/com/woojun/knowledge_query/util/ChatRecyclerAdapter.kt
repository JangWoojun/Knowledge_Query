package com.woojun.knowledge_query.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woojun.knowledge_query.databinding.ChatItemBinding

class ChatRecyclerAdapter(private val messages: MutableList<Chat>): RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding).also { handler->
            binding.apply {
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    fun updateItem(position: Int, text: String, isEnd: Boolean) {
        messages[position].message = text
        messages[position].isAnswerEnd = isEnd
        notifyItemChanged(position)
    }

    fun addItem(item: Chat) {
        messages.add(item)
        notifyItemInserted(messages.size - 1)
    }


    class ChatViewHolder(private val binding: ChatItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.apply {
                if (chat.chatType == ChatType.User) {
                    myMessage.text = chat.message
                    myChatBackground.visibility = View.VISIBLE
                    botChatBackground.visibility = View.GONE
                } else {
                    botMessage.text = chat.message
                    botChatBackground.visibility = View.VISIBLE
                    myChatBackground.visibility = View.GONE

                    if (chat.isAnswerEnd) {
                        loadingProgress.visibility = View.GONE
                    } else {
                        loadingProgress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}