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

data class Chat(
    val chatType: ChatType,
    val message: String
)

enum class ChatType {
    Bot, User
}

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val myBookList: MutableList<BookInfo>
)

data class PagerItem(
    val image: Int,
    val title: String,
    val subject: String
)

@Parcelize
data class BookInfo(
    val title: String,
    val writer: String,
    val image: Int,
    val category: BookType,
    val overview: String,
    val aboutAuthor: String,
    var url: String = "",
    var isDownload: Boolean = false,
    var bookmark: Boolean = false
): Parcelable

data class Space(
    var top: Int,
    var left: Int,
    var right: Int,
    var bottom: Int,
    var firstLeft: Int,
    var lastRight: Int
)

enum class BookType {
    Category, My, ClassicNovel, FairyTale, Poem, SocialNews, ItNews, DailyNews
}