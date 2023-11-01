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
import androidx.appcompat.app.AppCompatDelegate
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
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val list = arrayListOf(
        BookInfo(
            "한강의 미래", "김영하",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "20세기 중반, 한강을 중심으로 벌어진 두 가족 간의 이야기. 시대의 변화와 그 속에서 사랑과 이별을 겪은 두 주인공의 모습이 생생하게 그려진다. 강 물결 속에 반영된 사람들의 삶과 그림자가 독자의 마음을 사로잡는다.",
            "김영하는 1980년대부터 활동하기 시작한 작가로서, 그의 작품은 현대 사회의 모습과 사람들의 내면을 깊이 있게 그려낸다. 대표작으로는 '하늘과 바람과 별과 시'가 있으며, 많은 독자들에게 사랑받고 있다."
        ),
        BookInfo(
            "마지막 눈물", "이문열",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "일제 강점기, 한 소녀와 소년의 순수한 사랑을 중심으로 그려진 이야기. 그들의 사랑은 시대의 어려움과 갈등 속에서도 흔들리지 않으나, 결국은 잔인한 현실 앞에 무너지게 된다. 사랑과 이별의 아픔을 그린 작품.",
            "이문열은 한국 현대문학의 대표적인 작가 중 한 명으로, 그의 작품은 국내외 여러 언어로 번역되어 널리 읽히고 있다. 주로 역사와 인간의 내면을 탐구하는 작품을 썼으며, '서울 1945' 등의 작품으로 유명하다.",
        ),
        BookInfo(
            "저녁의 전설", "박완서",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "1970년대 서울. 도시의 빠른 변화와 그 속에서 살아가는 사람들의 이야기. 주인공은 도시의 변화에 발맞춰 자신의 삶을 살아가려 하지만, 과거의 그림자와 현재의 현실 사이에서 갈등을 겪게 된다.",
            "박완서는 20세기 한국 문학의 여성 작가로, 그녀의 작품은 여성의 시선으로 사회와 인간을 바라보는 독특한 시각을 보여준다. '기다리는 연인들'과 '꽃을 보듯 너를 본다' 등의 작품으로 널리 알려져 있다."
        ),
        BookInfo(
            "산과 강",
            "김희선",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "조선시대 중반의 산골마을을 배경으로 한 진실한 사랑과 운명의 이야기를 그린다. 주인공은 자신의 운명을 거스르며 진정한 사랑을 찾아 나선다.",
            "김희선은 1950년대부터 활발하게 활동을 펼치며 한국의 대표적인 소설가로 자리매김하였다. 그녀의 작품은 깊은 인간성과 사랑에 대한 고찰이 돋보인다."
        ),
        BookInfo(
            "바람의 노래",
            "이준호",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "옛날 한 작은 섬마을에서 발생한 신비한 사건을 중심으로 전개되는 이야기. 주인공은 섬의 비밀을 밝혀내려고 여러 위험을 감수한다.",
            "이준호는 1970년대를 대표하는 작가로, 그의 작품 속에는 자연과 인간, 그리고 운명에 대한 깊은 성찰이 담겨져 있다."
        ),
        BookInfo(
            "별들의 전쟁",
            "최유나",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "우주 전쟁을 배경으로 별들 사이의 권력 다툼과 우정, 사랑을 그린다. 이 소설은 우주의 무한함과 인간의 한계를 동시에 보여준다.",
            "최유나는 현대 한국의 대표적인 SF 작가로, 그녀의 작품은 상상력이 풍부하고, 복잡한 감정을 섬세하게 표현한다."
        ),
        BookInfo(
            title = "희망의 언덕",
            writer = "이영희",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "서울의 한 외곽 마을을 배경으로 이루어진 사랑과 이별의 이야기. 여름의 뜨거운 햇살 아래에서 펼쳐지는 두 청년의 순수한 사랑은 독자들의 마음을 따뜻하게 만든다. 그러나 사랑 앞에서의 선택은 항상 쉽지 않다는 것을 알려준다.",
            aboutAuthor = "이영희는 20세기 중반 한국의 대표적인 작가로, 그녀의 작품은 사랑과 인간의 내면을 깊이 탐구한다. 그녀는 순수한 사랑의 가치를 중요하게 여기며, 그 테마를 다양한 작품에서 다루고 있다."
        ),
        BookInfo(
            title = "가을의 전설",
            writer = "김태준",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "가을의 서정적인 풍경 속에서 시작된 한 남자와 여자의 운명적인 만남. 두 사람은 서로를 알아가면서 진정한 사랑의 의미를 깨닫게 된다. 하지만 그들 앞에는 예상치 못한 장애물이 다가온다.",
            aboutAuthor = "김태준은 한국의 대표적인 서정소설 작가로, 그의 작품은 자연의 아름다움과 인간의 감성을 중심으로 그려진다. 그는 사람들 사이의 감정의 미묘함을 섬세하게 표현하는 데 능하다."
        ),
        BookInfo(
            title = "겨울의 꿈",
            writer = "박은미",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "겨울의 추위 속에서 한 남자가 자신의 과거를 회상한다. 어린 시절, 첫사랑, 그리고 잃어버린 친구들의 기억. 그의 과거는 따뜻한 눈물과 함께 독자들의 마음 속에 남게 될 것이다.",
            aboutAuthor = "박은미는 20세기 후반 한국의 주요 작가로, 그녀의 작품은 과거와 현재를 연결하는 다리 역할을 한다. 그녀는 인간의 내면세계와 감성을 정확하게 포착하여 독자들의 공감을 이끌어낸다."
        ),

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

            switchMode.isChecked = isNightModeActive(requireContext())
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Handler().postDelayed({
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }, 100)
                } else {
                    Handler().postDelayed({
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }, 100)
                }
            }


            val adapter1 = BookRecyclerAdapter(list, Category)

            categoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter1.filterByCategory(ClassicNovel)
            categoryList.adapter = adapter1
            categoryList.addItemDecoration(SpacesItemDecoration(Space(0,  14, 0, 0, 16, 16), Category))

            val adapter2 = BookRecyclerAdapter(list, My)

            recentlyList.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter2.filterByCategory(My)
            recentlyList.adapter = adapter2
            recentlyList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 16, 16), My))

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

            val adapter = BookRecyclerAdapter(list, Category)
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

    private fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    private fun isNightModeActive(context: Context): Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> false
        }
    }


}