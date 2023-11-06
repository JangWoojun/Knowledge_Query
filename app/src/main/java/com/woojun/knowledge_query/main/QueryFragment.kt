package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.knowledge_query.databinding.FragmentQueryBinding
import com.woojun.knowledge_query.util.Chat
import com.woojun.knowledge_query.util.ChatRecyclerAdapter
import com.woojun.knowledge_query.util.ChatType

class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    private val list: MutableList<Chat> = mutableListOf(Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용하실 글을 입력해주세요."), Chat(ChatType.User, "사용하시기 앞서 우선 쿼리 기능을 사용하실 글을 입력해주세요."), Chat(ChatType.User, "사용하시기 앞서 우선 쿼리 기능을 사용하실 글을 입력해주세요."), Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용하실 글을 입력해주세요."), Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용하실 글을 입력해주세요."))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            chatList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            chatList.adapter = ChatRecyclerAdapter(list)

            chatList.smoothScrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}