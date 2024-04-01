# Knowledge_Query
> 지식 쿼리
![thumbnail](https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/5b4df94e-7a84-4b0f-a096-9d86d3d95a96)


## 프로젝트 소개

- 플랫폼 : 안드로이드/모바일
- 제작 인원 : 1 인 (개인 프로젝트)
- 제작 기간 : 14 일 (2023.10.29 ~ 2023.11.10)
- 사용 기술 : Kotlin, XML, ViewBinding, Firebase, Glide, Room, Retrofit2

현재 많은 초중고 학생들과 MZ세대들은 낮아진 문해력과 긴 글 까막눈을 호소하고 있으며<br>
제 주위에도 많은 친구들이 수행 평가나 과제를 하기 위해 원하는 답을 긴 글에서 보고 있습니다<br>
그래서 저는 SQL 쿼리 기능처럼 원하는 답만 쉽게 찾을 수 있는 앱인 지식 쿼리를 제작하였습니다



## 사용 예제

### 영상

https://www.youtube.com/watch?v=jjeG2BGt5ns

### 스샷

<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/1af28e68-41b4-4d17-9bdf-1fc5833f48ac" width="40%" height="40%">
<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/c5885cb5-3f7e-4fd8-85e8-e06720375b86" width="40%" height="40%">
<br><br>

<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/1513d606-7d48-4e6a-bb5a-845f933ecf1c" width="20%" height="20%">
<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/812b652b-15b3-42bd-b9a1-02ada802af4e" width="20%" height="20%">
<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/3704650b-0348-45f6-a450-074d225a20b9" width="20%" height="20%">
<img src="https://github.com/JangWoojun/Knowledge_Query_Original/assets/102157871/0d3a9a45-3f87-4edd-9389-b9ababbb512f" width="20%" height="20%">



## 구현 기능

- 홈
    - 다크 모드 & 라이트 모드
    - 저작권 만료 서적을 무료로 감상
    - 등록 된 저작권 만료 서적 검색
- 내 서재 
    - .txt 파일을 등록
    - 등록한 서적 검색
    - 등록한 서적 바로가기
- 질문하기
    - 기계 독해 API를 통해 채팅 식 질문으로 긴 글에서 유저가 원하는 답을 뽑아주는 기능
- E-BOOK 리더
    - 위키 백과 API를 통해 독서중 모르는 단어를 검색 가능하는 기능
    - .txt 파일을 뷰어로 보여주는 기능


<br>

## 배운 점 & 아쉬운 점 & 이슈

배운 점 및 아쉬운 점, 이슈 등은 블로그 회로록으로 더욱 자세히 정리하였습니다. 관심 있으시다면 해당 [포스트]()를 확인해주세요.

### 배운 점

1. Firebase를 통해 혼자서는 구현하기 오래 걸리고 새로 배워야하는 비회원 로그인 기능이나 데이터베이스 부분을 Firebase를 이용해 구현해볼 수 있었고
해당 기능등을 Firebase를 통해 구현하는 법에 대해 배울 수 있었습니다.

1. 다른 앱을 보면서 꼭 구현하고 싶었던 기능을 Cardstackview 라는 오픈 소스를 이용하여 구현해보며 처음으로 오픈 소스를 사용해보는 경험을 해볼 수 있었고
Cardstackview 와 Toasty 라는 오픈 소스들을 사용해보며 오픈 소스를 사용하는 법에 대해 알 수 있었습니다.

<br>

### 아쉬운 점
1. Firebase에 Realtime Database를 사용하여 데이터베이스를 구현했지만 데이터베이스에서 데이터를 읽는 속도가 만족스럽지 않았습니다.
이번에는 로딩 중이라는 걸 유저들에게 알려주어 앱이 반응이 없다고 유저가 생각하지 않게 해였으나, 다음번 프로젝트에는 스스로 백엔드를 배우거나 백엔드 역할을 하는
팀원과 같이 작업하여 속도를 증가시키는 방법 또는 효율적으로 데이터를 읽는 방법을 배워 읽는 속도 부분을 개선할 수 있도록 노력해 봐야겠습니다.

1. 구현을 최우선 목표로 개발을 했기 때문에 코드에 주석 처리가 미흡하며 코드가 깔끔하지 않은 점이 아쉽습니다. 개인적으로 구현 능력은 어느 정도
확인이 되었다고 생각하기에 다음 프로젝트부터는 클린 코드 원칙을 적용하고 주석을 남겨 제3자 혹은 시간이 지난 후에 코드를 읽더라도 이해하기 쉽게
코드를 구성해야겠습니다
