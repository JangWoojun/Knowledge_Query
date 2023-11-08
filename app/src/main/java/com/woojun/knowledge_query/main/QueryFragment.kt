package com.woojun.knowledge_query.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.knowledge_query.BuildConfig
import com.woojun.knowledge_query.databinding.FragmentQueryBinding
import com.woojun.knowledge_query.util.Argument
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.Chat
import com.woojun.knowledge_query.util.ChatRecyclerAdapter
import com.woojun.knowledge_query.util.ChatType
import com.woojun.knowledge_query.util.MRResult
import com.woojun.knowledge_query.util.RetrofitAPI
import com.woojun.knowledge_query.util.RetrofitClient
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    private val list: MutableList<Chat> = mutableListOf(Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용할 글을 입력해주세요", true), Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용할 글을 입력해주세요", true), Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용할 글을 입력해주세요", true), Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용할 글을 입력해주세요", true))
    private val passageList: MutableList<Chat> = mutableListOf()
    private var isQuestion = false

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
            val chatAdapter = ChatRecyclerAdapter(list)

            chatList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            chatList.adapter = chatAdapter
            chatList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 10, 30, 0, 0, 30), BookType.My))

            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    textLength.text = "${input.text.toString().length}/10000"
                }

                override fun afterTextChanged(s: Editable) {
                }
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}