package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.woojun.knowledge_query.databinding.FragmentSearchBinding
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.BookViewModel
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val arguments = arguments
            val item = arguments?.getString("book name")

            viewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]
            viewModel.fetchData()
            viewModel.dataList.observe(viewLifecycleOwner) { list ->
                val adapter = BookRecyclerAdapter(list.filter { it.title.contains(item!!) }, BookType.Category)
                bookList.layoutManager = GridLayoutManager(requireContext(), 2)
                bookList.adapter = adapter
            }

            bookList.addItemDecoration(SpacesItemDecoration(Space(0,  10, 10, 16, 0, 10, 10, 0),
                BookType.My
            ))

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideBottomNavigation(false)
        _binding = null
    }

}