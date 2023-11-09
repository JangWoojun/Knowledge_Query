package com.woojun.knowledge_query.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentHomeBinding
import com.woojun.knowledge_query.util.AppDatabase
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.BookType.*
import com.woojun.knowledge_query.util.BookViewModel
import com.woojun.knowledge_query.util.MyApplication
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var viewModel: BookViewModel

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
        database = Firebase.database.reference

        binding.apply {

            switchMode.isChecked = MyApplication.prefs.isDarkMode()
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                Handler().postDelayed({
                    activity?.let { activity ->
                        val nightMode = if (isChecked) {
                            MyApplication.prefs.setMode(true)
                            AppCompatDelegate.MODE_NIGHT_YES
                        } else {
                            MyApplication.prefs.setMode(false)
                            AppCompatDelegate.MODE_NIGHT_NO
                        }
                        (activity as? AppCompatActivity)?.delegate?.localNightMode = nightMode
                        activity.recreate()
                    }
                }, 250)
            }

            viewModel = ViewModelProvider(requireActivity())[BookViewModel::class.java]
            viewModel.fetchData()
            viewModel.dataList.observe(requireActivity()) { list ->
                val adapter = BookRecyclerAdapter(list, Category)

                categoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter.filterByCategory(ClassicNovel)
                categoryList.adapter = adapter
            }
            categoryList.addItemDecoration(SpacesItemDecoration(Space(0,  14, 0, 0, 0, 14, 16, 0), Category))

            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(requireContext())
                var list = db?.userDao()!!.getUser().myBookList

                if (list.size > 4) {
                    list = list.slice(0..3).toMutableList()
                }
                val adapter = BookRecyclerAdapter(list, My)

                recentlyList.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter.filterByCategory(My)
                recentlyList.adapter = adapter
            }
            recentlyList.addItemDecoration(SpacesItemDecoration(Space(0,  10, 10, 16, 0, 10, 10, 0), My))

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

            bookNameInput.setOnEditorActionListener { _, _, _ ->
                val bundle = Bundle()
                bundle.putString("book name", bookNameInput.text.toString())
                findNavController().navigate(R.id.action_home_to_searchFragment, bundle)

                true
            }

            bookNameLayout.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            container.setOnTouchListener { it, _ ->
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                it.clearFocus()
                true
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

            val adapter = viewModel.dataList.value?.let { BookRecyclerAdapter(it, Category) }

            if (adapter != null) {
                val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {

                    classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    when(type) {
                        ClassicNovel -> {
                            adapter.filterByCategory(ClassicNovel)
                            categoryList.adapter = adapter

                            classicNovelLine.visibility = View.VISIBLE
                        }
                        FairyTale -> {
                            adapter.filterByCategory(FairyTale)
                            categoryList.adapter = adapter

                            fairyTaleLine.visibility = View.VISIBLE
                        }
                        Poem -> {
                            adapter.filterByCategory(Poem)
                            categoryList.adapter = adapter

                            poemLine.visibility = View.VISIBLE
                        }
                        SocialNews -> {
                            adapter.filterByCategory(SocialNews)
                            categoryList.adapter = adapter

                            socialNewsLine.visibility = View.VISIBLE
                        }
                        ItNews -> {
                            adapter.filterByCategory(ItNews)
                            categoryList.adapter = adapter

                            itNewsLine.visibility = View.VISIBLE
                        }
                        DailyNews -> {
                            adapter.filterByCategory(DailyNews)
                            categoryList.adapter = adapter

                            dailyNewsLine.visibility = View.VISIBLE
                        }
                        else -> {

                        }
                    }
                } else {
                    classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                    fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                    poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                    socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                    itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                    dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))

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
    }

    private fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

}