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

    private val list: MutableList<Chat> = mutableListOf(Chat(ChatType.Bot, "사용하시기 앞서 우선 쿼리 기능을 사용할 글을 입력해주세요!", true))
    private val passageList: MutableList<Chat> = mutableListOf()
    private var isQuestion = false
    private var isQuestionEnd = true

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
            chatList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 50, 0, 0, 0, 50), BookType.My))

            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    textLength.text = "${input.text.toString().length}/10000"
                }

                override fun afterTextChanged(s: Editable) {
                }
            })

            submitButton.setOnClickListener {
                if (input.text.toString().length >= 3 && isQuestionEnd) {
                    if (isQuestion) {
                        isQuestionEnd = false
                        chatAdapter.addItem(Chat(ChatType.User, input.text.toString()))
                        chatAdapter.addItem(Chat(ChatType.Bot, ""))

                        val retrofit = RetrofitClient.getInstance()
                        val apiService = retrofit.create(RetrofitAPI::class.java)

                        val call = apiService.MRPost(BuildConfig.APIKEY, com.woojun.knowledge_query.util.
                        RequestBody(
                            Argument(input.text.toString(), passageList[0].message)
                        ))

                        call.enqueue(object : Callback<MRResult> {
                            override fun onResponse(call: Call<MRResult>, response: Response<MRResult>) {
                                if (response.isSuccessful && response.body()!!.result != -1) {
                                    val result = response.body()!!.return_object.MRCInfo
                                    chatAdapter.updateItem(
                                        chatAdapter.itemCount - 1,
                                        "${result.answer}\n답변 신뢰도: ${(result.confidence.toDouble()*100).toInt()}%",
                                        true)
                                } else {
                                    chatAdapter.updateItem(chatAdapter.itemCount - 1, "단락 내에서 답변을 찾을 수 없습니다", true)
                                }
                                isQuestionEnd = true
                            }

                            override fun onFailure(call: Call<MRResult>, t: Throwable) {
                                chatAdapter.updateItem(chatAdapter.itemCount - 1, "단락 내에서 답변을 찾을 수 없습니다", true)
                                isQuestionEnd = true
                            }
                        })
                    }
                    else {
                        passageList.add(Chat(ChatType.Bot, input.text.toString()))
                        isQuestion = true

                        chatAdapter.addItem(Chat(ChatType.User, "지식 쿼리를 사용할 글", false))
                        chatAdapter.addItem(Chat(ChatType.Bot, "이제 질문을 시작해주세요!", true))
                    }
                    input.text?.clear()
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken, 0)

                    it.clearFocus()
                } else {
                    if (isQuestionEnd) {
                        Toast.makeText(requireContext(), "최소 3글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "답변이 끝나고 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                chatList.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}