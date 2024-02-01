package com.woojun.knowledge_query.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookViewModel : ViewModel() {
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("book")

    private val _dataList = MutableLiveData<List<BookInfo>>()
    val dataList: LiveData<List<BookInfo>> = _dataList

    fun fetchData() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tempList = mutableListOf<BookInfo>()
                for (childSnapshot in dataSnapshot.children) {
                    val dataValue = childSnapshot.getValue(BookInfo::class.java)
                    dataValue?.let { tempList.add(it) }
                }
                _dataList.value = tempList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("확인", "에러")
            }
        })
    }
}