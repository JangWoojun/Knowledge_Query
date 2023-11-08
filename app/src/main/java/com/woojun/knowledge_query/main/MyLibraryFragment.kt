package com.woojun.knowledge_query.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentMyLibraryBinding
import com.woojun.knowledge_query.util.AppDatabase
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyLibraryFragment : Fragment() {
    private var _binding: FragmentMyLibraryBinding? = null
    private val binding get() = _binding!!

    private var isMyChecked: Boolean = true
    private var isBookmarkChecked: Boolean = false
    private var isClassicNovelChecked: Boolean = false
    private var isFairyTaleChecked: Boolean = false
    private var isPoemChecked: Boolean = false
    private var isSocialNewsChecked: Boolean = false
    private var isItNewsChecked: Boolean = false
    private var isDailyNewsChecked: Boolean = false

    private val selectCategorySet = mutableSetOf<BookType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(requireContext())
                val list = db?.userDao()!!.getUser().myBookList
                val adapter = BookRecyclerAdapter(list, BookType.Category)

                withContext(Dispatchers.Main) {
                    myButtonBackground.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.primary)
                    myButtonText.setTextColor(resources.getColor(R.color.white, null))

                    bookList.layoutManager = GridLayoutManager(requireContext(), 2)
                    bookList.adapter = adapter

                    selectCategorySet.add(BookType.My)
                    adapter.selectButtonByCategory(selectCategorySet)
                }

            }

            searchButton.setOnClickListener {
                textView.visibility = View.GONE
                searchButton.visibility = View.GONE
                searchInputButton.visibility = View.VISIBLE

                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            searchInputButton.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            myButton.setOnClickListener {
                isMyChecked = !isMyChecked
                selectButton(myButtonBackground, myButtonText, isMyChecked, BookType.My)
            }

            bookmarkButton.setOnClickListener {
                isBookmarkChecked = !isBookmarkChecked
                selectButton(bookmarkButtonBackground, bookmarkButtonText, isBookmarkChecked, BookType.Bookmark)
            }

            classicNovelButton.setOnClickListener {
                isClassicNovelChecked = !isClassicNovelChecked
                selectButton(classicNovelButtonBackground, classicNovelButtonText, isClassicNovelChecked, BookType.ClassicNovel)
            }

            fairyTaleButton.setOnClickListener {
                isFairyTaleChecked = !isFairyTaleChecked
                selectButton(fairyTaleButtonBackground, fairyTaleButtonText, isFairyTaleChecked, BookType.FairyTale)
            }

            poemButton.setOnClickListener {
                isPoemChecked = !isPoemChecked
                selectButton(poemButtonBackground, poemButtonText, isPoemChecked, BookType.Poem)
            }

            socialNewsButton.setOnClickListener {
                isSocialNewsChecked = !isSocialNewsChecked
                selectButton(socialNewsButtonBackground, socialNewsButtonText, isSocialNewsChecked, BookType.SocialNews)
            }

            itNewsButton.setOnClickListener {
                isItNewsChecked = !isItNewsChecked
                selectButton(itNewsButtonBackground, itNewsButtonText, isItNewsChecked, BookType.ItNews)
            }

            dailyNewsButton.setOnClickListener {
                isDailyNewsChecked = !isDailyNewsChecked
                selectButton(dailyNewsButtonBackground, dailyNewsButtonText, isDailyNewsChecked, BookType.DailyNews)
            }

            bookList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 0, 16, 16, 0), BookType.My))

            bookNameLayout.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            container.setOnTouchListener { it, _ ->
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                textView.visibility = View.VISIBLE
                searchButton.visibility = View.VISIBLE
                searchInputButton.visibility = View.GONE

                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    private fun selectButton(view: View, text: TextView, type: Boolean, category: BookType) {
        binding.apply {
            val colorChangeDuration = 250L
            val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(colorChangeDuration / 2)
            val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(colorChangeDuration / 2)

            fadeOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (type) {
                        view.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.primary)
                        text.setTextColor(resources.getColor(R.color.white, null))
                        selectCategorySet.add(category)
                    }
                    else {
                        view.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.toggle_backgroundColor)
                        text.setTextColor(resources.getColor(R.color.text_black, null))
                        selectCategorySet.remove(category)
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = AppDatabase.getDatabase(requireContext())
                        val list = db?.userDao()!!.getUser().myBookList

                        val adapter = BookRecyclerAdapter(list, BookType.Category)

                        withContext(Dispatchers.Main) {
                            bookList.layoutManager = GridLayoutManager(requireContext(), 2)
                            bookList.adapter = adapter
                            adapter.selectButtonByCategory(selectCategorySet)
                        }

                    }
                    fadeIn.start()
                }
            })

            fadeOut.start()

            view.animate().scaleX(0.9f).scaleY(0.9f).setDuration(150).withEndAction {
                view.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
            }.start()
        }
    }

}