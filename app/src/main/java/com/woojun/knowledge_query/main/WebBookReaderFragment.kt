package com.woojun.knowledge_query.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.woojun.knowledge_query.BuildConfig
import com.woojun.knowledge_query.databinding.FragmentWebBookReaderBinding
import com.woojun.knowledge_query.util.Argument2
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.RetrofitAPI
import com.woojun.knowledge_query.util.RetrofitClient
import com.woojun.knowledge_query.util.WKResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

            val arguments = arguments
            val item = arguments?.getParcelable<BookInfo>("book info")

            val storage = Firebase.storage
            val storageRef = storage.reference
            val fileRef = storageRef.child(item!!.url)

            val localFile = File.createTempFile("tempfile", ".txt")

            fileRef.getFile(localFile).addOnSuccessListener {
                loadingProgress.visibility = View.GONE
                val text = localFile.readText(Charsets.UTF_8)
                bookText.text = text
            }.addOnFailureListener {
                loadingProgress.visibility = View.GONE
                Toast.makeText(requireContext(), "오류 잠시 후 시도해주세요", Toast.LENGTH_SHORT).show()
            }

            questionBackground.bringToFront()
            buttonBackground.bringToFront()

            dictionaryButton.setOnClickListener {
                (activity as MainActivity).changeWindow(true)
                questionBackground.visibility = View.VISIBLE
            }

            closeButton.setOnClickListener {
                (activity as MainActivity).changeWindow(false)
                answerText.visibility = View.GONE
                questionBackground.visibility = View.GONE
            }

            questionText.setOnEditorActionListener { _, _, _ ->
                if (questionText.text.toString() != "") {
                    val retrofit = RetrofitClient.getInstance()
                    val apiService = retrofit.create(RetrofitAPI::class.java)

                    val call = apiService.WKPost(
                        BuildConfig.APIKEY, com.woojun.knowledge_query.util.
                    RequestBody2(
                        Argument2("hybridqa", questionText.text.toString())
                    ))

                    call.enqueue(object : Callback<WKResult> {
                        override fun onResponse(call: Call<WKResult>, response: Response<WKResult>) {
                            if (response.isSuccessful && response.body()!!.result != -1) {
                                val result = response.body()!!.return_object.WiKiInfo.AnswerInfo[0].answer
                                answerText.visibility = View.VISIBLE
                                answerText.text = result
                            } else {
                                answerText.visibility = View.VISIBLE
                                answerText.text = "찾을 수 없음"
                            }
                            questionText.text?.clear()
                        }

                        override fun onFailure(call: Call<WKResult>, t: Throwable) {
                            answerText.visibility = View.VISIBLE
                            answerText.text = "찾을 수 없음"
                            questionText.text?.clear()
                        }
                    })
                } else {
                    Toast.makeText(requireContext(), "질문을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).changeWindow(false)
        (activity as MainActivity).hideBottomNavigation(false)
        _binding = null
    }

}