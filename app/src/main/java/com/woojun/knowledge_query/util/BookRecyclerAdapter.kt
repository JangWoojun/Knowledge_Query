package com.woojun.knowledge_query.util

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.BookItemBinding

class BookRecyclerAdapter(private val bookList: List<BookInfo>, private val type: BookType): RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {

    private var filteredBookList: List<BookInfo> = bookList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder{
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding).also { handler->
            binding.apply {
                bookItem.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable("book info", filteredBookList[handler.adapterPosition])

                    if (type == BookType.Category) {
                        parent.findNavController().navigate(R.id.bookInfoFragment, bundle)
                    } else {
                        parent.findNavController().navigate(R.id.bookReaderFragment, bundle)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredBookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(filteredBookList[position], type)
    }

    fun filterByCategory(category: BookType) {
        filteredBookList = bookList.filter { it.category == category }
        notifyDataSetChanged()
    }

    class BookViewHolder(private val binding: BookItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookInfo: BookInfo, type: BookType) {
            binding.apply {
                title.text = bookInfo.title
                writer.text = bookInfo.writer
                image.setImageResource(bookInfo.image)

                if (type != BookType.Category) {
                    title.gravity = Gravity.CENTER
                    writer.gravity = Gravity.CENTER
                }
            }
        }
    }

}