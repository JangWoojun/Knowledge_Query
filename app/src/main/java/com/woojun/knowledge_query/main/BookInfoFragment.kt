package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentBookInfoBinding
import com.woojun.knowledge_query.util.AppDatabase
import com.woojun.knowledge_query.util.BookInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookInfoFragment : Fragment() {
    private var _binding: FragmentBookInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val arguments = arguments
            val item = arguments?.getParcelable<BookInfo>("book info")

            image.setImageResource(item!!.image)
            title.text = item.title
            writer.text = item.writer
            overviewText.text = item.overview
            aboutAuthorText.text = item.aboutAuthor

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideBottomNavigation(false)
        _binding = null
    }

}