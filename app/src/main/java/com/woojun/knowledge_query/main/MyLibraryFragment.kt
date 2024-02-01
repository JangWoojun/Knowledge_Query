package com.woojun.knowledge_query.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentMyLibraryBinding
import com.woojun.knowledge_query.util.AppDatabase
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

class MyLibraryFragment : Fragment() {
    private var _binding: FragmentMyLibraryBinding? = null
    private val binding get() = _binding!!

    private val READ_REQUEST_CODE = 42

    private var adapter = BookRecyclerAdapter(listOf(), BookType.My)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(requireContext())
                val list = db?.userDao()!!.getUser().myBookList
                adapter = BookRecyclerAdapter(list, BookType.My)

                withContext(Dispatchers.Main) {
                    bookList.layoutManager = GridLayoutManager(requireContext(), 2)
                    bookList.adapter = adapter
                }

            }

            bookList.addItemDecoration(SpacesItemDecoration(Space(0,  10, 10, 16, 0, 10, 10, 0),
                BookType.My
            ))

            addButton.setOnClickListener {
                pleasePermission()
            }

            searchButton.setOnClickListener {
                textView.visibility = View.GONE
                searchButton.visibility = View.GONE
                addButton.visibility = View.GONE
                searchInputButton.visibility = View.VISIBLE

                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            searchInputButton.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            bookList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 0, 16, 16, 0), BookType.My))

            bookNameLayout.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            bookNameInput.setOnEditorActionListener { _, _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    val db = AppDatabase.getDatabase(requireContext())
                    val list = db?.userDao()!!.getUser().myBookList

                    adapter = BookRecyclerAdapter(list.filter { it.title.contains(bookNameInput.text.toString()) }, BookType.My)

                    withContext(Dispatchers.Main) {
                        bookList.layoutManager = GridLayoutManager(requireContext(), 2)
                        bookList.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                }

                true
            }

            container.setOnTouchListener { it, _ ->
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                textView.visibility = View.VISIBLE
                searchButton.visibility = View.VISIBLE
                addButton.visibility = View.VISIBLE
                searchInputButton.visibility = View.GONE

                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun randomImage(): Int {
        return when (Random().nextInt(7)) {
            0 -> R.drawable.test_1
            1 -> R.drawable.test_2
            2 -> R.drawable.test_3
            3 -> R.drawable.test_4
            4 -> R.drawable.test_5
            5 -> R.drawable.test_6
            6 -> R.drawable.test_7
            else -> R.drawable.test_6
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val text = inputStream?.bufferedReader().use { it?.readText() }
                context?.contentResolver?.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                if (text != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = AppDatabase.getDatabase(requireContext())
                        val userDao = db?.userDao()
                        val userBookList = userDao!!.getUser().myBookList

                        userBookList.add(
                            BookInfo(
                                getFileNameFromUri(uri).toString(),
                                "",
                                randomImage(),
                                BookType.My,
                                "",
                                "",
                                uri.toString(),
                                true,
                                bookmark = false
                            )
                        )

                        val user = userDao.getUser()
                        user.myBookList = userBookList

                        userDao.updateUser(user)
                        adapter = BookRecyclerAdapter(userBookList, BookType.My)

                        withContext(Dispatchers.Main) {
                            binding.apply {
                                bookList.adapter = adapter
                                bookList.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
                inputStream?.close()
            }
        }
    }

    private fun openFile() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "text/plain"
            startActivityForResult(intent, READ_REQUEST_CODE)
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
            openFile()
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
                openFile()
            } else {
                pleasePermission()
            }
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        val contentResolver = context?.contentResolver
        val cursor = contentResolver?.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    val displayName = it.getString(displayNameIndex)
                    return displayName.dropLast(4).trim()
                }
            }
        }
        return null
    }

    private fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

}