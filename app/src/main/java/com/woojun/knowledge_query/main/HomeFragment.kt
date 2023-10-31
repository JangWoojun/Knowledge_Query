package com.woojun.knowledge_query.main

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentHomeBinding
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.BookType.*
import com.woojun.knowledge_query.util.Preference
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val list = arrayListOf(
        BookInfo("Catcher in the Rye1", "J.D. Salinger", R.drawable.test_1, ClassicNovel),
        BookInfo("Catcher in the Rye2", "J.D. Salinger", R.drawable.test_1, ClassicNovel),
        BookInfo("Catcher in the Rye3", "J.D. Salinger", R.drawable.test_1, ClassicNovel),
        BookInfo("Catcher in the Rye4", "J.D. Salinger", R.drawable.test_1, ClassicNovel),
        BookInfo("Catcher in the Rye5", "J.D. Salinger", R.drawable.test_1, ClassicNovel),

        BookInfo("To Kill a Mockingbird1", "Harper Lee", R.drawable.test_2, FairyTale),
        BookInfo("To Kill a Mockingbird2", "Harper Lee", R.drawable.test_2, FairyTale),
        BookInfo("To Kill a Mockingbird3", "Harper Lee", R.drawable.test_2, FairyTale),
        BookInfo("To Kill a Mockingbird4", "Harper Lee", R.drawable.test_2, FairyTale),
        BookInfo("To Kill a Mockingbird5", "Harper Lee", R.drawable.test_2, FairyTale),

        BookInfo("1984 1", "George Orwell1", R.drawable.test_3, Poem),
        BookInfo("1984 2", "George Orwell2", R.drawable.test_3, Poem),
        BookInfo("1984 3", "George Orwell3", R.drawable.test_3, Poem),
        BookInfo("1984 4", "George Orwell4", R.drawable.test_3, Poem),
        BookInfo("1984 5", "George Orwell5", R.drawable.test_3, Poem),

        BookInfo("Brave New World1", "Aldous Huxley", R.drawable.test_4, SocialNews),
        BookInfo("Brave New World2", "Aldous Huxley", R.drawable.test_4, SocialNews),
        BookInfo("Brave New World3", "Aldous Huxley", R.drawable.test_4, SocialNews),
        BookInfo("Brave New World4", "Aldous Huxley", R.drawable.test_4, SocialNews),
        BookInfo("Brave New World5", "Aldous Huxley", R.drawable.test_4, SocialNews),

        BookInfo("Moby Dick1", "Herman Melville", R.drawable.test_5, ItNews),
        BookInfo("Moby Dick2", "Herman Melville", R.drawable.test_5, ItNews),
        BookInfo("Moby Dick3", "Herman Melville", R.drawable.test_5, ItNews),
        BookInfo("Moby Dick4", "Herman Melville", R.drawable.test_5, ItNews),
        BookInfo("Moby Dick5", "Herman Melville", R.drawable.test_5, ItNews),

        BookInfo("War and Peace1", "Leo Tolstoy", R.drawable.test_6, DailyNews),
        BookInfo("War and Peace2", "Leo Tolstoy", R.drawable.test_6, DailyNews),
        BookInfo("War and Peace3", "Leo Tolstoy", R.drawable.test_6, DailyNews),
        BookInfo("War and Peace4", "Leo Tolstoy", R.drawable.test_6, DailyNews),
        BookInfo("War and Peace5", "Leo Tolstoy", R.drawable.test_6, DailyNews),

        BookInfo("The Great Gatsby1", "F. Scott Fitzgerald", R.drawable.test_7, My),
        BookInfo("The Great Gatsby2", "F. Scott Fitzgerald", R.drawable.test_7, My),
        BookInfo("The Great Gatsby3", "F. Scott Fitzgerald", R.drawable.test_7, My),
        BookInfo("The Great Gatsby4", "F. Scott Fitzgerald", R.drawable.test_7, My),

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

            val adapter1 = BookRecyclerAdapter(list)

            categoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter1.filterByCategory(ClassicNovel)
            categoryList.adapter = adapter1
            categoryList.addItemDecoration(SpacesItemDecoration(Space(0,  14, 0, 0, 16, 16), Category))

            val adapter2 = BookRecyclerAdapter(list)

            myList.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter2.filterByCategory(My)
            myList.adapter = adapter2
            myList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 16, 16), My))

            classicNovelButton.setOnClickListener {
                categoryButtonClick(ClassicNovel)
            }

            fairyTaleButton.setOnClickListener {
                categoryButtonClick(FairyTale)
            }

            poemButton.setOnClickListener {
                categoryButtonClick(Poem)
            }

            socialNewsButton.setOnClickListener{
                categoryButtonClick(SocialNews)
            }

            itNewsButton.setOnClickListener {
                categoryButtonClick(ItNews)
            }

            dailyNewsButton.setOnClickListener {
                categoryButtonClick(DailyNews)
            }

            bookNameInput.setOnEditorActionListener { _, actionId, event ->
                val bundle = Bundle()
                bundle.putString("book name", bookNameInput.text.toString())
                findNavController().navigate(R.id.action_home_to_searchFragment, bundle)

                true
            }

            bookNameLayout.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun categoryButtonClick(type: BookType) {
        binding.apply {
            classicNovelLine.visibility = View.INVISIBLE
            fairyTaleLine.visibility = View.INVISIBLE
            poemLine.visibility = View.INVISIBLE
            socialNewsLine.visibility = View.INVISIBLE
            itNewsLine.visibility = View.INVISIBLE
            dailyNewsLine.visibility = View.INVISIBLE

            classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
            fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
            poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
            socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
            itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
            dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))

            val adapter = BookRecyclerAdapter(list)

            when(type) {
                ClassicNovel -> {
                    adapter.filterByCategory(ClassicNovel)
                    categoryList.adapter = adapter

                    classicNovelLine.visibility = View.VISIBLE
                    classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                FairyTale -> {
                    adapter.filterByCategory(FairyTale)
                    categoryList.adapter = adapter

                    fairyTaleLine.visibility = View.VISIBLE
                    fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                Poem -> {
                    adapter.filterByCategory(Poem)
                    categoryList.adapter = adapter

                    poemLine.visibility = View.VISIBLE
                    poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                SocialNews -> {
                    adapter.filterByCategory(SocialNews)
                    categoryList.adapter = adapter

                    socialNewsLine.visibility = View.VISIBLE
                    socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                ItNews -> {
                    adapter.filterByCategory(ItNews)
                    categoryList.adapter = adapter

                    itNewsLine.visibility = View.VISIBLE
                    itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                DailyNews -> {
                    adapter.filterByCategory(DailyNews)
                    categoryList.adapter = adapter

                    dailyNewsLine.visibility = View.VISIBLE
                    dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                }
                else -> {

                }
            }

        }
    }

}