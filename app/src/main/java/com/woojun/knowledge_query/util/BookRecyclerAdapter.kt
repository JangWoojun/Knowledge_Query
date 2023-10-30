package com.woojun.knowledge_query.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woojun.knowledge_query.databinding.BookItemBinding

class BookRecyclerAdapter(private val bookList: List<BookInfo>): RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder{
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding).also { handler->
            binding.apply{

            }
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    class BookViewHolder(private val binding: BookItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookInfo: BookInfo) {
            binding.apply {
                title.text = bookInfo.title
                writer.text = bookInfo.writer
                image.setImageResource(bookInfo.image)
            }
        }
    }

}