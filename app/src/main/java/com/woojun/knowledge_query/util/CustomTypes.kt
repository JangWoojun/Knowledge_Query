package com.woojun.knowledge_query.util

data class PagerItem(
    val image: Int,
    val title: String,
    val subject: String
)

data class Space(
    var top: Int,
    var left: Int,
    var right: Int,
    var bottom: Int,
    var firstLeft: Int,
    var lastRight: Int
)

enum class BookType {
    Category, My
}