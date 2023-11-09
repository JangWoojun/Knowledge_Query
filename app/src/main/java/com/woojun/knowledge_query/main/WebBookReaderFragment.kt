package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentSearchBinding
import com.woojun.knowledge_query.databinding.FragmentWebBookReaderBinding

class WebBookReaderFragment : Fragment() {

    private var _binding: FragmentWebBookReaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBookReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideBottomNavigation(false)
        _binding = null
    }

}