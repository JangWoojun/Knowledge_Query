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
import com.woojun.knowledge_query.databinding.FragmentBookReaderBinding
import com.woojun.knowledge_query.util.BookInfo

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

            aiButton.setOnClickListener {
                touchButton(true)
            }

            dictionaryButton.setOnClickListener {
                touchButton(false)
            }

            closeButton.setOnClickListener {
                (activity as MainActivity).changeWindow(false)
                questionBackground.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideBottomNavigation(false)
        _binding = null
    }

    private fun touchButton(isAi: Boolean) {
        binding.apply {
            (activity as MainActivity).changeWindow(true)
            questionBackground.visibility = View.VISIBLE

            if (isAi) {
                typeText.text = "지식 쿼리 A.I"
            } else {
                typeText.text = "위키 백과 A.I"
            }
        }
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

                val inputStream = requireContext().contentResolver.openInputStream(item!!.url.toUri())
                val text = inputStream?.bufferedReader().use { it?.readText() }
                if (text != null) {
                    bookText.text = text
                    titleText.text = item.title
                } else {
                    Toast.makeText(requireContext(), "지원되지 않는 파일입니다 .txt 파일을 사용해주세요", Toast.LENGTH_SHORT).show()
                }
                inputStream?.close()
            }
        } else {
            pleasePermission()
        }
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