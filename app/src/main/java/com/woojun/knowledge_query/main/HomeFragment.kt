package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentHomeBinding
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val list = arrayListOf(
        BookInfo("Catcher in the Rye", "J.D. Salinger", R.drawable.test_1, BookType.Category),
        BookInfo("To Kill a Mockingbird", "Harper Lee", R.drawable.test_2, BookType.Category),
        BookInfo("1984", "George Orwell", R.drawable.test_3, BookType.Category),
        BookInfo("Brave New World", "Aldous Huxley", R.drawable.test_4, BookType.Category),
        BookInfo("Moby Dick", "Herman Melville", R.drawable.test_5, BookType.Category),
        BookInfo("War and Peace", "Leo Tolstoy", R.drawable.test_6, BookType.Category),
        BookInfo("The Great Gatsby", "F. Scott Fitzgerald", R.drawable.test_7, BookType.Category),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            categoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            categoryList.adapter = BookRecyclerAdapter(list)

            categoryList.addItemDecoration(SpacesItemDecoration(Space(0,  14, 0, 0, 16, 16), Category))

            myList.layoutManager = GridLayoutManager(requireContext(), 2)
            myList.adapter = BookRecyclerAdapter(list.subList(3, 7))

            myList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 16, 16), BookType.My))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}