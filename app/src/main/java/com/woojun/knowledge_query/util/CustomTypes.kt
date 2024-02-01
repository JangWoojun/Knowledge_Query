package com.woojun.knowledge_query.util
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

class TypeConverter {
    @TypeConverter
    fun fromUser(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun toUser(user: String): User {
        val userType = object : TypeToken<User>() {}.type
        return Gson().fromJson(user, userType)
    }

    @TypeConverter
    fun fromBookInfo(bookInfo: BookInfo): String {
        return Gson().toJson(bookInfo)
    }

    @TypeConverter
    fun toBookInfo(bookInfoString: String): BookInfo {
        return Gson().fromJson(bookInfoString, BookInfo::class.java)
    }

    @TypeConverter
    fun fromBookInfoList(bookInfoList: List<BookInfo>): String {
        val gson = Gson()
        return gson.toJson(bookInfoList)
    }

    @TypeConverter
    fun toBookInfoList(bookInfoString: String): List<BookInfo> {
        val gson = Gson()
        val type = object : TypeToken<List<BookInfo>>() {}.type
        return gson.fromJson(bookInfoString, type)
    }
}

data class WKResult(
    val result: Int,
    val return_object: ReturnObject
)

data class AnswerInfo(
    val answer: String,
    val confidence: Int,
    val rank: Int,
    val url: List<String>
)

data class IRInfo(
    val sent: String,
    val url: String,
    val wiki_title: String
)
data class WiKiInfo(
    val AnswerInfo: List<AnswerInfo>,
    val IRInfo: List<IRInfo>
)

data class ReturnObject(
    val WiKiInfo: WiKiInfo
)

data class MRResult(
    val result: Int,
    val return_object: MRCReturnObject
)

data class MRCReturnObject(
    val MRCInfo: MRCInfo
)

data class RequestBody(
    val argument: Argument
)

data class RequestBody2(
    val argument: Argument2
)

data class Argument(
    val question: String,
    val passage: String
)

data class Argument2(
    val type: String,
    val question: String
)

data class MRCInfo(
    val answer: String,
    val begin: Int,
    val confidence: String,
    val end: Int,
    val passage: String,
    val question: String
)

data class Chat(
    val chatType: ChatType,
    var message: String,
    var isAnswerEnd: Boolean = false
)

enum class ChatType {
    Bot, User
}

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var myBookList: MutableList<BookInfo>
)

data class PagerItem(
    val image: Int,
    val title: String,
    val subject: String
)

@Parcelize
data class BookInfo(
    val title: String = "",
    val writer: String = "",
    val image: Int = 0,
    val category: BookType = BookType.Category,
    val overview: String = "",
    val aboutAuthor: String = "",
    var url: String = "",
    var isDownload: Boolean = false,
    var bookmark: Boolean = false
): Parcelable

data class Space(
    var top: Int,
    var left: Int,
    var right: Int,
    var bottom: Int,
    var firstTop: Int,
    var firstLeft: Int,
    var lastRight: Int,
    var lastBottom: Int
)

enum class BookType {
    Category, My, Bookmark, ClassicNovel, FairyTale, Poem, SocialNews, ItNews, DailyNews
}