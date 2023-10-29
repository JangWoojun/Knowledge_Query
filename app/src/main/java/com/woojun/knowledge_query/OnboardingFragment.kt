package com.woojun.knowledge_query

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.viewpager2.widget.ViewPager2
import com.woojun.knowledge_query.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private var pagerItemList = arrayListOf(
        PagerItem(R.drawable.onboarding_image1, "긴 글에 지쳤다면?" , "지식 쿼리로 Ai에게 질문하여\n해당 글의 핵심 정보만 파악해보세요"),
        PagerItem(R.drawable.onboarding_image2, "모르는 단어가 있다면?", "위키 백과를 통한 검색으로\n빠르게 단어의 뜻을 파악할 수 있어요"),
        PagerItem(R.drawable.onboarding_image3, "원하는 글이 없다면?", "가지고 있는 텍스트 파일 또는\n복사한 텍스트로도 질문이 가능해요")
    )
    private lateinit var introPagerRecyclerAdapter: OnboardingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            introPagerRecyclerAdapter = OnboardingRecyclerAdapter(pagerItemList)

            introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 2) {
                        startButton.visibility = View.VISIBLE
                    } else {
                        startButton.visibility = View.GONE
                    }
                }
            })

            introViewPager.apply {
                adapter = introPagerRecyclerAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                binding.dotsIndicator.attachTo(this)
            }

            startButton.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}