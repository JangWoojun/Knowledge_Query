package com.woojun.knowledge_query.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.woojun.knowledge_query.BuildConfig
import com.woojun.knowledge_query.databinding.FragmentBookReaderBinding
import com.woojun.knowledge_query.util.Argument2
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.RetrofitAPI
import com.woojun.knowledge_query.util.RetrofitClient
import com.woojun.knowledge_query.util.WKResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BookReaderFragment : Fragment() {
    private var _binding: FragmentBookReaderBinding? = null
    private val binding get() = _binding!!
    private val READ_REQUEST_CODE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            readFile()

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

                    val call = apiService.WKPost(BuildConfig.APIKEY, com.woojun.knowledge_query.util.
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

    private fun readFile() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.apply {
                val arguments = arguments
                val item = arguments?.getParcelable<BookInfo>("book info")

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val inputStream = requireContext().contentResolver.openInputStream(item!!.url.toUri())
                        inputStream?.bufferedReader().use { reader ->
                            var line: String?
                            var pageContent = StringBuilder()
                            var lineCount = 0
                            val pageSize = 5

                            while (reader?.readLine().also { line = it } != null) {
                                if (lineCount < pageSize) {
                                    pageContent.append(line).append("\n")
                                    lineCount++
                                } else {
                                    withContext(Dispatchers.Main) {
                                        updateTextView(pageContent.toString())
                                    }
                                    pageContent = StringBuilder()
                                    lineCount = 0
                                }
                            }

                            if (pageContent.isNotEmpty()) {
                                withContext(Dispatchers.Main) {
                                    updateTextView(pageContent.toString())
                                }
                            }
                        }
                    } catch (e: IOException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "파일을 읽는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            pleasePermission()
        }
    }

    fun updateTextView(text: String) {
        binding.bookText.append(text)
    }

    private fun pleasePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val granted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != granted) {
            requestPermissions(arrayOf(permission), READ_REQUEST_CODE)
        } else {
            readFile()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile()
            } else {
                pleasePermission()
            }
        }
    }

}