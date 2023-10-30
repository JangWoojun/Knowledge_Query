package com.woojun.knowledge_query.util

data class PagerItem(
    val image: Int,
    val title: String,
    val subject: String
)

enum class BookType {
    Category, My
}