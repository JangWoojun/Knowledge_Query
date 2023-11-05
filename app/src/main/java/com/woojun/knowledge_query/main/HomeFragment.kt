package com.woojun.knowledge_query.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.woojun.knowledge_query.R
import com.woojun.knowledge_query.databinding.FragmentHomeBinding
import com.woojun.knowledge_query.util.BookInfo
import com.woojun.knowledge_query.util.BookRecyclerAdapter
import com.woojun.knowledge_query.util.BookType
import com.woojun.knowledge_query.util.BookType.*
import com.woojun.knowledge_query.util.MyApplication
import com.woojun.knowledge_query.util.Space
import com.woojun.knowledge_query.util.SpacesItemDecoration

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val list = arrayListOf(
        BookInfo(
            "한강의 미래", "김영하",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "20세기 중반, 한강을 중심으로 벌어진 두 가족 간의 이야기. 시대의 변화와 그 속에서 사랑과 이별을 겪은 두 주인공의 모습이 생생하게 그려진다. 강 물결 속에 반영된 사람들의 삶과 그림자가 독자의 마음을 사로잡는다.",
            "김영하는 1980년대부터 활동하기 시작한 작가로서, 그의 작품은 현대 사회의 모습과 사람들의 내면을 깊이 있게 그려낸다. 대표작으로는 '하늘과 바람과 별과 시'가 있으며, 많은 독자들에게 사랑받고 있다."
        ),
        BookInfo(
            "마지막 눈물", "이문열",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "일제 강점기, 한 소녀와 소년의 순수한 사랑을 중심으로 그려진 이야기. 그들의 사랑은 시대의 어려움과 갈등 속에서도 흔들리지 않으나, 결국은 잔인한 현실 앞에 무너지게 된다. 사랑과 이별의 아픔을 그린 작품.",
            "이문열은 한국 현대문학의 대표적인 작가 중 한 명으로, 그의 작품은 국내외 여러 언어로 번역되어 널리 읽히고 있다. 주로 역사와 인간의 내면을 탐구하는 작품을 썼으며, '서울 1945' 등의 작품으로 유명하다.",
        ),
        BookInfo(
            "저녁의 전설", "박완서",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "1970년대 서울. 도시의 빠른 변화와 그 속에서 살아가는 사람들의 이야기. 주인공은 도시의 변화에 발맞춰 자신의 삶을 살아가려 하지만, 과거의 그림자와 현재의 현실 사이에서 갈등을 겪게 된다.",
            "박완서는 20세기 한국 문학의 여성 작가로, 그녀의 작품은 여성의 시선으로 사회와 인간을 바라보는 독특한 시각을 보여준다. '기다리는 연인들'과 '꽃을 보듯 너를 본다' 등의 작품으로 널리 알려져 있다."
        ),
        BookInfo(
            "산과 강",
            "김희선",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "조선시대 중반의 산골마을을 배경으로 한 진실한 사랑과 운명의 이야기를 그린다. 주인공은 자신의 운명을 거스르며 진정한 사랑을 찾아 나선다.",
            "김희선은 1950년대부터 활발하게 활동을 펼치며 한국의 대표적인 소설가로 자리매김하였다. 그녀의 작품은 깊은 인간성과 사랑에 대한 고찰이 돋보인다."
        ),
        BookInfo(
            "바람의 노래",
            "이준호",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "옛날 한 작은 섬마을에서 발생한 신비한 사건을 중심으로 전개되는 이야기. 주인공은 섬의 비밀을 밝혀내려고 여러 위험을 감수한다.",
            "이준호는 1970년대를 대표하는 작가로, 그의 작품 속에는 자연과 인간, 그리고 운명에 대한 깊은 성찰이 담겨져 있다."
        ),
        BookInfo(
            "별들의 전쟁",
            "최유나",
            R.drawable.test_1,
            BookType.ClassicNovel,
            "우주 전쟁을 배경으로 별들 사이의 권력 다툼과 우정, 사랑을 그린다. 이 소설은 우주의 무한함과 인간의 한계를 동시에 보여준다.",
            "최유나는 현대 한국의 대표적인 SF 작가로, 그녀의 작품은 상상력이 풍부하고, 복잡한 감정을 섬세하게 표현한다."
        ),
        BookInfo(
            title = "희망의 언덕",
            writer = "이영희",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "서울의 한 외곽 마을을 배경으로 이루어진 사랑과 이별의 이야기. 여름의 뜨거운 햇살 아래에서 펼쳐지는 두 청년의 순수한 사랑은 독자들의 마음을 따뜻하게 만든다. 그러나 사랑 앞에서의 선택은 항상 쉽지 않다는 것을 알려준다.",
            aboutAuthor = "이영희는 20세기 중반 한국의 대표적인 작가로, 그녀의 작품은 사랑과 인간의 내면을 깊이 탐구한다. 그녀는 순수한 사랑의 가치를 중요하게 여기며, 그 테마를 다양한 작품에서 다루고 있다."
        ),
        BookInfo(
            title = "가을의 전설",
            writer = "김태준",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "가을의 서정적인 풍경 속에서 시작된 한 남자와 여자의 운명적인 만남. 두 사람은 서로를 알아가면서 진정한 사랑의 의미를 깨닫게 된다. 하지만 그들 앞에는 예상치 못한 장애물이 다가온다.",
            aboutAuthor = "김태준은 한국의 대표적인 서정소설 작가로, 그의 작품은 자연의 아름다움과 인간의 감성을 중심으로 그려진다. 그는 사람들 사이의 감정의 미묘함을 섬세하게 표현하는 데 능하다."
        ),
        BookInfo(
            title = "겨울의 꿈",
            writer = "박은미",
            image = R.drawable.test_1,
            category = BookType.ClassicNovel,
            overview = "겨울의 추위 속에서 한 남자가 자신의 과거를 회상한다. 어린 시절, 첫사랑, 그리고 잃어버린 친구들의 기억. 그의 과거는 따뜻한 눈물과 함께 독자들의 마음 속에 남게 될 것이다.",
            aboutAuthor = "박은미는 20세기 후반 한국의 주요 작가로, 그녀의 작품은 과거와 현재를 연결하는 다리 역할을 한다. 그녀는 인간의 내면세계와 감성을 정확하게 포착하여 독자들의 공감을 이끌어낸다."
        ),

        BookInfo(
            "별빛 아래의 숲", "이수민",
            R.drawable.test_2,
            BookType.FairyTale,
            "어느 날, 하늘에서 떨어진 별빛 조각들이 숲 속에 숨겨진 비밀을 밝혀내게 되는 이야기. 숲 속 동물들과 함께하는 별빛의 모험은 읽는 이로 하여금 상상력을 키울 수 있게 해준다.",
            "이수민은 아동문학 분야에서 10년 가까이 활동하며 다양한 상상력을 가진 이야기로 어린이들의 마음을 사로잡는다."
        ),
        BookInfo(
            "시간을 잃어버린 마을", "김혜리",
            R.drawable.test_2,
            BookType.FairyTale,
            "시계가 멈춰버린 작은 마을에서 사람들은 시간의 중요성을 깨닫게 된다. 어느 날, 한 소년이 시간을 되돌리기 위한 여정을 시작하는데...",
            "김혜리 작가는 그녀의 독특한 상상력과 깊은 메시지로 아동문학계에서 크게 주목받고 있다."
        ),
        BookInfo(
            "바람의 기억", "박종우",
            R.drawable.test_2,
            BookType.FairyTale,
            "바람이 불어오는 고요한 평원. 거기에는 바람이 기억하는 이야기들이 가득하다. 어린 소녀는 바람에게 그 기억들을 듣게 되는데, 그 중에는 소녀의 과거도 포함되어 있었다.",
            "박종우는 그의 서정적인 문체와 풍부한 감성으로 아이들의 마음 속에 깊은 감동을 주는 작가로 알려져 있다."
        ),
        BookInfo(
            title = "별빛 마을의 비밀",
            writer = "조은희",
            image = R.drawable.test_2,
            category = BookType.FairyTale,
            overview = "별빛 마을에는 오랜 시간 동안 숨겨진 비밀이 있었다. 마을의 아이들은 그 비밀을 찾아 세계를 구하는 대모험을 떠난다. 그들의 여정에서 벌어지는 기적 같은 이야기를 만나보세요.",
            aboutAuthor = "조은희는 한국의 대표적인 아동문학 작가로, 그녀의 작품들은 많은 어린이들에게 사랑받아 왔다. 그녀는 아이들의 꿈과 희망을 주는 이야기를 통해 많은 상을 받았다."
        ),
        BookInfo(
            title = "바람의 숲에서",
            writer = "김지영",
            image = R.drawable.test_2,
            category = BookType.FairyTale,
            overview = "바람이 불어오는 숲의 깊숙한 곳에는 특별한 동물들의 왕국이 있었다. 여기에 살고 있는 작은 고양이 미미는 그곳의 비밀을 밝혀내기 위해 모험을 시작한다. 그녀와 함께하는 동물 친구들의 이야기.",
            aboutAuthor = "김지영은 그녀의 독특한 상상력과 따뜻한 문체로 많은 아동문학상을 수상한 작가다. 그녀의 작품들은 세대를 넘어서 사랑받고 있으며, 어린이들의 마음을 어루만져준다."
        ),
        BookInfo(
            title = "해와 달의 전설",
            writer = "이현우",
            image = R.drawable.test_2,
            category = BookType.FairyTale,
            overview = "해와 달은 오랜 시간동안 서로를 그리워했다. 하늘 위에서만 만날 수 있었던 두 사람은 지구에서의 새로운 삶을 꿈꾼다. 이들의 사랑과 우정, 그리고 희생을 통해 세상을 구하는 감동적인 이야기.",
            aboutAuthor = "이현우는 아동문학 분야에서 꾸준히 활동하며 어린이들의 마음을 사로잡는 이야기를 쓴다. 그의 작품들은 국내외에서도 높은 평가를 받아왔다."
        ),
        BookInfo("비밀의 숲", "김나라", R.drawable.test_2, BookType.FairyTale,
            "옛날, 작은 마을 근처의 숲에는 특별한 비밀이 숨겨져 있었다. 마을 아이들은 그 숲에 들어가면 절대로 돌아오지 못한다는 루머를 믿고 있었는데...",
            "김나라는 아동문학 분야에서 20년 동안 활동하였으며, 그녀의 동화는 아이들에게 꿈과 희망을 전한다는 평가를 받고 있다."),

        BookInfo("달빛 아래 마법의 춤", "박준호", R.drawable.test_2, BookType.FairyTale,
            "한 여름 밤, 달빛 아래서 미스터리한 무도회가 열렸다. 참가자들은 모두 동물로 변해 춤을 추었는데, 그 중에는 특별한 비밀을 간직한 자도 있었다...",
            "박준호 작가는 그의 동화를 통해 아이들에게 상상력의 중요성과 창의력을 강조하려는 의도를 갖고 있으며, 그의 작품은 많은 사랑을 받고 있다."),

        BookInfo("용기를 주는 마법의 구두", "이소영", R.drawable.test_2, BookType.FairyTale,
            "소녀 엘라는 마을에서 가장 작은 신발을 가지고 있었다. 하지만 그 구두는 특별했다. 그것을 신으면 누구든지 자신의 두려움을 극복할 수 있게 되었다...",
            "이소영은 아이들의 마음을 따뜻하게 하는 동화를 통해, 여러 어려움을 극복하는 방법을 전달하려는 노력을 지속적으로 하고 있다.")
        ,

        BookInfo(
            "잠들지 않는 밤",
            "이재환",
            R.drawable.test_3,
            BookType.Poem,
            "이 시집은 가로등 아래 잠들지 않는 밤의 감성을 담고 있습니다. 각각의 시는 깊은 사색과 함께 아름다운 자연의 풍경을 그려냅니다. 가슴 깊은 곳에서 울리는 그 감정을 느껴보세요.",
            "이재환은 대한민국의 중견 시인으로, 자연과 사랑에 대한 깊은 감성을 지닌 작품들로 유명합니다. 그의 시는 많은 독자들의 공감을 이끌어냅니다."
        ),
        BookInfo(
            "시간의 흔적",
            "최수연",
            R.drawable.test_3,
            BookType.Poem,
            "시간의 흔적 속에서 삶의 의미를 찾아보는 작품 모음입니다. 인간의 삶 속에서 빛나는 순간들, 그리고 그것들이 빚어내는 추억의 조각들을 담고 있습니다.",
            "최수연은 10여 년 동안 시계를 집필해 온 작가로, 시간과 관련된 다양한 주제로 작품 활동을 이어가고 있습니다. 그녀의 시는 섬세하고도 깊은 여운을 남깁니다."
        ),
        BookInfo(
            "빛나는 순간들",
            "박준혁",
            R.drawable.test_3,
            BookType.Poem,
            "삶의 빛나는 순간들을 잡아낸 이 시집은, 작은 행복부터 큰 감동까지 다양한 감정의 파레트를 그립니다. 삶의 다양한 순간들 속에서 찾아낸 아름다움을 공유합니다.",
            "박준혁은 신인 시인으로 데뷔한 지 얼마 되지 않았지만, 그의 작품은 많은 독자들의 마음을 사로잡고 있습니다. 그는 일상의 소중함을 강조하는 작품으로 알려져 있습니다."
        ),
        BookInfo(
            title = "겨울의 새벽",
            writer = "이승민",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "이 시집은 겨울 새벽의 고요함과 그 안에서 울려오는 속삭임을 집중적으로 다룬다. 우리의 일상 속에 녹아든 작은 순간들을 따뜻한 시로 풀어내었다.",
            aboutAuthor = "이승민은 한국의 대표적인 현대 시인으로, 그의 작품은 주로 자연과 인간의 관계에 초점을 맞추고 있다. 다양한 상과 수상 경력이 있는 작가이다."
        ),
        BookInfo(
            title = "바다의 속삭임",
            writer = "장수영",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "이 작품은 파도의 소리와 해변의 모래, 그리고 바다의 깊은 곳에서 들려오는 이야기들을 섬세하게 담아냈다. 각 시는 마치 바다의 숨결 같다.",
            aboutAuthor = "장수영은 바다를 주제로 한 다양한 작품을 발표하며, 그만의 독특한 세계관을 구축해왔다. 그녀의 작품은 많은 사랑을 받고 있다."
        ),
        BookInfo(
            title = "봄날의 꽃",
            writer = "김지애",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "봄의 따스함과 꽃의 향기, 그리고 그 사이에서 느껴지는 삶의 기쁨과 슬픔을 진심으로 담아냈다. 꽃이 피는 계절의 아름다움을 느낄 수 있다.",
            aboutAuthor = "김지애는 자연의 아름다움을 사랑하는 시인으로, 그녀의 작품은 많은 독자들에게 위로와 희망을 전달하고 있다."
        ),
        BookInfo(
            title = "봄의 속삭임",
            writer = "김향미",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "봄바람 속에 숨겨진 소리들, 꽃잎의 눈물, 나비의 날개짓. 이 시집은 자연의 소리와 사람의 마음을 연결하는 다리로서, 각 시마다 봄의 따뜻한 향기를 느낄 수 있다.",
            aboutAuthor = "김향미는 한국의 젊은 세대 시인 중 하나로, 자연과 사람의 관계에 중점을 둔 작품들을 발표하고 있다. 그녀의 시는 많은 독자들에게 사랑받고 있다."
        ),
        BookInfo(
            title = "별의 노래",
            writer = "이경숙",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "별들의 깜박임, 밤하늘의 심연. 이 시집은 우주와 인간의 존재에 대한 깊은 사색을 담고 있다. 별과 달, 밤하늘을 바라보며 느끼는 섬세한 감정들을 표현하였다.",
            aboutAuthor = "이경숙은 우주와 인간의 관계에 대해 탐구하는 시인으로 알려져 있다. 그녀의 작품은 철학적인 물음과 감정의 깊이를 동시에 담고 있다."
        ),
        BookInfo(
            title = "산책",
            writer = "박준호",
            image = R.drawable.test_3,
            category = BookType.Poem,
            overview = "산책하는 도중 마주치는 작은 풍경들, 바람의 냄새, 나뭇잎의 촉감. 이 시집은 일상 속의 작은 순간들을 따뜻하게 포착하였다. 산책로에서 느낄 수 있는 그 아름다움을 전한다.",
            aboutAuthor = "박준호는 일상의 소박한 풍경을 사랑하는 시인으로, 그의 시는 많은 독자들에게 편안한 위로와 공감을 제공한다. 일상의 아름다움을 찾아내는 눈을 가진 작가이다."
        ),

        BookInfo(
            title = "세대 간의 소통과 갈등",
            writer = "정하은",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "디지털 시대가 가져온 세대 간의 소통의 장벽과 그로 인한 갈등에 대해 다룬 기사입니다. " +
                    "여기서는 다양한 사례를 통해 세대 차이를 이해하고 해결책을 모색해봅니다.",
            aboutAuthor = "정하은 기자는 사회 문제와 세대 간 소통에 대해 깊이 있는 분석을 제공하는 베테랑 기자입니다. " +
                    "20년의 경력을 바탕으로 사회 각 분야의 뉴스를 다루며, 특히 세대 간 이해의 중요성을 강조해 왔습니다."
        ),
        BookInfo(
            title = "지구 온난화와 한반도",
            writer = "박준우",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "지구 온난화가 한반도의 기후에 미치는 영향을 분석한 기사로, 이를 통해 미래 세대에 대한 경고의 메시지를 전달합니다. " +
                    "국내외 전문가의 의견을 종합하여 심층적인 보고를 시도합니다.",
            aboutAuthor = "환경 변화에 대한 통찰력 있는 보도로 잘 알려진 박준우 기자는 국내외 환경 이슈에 대한 명확한 분석으로 독자들에게 큰 호응을 얻고 있습니다. " +
                    "환경 보호에 대한 인식 제고를 위해 다양한 매체를 통해 활동하고 있습니다."
        ),
        BookInfo(
            title = "현대 사회와 스트레스",
            writer = "김나린",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "현대 사회에서 증가하는 스트레스 요인들과 이에 대처하는 개인과 사회의 방법을 조명합니다. " +
                    "심리학자와의 인터뷰를 바탕으로 한 흥미로운 분석을 제공합니다.",
            aboutAuthor = "김나린 기자는 현대인의 삶의 질과 정신 건강에 초점을 맞춘 기사를 쓰는 것으로 유명합니다. " +
                    "다년간의 경험을 통해 사람들이 겪는 일상적인 문제들에 대해 깊이 있고 실질적인 조언을 제공합니다."
        ),
        BookInfo(
            title = "교육 불평등과 기회 격차",
            writer = "이정민",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "한국 사회 내에서 교육을 통한 기회의 불평등 문제를 심도 있게 다룬 기사입니다. " +
                    "다양한 통계 자료와 전문가 의견을 토대로 이 문제에 대한 실마리를 제시합니다.",
            aboutAuthor = "사회학에 깊은 관심을 가진 이정민 기자는 특히 교육과 사회 계층 문제에 대한 날카로운 통찰로 독자들에게 새로운 시각을 제공합니다. " +
                    "공정한 사회를 위한 여러 캠페인에도 적극 참여하고 있습니다."
        ),
        BookInfo(
            title = "인터넷 시대의 개인정보 보호",
            writer = "홍세아",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "인터넷 사용이 일상화된 현대에서 개인정보 보호의 중요성과 그에 따른 사회적 논의를 다루는 기사입니다. " +
                    "개인정보 유출 사례 분석을 통해 현 상황을 진단하고 앞으로의 방향을 제시합니다.",
            aboutAuthor = "홍세아 기자는 디지털 시대의 개인정보와 사이버 보안에 대한 이슈를 주로 다루며, 기술 발전과 그에 따른 사회적 책임에 대해 목소리를 높이고 있습니다. " +
                    "정보 보안 전문가들과의 인터뷰를 통해 깊이 있는 보도를 이어가고 있습니다."
        ),
        BookInfo(
            title = "메가시티, 서울의 변화",
            writer = "장은실",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "이 책은 서울이 메가시티로서 겪고 있는 인구, 교통, 환경 문제와 그에 따른 도시 계획의 변화에 대해 심층 분석하고 있다. 서울의 미래 모습을 예측하면서 다양한 사회적 이슈를 다룬다.",
            aboutAuthor = "장은실 작가는 도시 계획과 사회적 이슈에 몰두하는 저널리스트로, 다년간의 현장 경험을 바탕으로 도시 문제에 대한 통찰을 제공한다."
        ),
        BookInfo(
            title = "스마트폰이 바꾼 세상",
            writer = "김다혜",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "스마트폰의 보급이 우리의 일상과 사회적 상호작용, 심지어 정치적 참여 방식에 어떤 변화를 가져왔는지 분석한다. 디지털 혁명의 소용돌이 속에서 스마트폰이 갖는 의미를 재조명한다.",
            aboutAuthor = "김다혜 작가는 기술과 사회의 상호작용에 주목하는 칼럼니스트로, 변화하는 미디어 환경 속 인간관계의 역동성을 탐구해왔다."
        ),
        BookInfo(
            title = "우리 시대의 직업 변화",
            writer = "이재민",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "자동화와 인공지능의 부상으로 인해 현대 직업 세계가 어떻게 변화하고 있는지 구체적인 사례를 통해 설명한다. 미래 직업의 전망을 통해 새로운 시대를 대비하자는 메시지를 전달한다.",
            aboutAuthor = "이재민 작가는 미래학 연구자이자 사회 변화에 대한 새로운 관점을 제시하는 저널리스트로, 직업과 교육의 미래에 관심을 두고 있다."
        ),
        BookInfo(
            title = "청년들의 도시 이탈",
            writer = "홍수연",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "경제적 압박과 삶의 질 추구로 인해 청년층 사이에서 도시를 떠나는 현상이 증가하고 있다. 이 책은 청년들이 직면한 사회적 문제와 지역 사회로의 회귀 현상을 다룬다.",
            aboutAuthor = "홍수연 작가는 사회 현상에 대한 깊이 있는 리포트를 제공하는 저널리스트로, 특히 청년 문제와 관련하여 활발히 활동하고 있다."
        ),
        BookInfo(
            title = "식탁 위의 경제학",
            writer = "조은정",
            image = R.drawable.test_4,
            category = BookType.SocialNews,
            overview = "농산물 가격의 변동부터 글로벌 식품 산업의 동향까지, 우리가 매일 접하는 식탁이 경제에 미치는 영향을 다양한 데이터와 통계를 바탕으로 분석한다.",
            aboutAuthor = "조은정 작가는 경제 전문 기자로서 식품 산업과 관련된 경제적 측면을 탐구하며, 독자에게 실용적인 경제 지식을 전달하기 위해 노력한다."
        ),

        BookInfo(
            title = "블록체인 혁명",
            writer = "정민호",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "블록체인 기술이 세계 경제에 가져올 변화에 대해 심도 깊게 탐구한 책. 암호화폐부터 스마트 계약까지, 블록체인의 모든 것을 설명한다.",
            aboutAuthor = "정민호 기자는 다년간 IT 업계를 취재해온 베테랑으로, 블록체인 기술에 특히 깊은 통찰력을 가지고 있습니다. 그의 글은 항상 정보와 예리한 분석으로 가득 차 있다."
        ),
        BookInfo(
            title = "인공지능의 새벽",
            writer = "송지은",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "인공지능 기술의 발달이 우리 일상에 어떤 혁신적인 변화를 가져올지 전망하는 책. 자율주행 자동차부터 가상비서까지, AI가 만들 미래를 조망한다.",
            aboutAuthor = "송지은 기자는 인공지능 분야에 대한 깊은 지식을 가진 IT 전문 기자입니다. 그녀는 AI가 사회와 산업에 미치는 영향에 대해 날카로운 분석을 제공한다."
        ),
        BookInfo(
            title = "사이버 보안의 최전선",
            writer = "이태준",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "사이버 보안이 현대 사회에서 차지하는 중요성과, 개인과 기업이 어떻게 정보를 보호해야 하는지에 대한 실질적인 조언을 담고 있다.",
            aboutAuthor = "이태준 기자는 사이버 보안 분야에서 10년 이상 경험을 쌓은 전문가로서, 보안 위협과 대응 전략에 관한 심층적인 기사를 작성해왔다."
        ),
        BookInfo(
            title = "클라우드 컴퓨팅의 이해",
            writer = "한소라",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "클라우드 서비스가 비즈니스와 기술의 세계를 어떻게 변화시키고 있는지, 그리고 클라우드 컴퓨팅이 앞으로 어떤 발전을 할지 상세히 설명한다.",
            aboutAuthor = "한소라 기자는 클라우드 컴퓨팅 기술에 대한 풍부한 지식을 가지고 있으며, 복잡한 기술적 개념을 쉽게 풀어서 설명하는 능력이 뛰어납니다."
        ),
        BookInfo(
            title = "데이터 과학의 미래",
            writer = "김도연",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "대량의 데이터에서 인사이트를 추출하는 데이터 과학의 중요성과 향후 기업 전략에 미치는 영향에 대해 분석한다.",
            aboutAuthor = "김도연 기자는 데이터 분석과 과학적 접근에 기반을 둔 기사 작성으로 명성이 높습니다. 그녀는 데이터 과학이 비즈니스에 혁신을 가져올 수 있는 방법을 탐구해왔다."
        ),
        BookInfo(
            title = "인공지능과 사회변화",
            writer = "정민호",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "이 책은 인공지능 기술이 사회 각 분야에 미치는 영향을 심층 분석한다. 의료, 교육, 산업, 서비스 분야 등에서의 AI의 역할을 조명하고 미래 예측을 시도한다.",
            aboutAuthor = "정민호는 기술 저널리즘에 특화된 작가로, IT 분야에서 20년 이상을 종사하며 인공지능 연구와 기술 발전을 지속적으로 취재해왔다."
        ),
        BookInfo(
            title = "블록체인: 미래 경제를 위한 기술",
            writer = "한지은",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "블록체인 기술이 현대 경제에 어떤 혁신을 가져올 수 있는지 구체적인 사례와 함께 설명한다. 암호화폐 뿐만 아니라 여러 산업에 미칠 파급 효과에 대해 논의한다.",
            aboutAuthor = "한지은 작가는 경제와 기술을 연결하는 다리 역할을 해온 저널리스트로, 블록체인 기술에 대한 균형 잡힌 시각을 제공한다."
        ),
        BookInfo(
            title = "클라우드 컴퓨팅의 이해",
            writer = "송준영",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "클라우드 컴퓨팅이 기업의 IT 전략과 어떻게 접목되는지, 기술적인 배경과 함께 실질적인 사례 연구를 통해 설명한다. 클라우드 서비스의 미래에 대해서도 탐구한다.",
            aboutAuthor = "송준영은 클라우드 컴퓨팅과 데이터 센터 분야에서 다년간 전문 기자로 활동해 온 인물이며, 기술의 복잡성을 쉽게 풀어 설명하는 데 탁월한 능력을 지니고 있다."
        ),
        BookInfo(
            title = "사이버 보안의 최전선",
            writer = "김현수",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "사이버 보안의 최신 트렌드와 기술을 분석하고, 해킹 사례를 통해 보안의 중요성을 강조한다. 개인과 기업이 취해야 할 보안 조치에 대해서도 상세히 다룬다.",
            aboutAuthor = "김현수는 사이버 보안 분야에서 15년 이상 활동한 기술 저술가이다. 복잡한 보안 이슈들을 일반 독자들도 이해할 수 있도록 명확하게 전달하는 데 능숙하다."
        ),
        BookInfo(
            title = "데이터 과학의 신세계",
            writer = "이승아",
            image = R.drawable.test_5,
            category = BookType.ItNews,
            overview = "데이터 과학과 빅 데이터 분석이 어떻게 현대 사회를 변화시키고 있는지, 다양한 산업 분야에서의 데이터 활용 사례를 통해 살펴본다. 데이터 기반 의사결정의 중요성을 강조한다.",
            aboutAuthor = "이승아는 데이터 과학에 대한 여러 책을 집필한 작가로, 복잡한 데이터 분석 기법을 이해하기 쉽도록 설명하는 데 기여한 저널리스트이다."
        ),

        BookInfo(
            title = "도심 교통 혼잡, 대책은 있는가?",
            writer = "정지은",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "최근 급증하는 도심 내 차량으로 인한 교통 혼잡이 심화되고 있다. 본 기사에서는 이 문제의 원인을 분석하고, 효과적인 대책을 모색해 본다.",
            aboutAuthor = "정지은 기자는 도시 문제와 교통 정책에 대해 10년 이상을 연구하며, 시민들의 삶의 질 향상을 위한 제안을 지속적으로 해왔다."
        ),
        BookInfo(
            title = "미세먼지, 건강을 위협하는 불청객",
            writer = "이동훈",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "봄철이 되면 우리를 괴롭히는 미세먼지. 이 기사에서는 미세먼지의 원인과 건강에 미치는 영향, 그리고 예방법에 대해 자세히 살펴본다.",
            aboutAuthor = "환경 전문 기자 이동훈은 미세먼지와 같은 환경 문제에 대한 국민의 인식을 높이기 위해 다양한 기사를 작성해왔다."
        ),
        BookInfo(
            title = "국내 관광 산업의 부흥, 가능성은 무엇인가",
            writer = "최수진",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "코로나19 이후 국내 관광 산업이 큰 타격을 입었다. 이번 조사에서는 산업의 현재 상태를 진단하고 부흥을 위한 새로운 전략을 모색한다.",
            aboutAuthor = "최수진 기자는 경제 분야에 깊은 관심을 가지고 있으며, 특히 관광 산업의 경제적 영향에 대해 다년간 연구해온 베테랑이다."
        ),
        BookInfo(
            title = "저출산 문제, 해결을 위한 사회적 노력",
            writer = "박준영",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "저출산은 사회적 문제로, 이를 해결하기 위해선 정부와 기업, 개인의 노력이 필요하다. 본 기사에서는 저출산 문제의 심각성과 해결 방안을 논한다.",
            aboutAuthor = "사회 문제에 천착해 온 박준영 기자는 특히 인구 문제와 관련하여 국내외 다양한 사례를 연구하고 보도해왔다."
        ),
        BookInfo(
            title = "지속 가능한 에너지 사용, 어떻게 접근해야 하나",
            writer = "한지민",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "지구 온난화와 환경 파괴의 주범인 화석 연료 사용을 줄이고, 지속 가능한 에너지로의 전환은 시대의 필수 과제다. 이에 대한 현실적인 접근 방법을 모색한다.",
            aboutAuthor = "한지민 기자는 환경 문제를 전문으로 다루는 기자로서, 재생 에너지와 지속 가능한 발전에 대해 깊이 있게 보도해왔다."
        ),
        BookInfo(
            title = "도시 재생의 새로운 바람",
            writer = "황지현",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "대한민국의 여러 도시들이 직면한 도시 쇠퇴 문제와 이를 해결하기 위한 도시 재생 프로젝트들의 다양한 사례를 조명하며, 도시재생의 중요성을 강조한다.",
            aboutAuthor = "황지현 기자는 도시 계획 및 재생 프로젝트에 대한 깊은 이해를 바탕으로 사회 문제를 조명하는 기사를 작성해 온 경험이 풍부한 언론인이다."
        ),
        BookInfo(
            title = "미세먼지, 우리가 알아야 할 것들",
            writer = "조민수",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "미세먼지의 위험성과 그 영향에 대해 심도 깊은 분석을 제공하며, 일상에서 우리가 취할 수 있는 예방 대책들에 대해서 상세히 설명한다.",
            aboutAuthor = "조민수 기자는 환경 문제에 대한 국내외 뉴스를 취재하며, 특히 대기오염과 관련된 이슈에 대해 다수의 기사를 작성해온 전문가이다."
        ),
        BookInfo(
            title = "가상화폐 열풍, 경제에 미치는 영향",
            writer = "이태준",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "가상화폐가 세계 경제에 미치는 장단기적인 영향을 다각도로 분석하며, 투자자와 경제에 미치는 영향에 대해 상세하게 논의한다.",
            aboutAuthor = "이태준 기자는 금융과 기술이 교차하는 분야에 특화된 분석을 제공하는 경제 전문 기자로 잘 알려져 있다."
        ),
        BookInfo(
            title = "대중교통의 미래",
            writer = "박세영",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "지속 가능한 대중교통 시스템의 중요성을 강조하며, 전 세계적인 대중교통 혁신 사례를 통해 한국의 대중교통 미래를 전망한다.",
            aboutAuthor = "박세영 기자는 대중교통과 지속 가능한 도시 개발에 관한 깊은 연구와 논문으로 유명한 언론인이다."
        ),
        BookInfo(
            title = "교육 혁신, 무엇을 준비해야 하는가",
            writer = "김은정",
            image = R.drawable.test_6,
            category = BookType.DailyNews,
            overview = "4차 산업혁명 시대를 맞이하여 교육 분야에서 요구되는 혁신적 변화와 그 준비 과정에 대한 심층 분석을 담고 있다.",
            aboutAuthor = "김은정 기자는 교육 분야의 변화와 혁신에 관심이 많으며, 다양한 교육 관련 기사를 통해 새로운 시각을 제공해 온 베테랑 기자다."
        )

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            switchMode.isChecked = MyApplication.prefs.isDarkMode()
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                Handler().postDelayed({
                    activity?.let { activity ->
                        val nightMode = if (isChecked) {
                            MyApplication.prefs.setMode(true)
                            AppCompatDelegate.MODE_NIGHT_YES
                        } else {
                            MyApplication.prefs.setMode(false)
                            AppCompatDelegate.MODE_NIGHT_NO
                        }
                        (activity as? AppCompatActivity)?.delegate?.localNightMode = nightMode
                        activity.recreate()
                    }
                }, 250)
            }


            val adapter1 = BookRecyclerAdapter(list, Category)

            categoryList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter1.filterByCategory(ClassicNovel)
            categoryList.adapter = adapter1
            categoryList.addItemDecoration(SpacesItemDecoration(Space(0,  14, 0, 0, 14, 16), Category))

            val adapter2 = BookRecyclerAdapter(list, My)

            recentlyList.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter2.filterByCategory(My)
            recentlyList.adapter = adapter2
            recentlyList.addItemDecoration(SpacesItemDecoration(Space(0,  0, 0, 16, 16, 16), My))

            classicNovelButton.setOnClickListener {
                categoryButtonClick(ClassicNovel)
            }

            fairyTaleButton.setOnClickListener {
                categoryButtonClick(FairyTale)
            }

            poemButton.setOnClickListener {
                categoryButtonClick(Poem)
            }

            socialNewsButton.setOnClickListener{
                categoryButtonClick(SocialNews)
            }

            itNewsButton.setOnClickListener {
                categoryButtonClick(ItNews)
            }

            dailyNewsButton.setOnClickListener {
                categoryButtonClick(DailyNews)
            }

            bookNameInput.setOnEditorActionListener { _, actionId, event ->
                val bundle = Bundle()
                bundle.putString("book name", bookNameInput.text.toString())
                findNavController().navigate(R.id.action_home_to_searchFragment, bundle)

                true
            }

            bookNameLayout.setOnClickListener {
                bookNameInput.requestFocus()
                showKeyboard(requireContext(), bookNameInput)
            }

            container.setOnTouchListener { it, _ ->
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                it.clearFocus()
                true
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun categoryButtonClick(type: BookType) {
        binding.apply {
            classicNovelLine.visibility = View.INVISIBLE
            fairyTaleLine.visibility = View.INVISIBLE
            poemLine.visibility = View.INVISIBLE
            socialNewsLine.visibility = View.INVISIBLE
            itNewsLine.visibility = View.INVISIBLE
            dailyNewsLine.visibility = View.INVISIBLE

            val adapter = BookRecyclerAdapter(list, Category)
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {

                classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                when(type) {
                    ClassicNovel -> {
                        adapter.filterByCategory(ClassicNovel)
                        categoryList.adapter = adapter

                        classicNovelLine.visibility = View.VISIBLE
                    }
                    FairyTale -> {
                        adapter.filterByCategory(FairyTale)
                        categoryList.adapter = adapter

                        fairyTaleLine.visibility = View.VISIBLE
                    }
                    Poem -> {
                        adapter.filterByCategory(Poem)
                        categoryList.adapter = adapter

                        poemLine.visibility = View.VISIBLE
                    }
                    SocialNews -> {
                        adapter.filterByCategory(SocialNews)
                        categoryList.adapter = adapter

                        socialNewsLine.visibility = View.VISIBLE
                    }
                    ItNews -> {
                        adapter.filterByCategory(ItNews)
                        categoryList.adapter = adapter

                        itNewsLine.visibility = View.VISIBLE
                    }
                    DailyNews -> {
                        adapter.filterByCategory(DailyNews)
                        categoryList.adapter = adapter

                        dailyNewsLine.visibility = View.VISIBLE
                    }
                    else -> {

                    }
                }
            } else {
                classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))
                dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.sub_text))

                when(type) {
                    ClassicNovel -> {
                        adapter.filterByCategory(ClassicNovel)
                        categoryList.adapter = adapter

                        classicNovelLine.visibility = View.VISIBLE
                        classicNovelText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    FairyTale -> {
                        adapter.filterByCategory(FairyTale)
                        categoryList.adapter = adapter

                        fairyTaleLine.visibility = View.VISIBLE
                        fairyTaleText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    Poem -> {
                        adapter.filterByCategory(Poem)
                        categoryList.adapter = adapter

                        poemLine.visibility = View.VISIBLE
                        poemText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    SocialNews -> {
                        adapter.filterByCategory(SocialNews)
                        categoryList.adapter = adapter

                        socialNewsLine.visibility = View.VISIBLE
                        socialNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    ItNews -> {
                        adapter.filterByCategory(ItNews)
                        categoryList.adapter = adapter

                        itNewsLine.visibility = View.VISIBLE
                        itNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    DailyNews -> {
                        adapter.filterByCategory(DailyNews)
                        categoryList.adapter = adapter

                        dailyNewsLine.visibility = View.VISIBLE
                        dailyNewsText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

}