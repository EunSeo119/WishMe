# Wish Me (나의 행운을 빌어줘)


## 프로젝트 개요

2023.10.09 ~ 2023.11.17

## 목차

1. [프로젝트 기획 배경](#프로젝트-기획-배경)
2. [프로젝트 목표](#프로젝트-목표)
3. [성과](#성과)
4. [주요기능](#주요기능)
5. [서비스 화면](#서비스-화면)
6. [기술차별점](#기술차별점)
7. [확장가능성](#확장가능성)
8. [홍보](#홍보)
9. [개발환경](#개발환경)
10. [프로젝트 구조](#프로젝트-구조)
11. [서비스 아키텍쳐](#서비스-아키텍쳐)
12. [협업환경](#협업-환경)
13. [팀원](#팀원)
14. [프로젝트 산출물](#프로젝트-산출물)
15. [프로젝트 발표자료](#프로젝트-발표자료)

## 프로젝트 기획 배경

롤링페이퍼를 작성하거나 기프티콘을 보내는 등 고생하는 수험생들을 응원하기 위한 활동이 주변에서 많이 이루어집니다. 하지만 응원할 수 있는 사람이 제한적이고, 응원을 받더라도 분실 혹은 훼손의 가능성이 있습니다.
<br/>

저희 Wish Me는 URL 공유를 통해 SNS로 누구에게나 손쉽게 응원을 주고 받을 수 있고 이미지로 편지를 저장해 안전하게 보관할 수 있습니다.
<br/>


## 프로젝트 목표

저희 Wish Me는 크게 다음과 같은 3가지 목표가 있었습니다.

1. 500명 이상의 다양한 실 사용자 유치
2. 안전한 편지 데이터 관리
3. 적극적인 유저의 니즈 파악 및 빠른 반영

성공적인 서비스 운영을 위해서는 실 서비스 사용자의 수가 최대한 많아야 한다고 생각하였습니다. 하여 최대한 빠르게 배포를 진행한 뒤 애자일하게 기능을 추가 및 수정해나갔습니다.

빠른 배포 후 SNS, ask, 수능 관련 카페, 오픈 채팅방 등을 통해 다양한 홍보를 진행하였습니다.

모인 사용자들의 실제 니즈를 파악하기 위해 개발자 책상(문의게시판), 구글 폼(버그리포트) 등을 받았고 모니터링 툴(Google Analytics, Clarity, 그라마나, 프로메테우스)을 사용하여 개선점을 찾고 수정하여 수시로 반영하였습니다.

또한 가장 중요한 유저데이터와, 편지 데이터들은 수시로 외부서버 DB로 백업하고 내용은 암호화를 하여 관리하였습니다.



## 성과

성과~~


## 주요기능

**개인 편지 보내기**

- 공유받은 URL로 책상에 접속하여 책상위에 올릴 선물을 선택하고, 응원의 편지를 작성할 수 있습니다. 
- 공개 여부를 선택할 수 있으며, 비공개인 경우 책상의 주인만 확인할 수 있습니다. 모든 편지는 암호화되어 저장됩니다.


**책상 공유하기**

- 자신만의 책상을 카카오톡/링크 공유를 통해 URL로 전달할 수 있습니다.
- 책상은 URL을 통해 접속한 사람만이 볼 수 있는 공간으로 책상위에 선물을 통해 편지를 확인하고 작성할 수 있습니다. 


**편지 필터링**

- 편지 작성을 완료한 후에 chatgpt를 이용하여 편지 내용을 필터링 합니다. 
- 욕설은 하트로 변환이 되고, 부정적인 표현이 감지되면 편지 확인 시에 모달을 통해 알림을 줍니다.


**답장**
- 책상의 주인은 응원의 편지에 답장을 작성할 수 있습니다. 
- 편지의 작성자는 답장함을 통해 답장과 작성한 편지를 확인할 수 있습니다. 


**학교 편지 보내기**

- 학교 검색 또는 공유받은 학교 URL을 통해 학교 칠판에 접속할 수 있습니다.
- 칠판에 문구를 선택하여 응원의 편지를 확인할 수 있습니다.
- 칠한에 올릴 문구를 선택하고, 응원의 메시지를 작성하여 게시할 수 있습니다.

**신고하기**
- 비방·비하·욕설 편지를 신고할 수 있습니다.

**다운로드**
- 간직하길 원하는 편지를 다운로드 할 수 있습니다.

**마이페이지**
- 개인 책상에 남겨지는 닉넥임을 수정할 수 있고, 학교를 지정하여 저장할 수 있습니다.

**버그 신고하기**
- 개발자 책상을 이용하여 편지를 남기거나, 버그 신고하기 메뉴를 통해 오류를 신고할 수 있습니다.


## 서비스 화면

<img src="img/home.jpg" height="700px">


### 개인 책상 


**개인 책상 접속**


<img src="img/mydesk.gif" height="700px">

- `카카오톡으로 로그인` 버튼을 누르면 로그인 후 `개인 책상`으로 이동합니다.
- 처음 회원가입한 회원이라면 빈 책상이 생성됩니다.

**개인 책상 공유**

<img src="img/shareMydesk.gif" height="700px" >

- `내 책상 공유하기` 버튼을 누르면 공유 방법을 선택할 수 있습니다.
- `카카오톡 공유하기` , `링크 공유하기`를 통해 자신만의 책상을 공유할 수 있습니다. 책상은 URL을 통해 접속한 사람만이 확인 가능합니다.

**개인편지 작성**

<img src="img/writeDeskLetter_1.gif" height="700px" >
<img src="img/writeDeskLetter_2.gif" height="700px" >

- 공유받은 URL을 통해 친구의 책상에 접속할 수 있습니다. `응원하기` 버튼을 통해 응원의 편지를 남길 수 있습니다.
- 응원의 편지를 답장을 받기를 원하다면 로그인이 필요합니다.
- 책상 위에 올려놓을 선물을 선택하고 응원의 편지를 작성할 수 있습니다. 공개여부를 선택할 수 있습니다.


**개인 편지 확인**

<img src="img/checkMyLetter.gif" height="700px" >
<img src="img/reportMyLetter.gif" height="700px" >

- 책상에 응원의 선물을 선택하여 편지를 확인할 수 있습니다. 
- 간직하고 싶은 편지라면 `다운로드`하여 소장할 수 있습니다.
- 부정적인 내용이 담긴 편지라면 열럼 여부를 미리 확인하고 있습니다. `신고` 버튼을 통해 부정적인 편지를 신고하여 책상에서 삭제할 수 있습니다. 


**답장 작성**

<img src="img/writeReply.gif" height="700px" >

- 편지 확인 화면에서 `답장하기` 버튼을 통해 답장을 작성할 수 있습니다. 답장 편지의 색상을 선택 후 응원의 답장을 남길 수 있습니다.

**답장 확인**

<img src="img/checkReply.gif" height="700px" >

- 메뉴바의 `답장함 가기`를 통해 받은 답장 목록을 확인할 수 있습니다.
- 하트 답장을 누르면 받은 답장을 확인할 수 있고, `내 책상 공유하기` 버튼을 누르면 내가 작성한 응원의 편지도 확인할 수 있습니다.


### 학교 칠판

**학교 칠찬 접속**

<img src="img/searchSchool.gif" height="700px" >

- 홈화면의 `로그인하지 않고 학교 응원 구경하기` 또는 메뉴바의 `학교 칠판 구경하기`를 통해 학교 검색 화면을 접속할 수 있습니다.
- 학교명의 검색을 통해 `학교 칠판`으로 접속할 있습니다.

**학교 편지 작성**

<img src="img/writeSchoolLetter.gif" height="700px" >

- `응원 하기`버튼을 통해 응원의 편지를 남갈 수  있습니다. 학교 칠판에 올라갈 응원의 문구를 선택하고 닉네임과 함께 응원의 편지를 작성할 수 있습니다.
- 학교 편지는 모두에게 공개되는 편지입니다. 부적절한 글에는 신고가 가능합니다.


### 메뉴바

**마이페이지**

<img src="img/mypage.gif" height="700px" >

- 메뉴바의 `마이페이지`로 이동하면 닉네임과 학교를 수정할 수 있습니다. 학교를 변경하면, 내 책상에서도 학교로 바로 이동할 수 있습니다.

**개발자 편지**

<img src="img/developerLetter.gif" height="700px" >

- 메뉴바의 `개발자 책상 가기`로 이동하면 개발자에게 문의하거나 편지를 작성할 수 있습니다. 




## 기술차별점

기술차별점~~

### 확장가능성

확장가능성~~

## 홍보

홍보~~

## 개발환경

- AWS
    - ubuntu - 20.04
    - nginx - 1.18.0
    - docker - 24.0.6
    - docker-compose - 2.21.0
    - Openjdk - 11.0.20.1
    - S3
- BackEnd
    - Java - 11
    - springboot - 2.7.17
- FrontEnd
    - node - 18.13
    - nginx - 1.25.3
- Monitoring
    - node exporter - 1.6.1
    - grafana - 9.0.5
    - prometheus - 2.47.2
- IDE
    - VS code
    - intelliJ IDEA
- 외부 활용 기능
    - 카카오 API


## 프로젝트 구조

### Frontend (React)

```
client
├── src
│   ├── apis
│   │   └── tokenHttp
│   ├── common
│   │   └── Header
│   ├── fonts
│   ├── Modal
│   │   ├── letterReportModal
│   │   ├── shareURLModal
│   │   └── MainPopup
│   ├── pages
│   │   ├── deskLetter
│   │   ├── deskPage
│   │   ├── developerPage
│   │   ├── mainPage
│   │   ├── deskPage
│   │   ├── replyPage
│   │   ├── schoolPage
│   │   └── searchSchoolPage
│   ├── APP
│   ├── Hotjar
│   ├── reportWebVitals
│   └── RouteChangeTracker
├── env
├── package.json
```

### Backend (Spring Boot)

```
user
├── domain
│   ├── Asset
│   ├── Reply
│   ├── MyLetter
│   ├── School
│   ├── SchoolLetter
│   └── User
├── school
│   └── model
│       └── repository
├── user
│   ├── controller
│   └── model
│       ├── dto
│       ├── repository
│       └── service
├── util
│   ├── AES256
│   ├── kakaoUtil
│   └── JwtUtil
```

```
myLetter
├── asset
│   ├── domain
│   ├── dto
│   └── repository
├── myLetter
│   ├── controller
│   ├── domain
│   ├── dto
│   ├── repository
│   └── service
├── openAPI
│   ├── controller
│   ├── dto
│   └── service
├── school
│   └── domain
├── user
│   ├── controller
│   ├── domain
│   ├── dto
│   ├── repository
│   └── service
├── config
│   ├── AES256
│   └── repository
├── common
│   ├── domain
│   ├── exception
│   └── response
├── util
│   ├── AES256
│   ├── BadWordFilter
│   ├── kakaoUtil
│   └── JwtUtil
```

```
schoolLetter
├── school
│   ├── controller
│   ├── domain
│   ├── repository
│   └── service
├── schoolLetter
│   ├── controller
│   ├── domain
│   ├── dto
│   ├── repository
│   └── service
├── asset
│   ├── domain
│   ├── dto
│   └── repository
├── config
│   ├── AES256
│   └── repository
├── common
│   ├── exception
│   ├── response
│   └── service
```


## 와이어프레임

![wireframe](./img/Figma_image.png)

## ERD

![erd](./img/ERD_Wishme.png)

## 서비스 아키텍쳐

![architecture](./img/architecture.png)

## 협업 환경

### Git으로 협업하기


브랜치는 develop, develop-test, develop-be, develop-fe, feature를 사용했으며 전략은 다음과 같습니다.

- `develop`: 서비스가 배포될 수 있는 브랜치입니다. develop 브랜치에 올라온 기능들은 에러 없이 작동하는 상태입니다.

- `develop-test`: 다음 서비스 출시를 위해 실제 개발이 이루어지는 브랜치입니다. develop 브랜치에 올라가기전 frontend와 backend의 통합 테스트를 위한 브랜치입니다.

- `develop-be`, `develop-fe` : 기능 개발이 완료된 브랜치를 병합하여 각 fontend와 backend 환경에서 실제 기능이 정상적으로 수행되는지 테스트를 위한 브랜치 입니다.

- `feature`: 기능 단위 개발을 위한 브랜치로 develop에서 분기하여 개발이 끝나면 각각 베이스 브랜치로 병합됩니다.



### Jira로 협업하기

매주 월요일 스프린트 회의를 통해 그 주의 목표를 세우고 목표 달성을 위한 구체적인 작업들을 정리했습니다.
팀 회의와 같은 공통적인 일정부터 파트별 회의, 개인 개발 작업까지 구체적으로 계획했습니다.

이를 위해 사용된 요소들은 다음과 같습니다.

`에픽`: 어떤 작업이 속하는 레벨로 공통(설계, 회의), 백엔드, 프론트, 인프라 총 4가지 에픽을 만들어 사용하였습니다.

`스토리`: 에픽에 속하는 작업의 단위입니다. 구체적인 작업 내용을 작성하고 스토리 포인트로 예상 소요 시간을 산정할 수 있습니다. 한 스토리 당 최대 4시간을 넘지 않게 하였고 개인별로 매주 40시간 이상 할당했습니다.

`번다운 차트`: 스프린트의 목표를 달성하기 위해 남은 시간과 남은 스토리 포인트를 확인해 프로젝트의 진척도를 파악할 수 있는 지표입니다.


### Notion으로 협업하기

컨벤션, 개발 노트, 미팅, 자료함 등을 Notion을 통해 작성 및 관리하였습니다.


- `컨벤션`: 프로젝트의 컨벤션들을 문서화하여 모두가 공유 가능하도록 하였습니다. Git Branch 컨벤션, Commit 컨벤션이 있습니다.  
- `개발 노트`: 서버 환경 설정과 설치 과정, 개발하면서 만난 오류와 문제 상황을 정리하고 원인과 해결방법을 정리하였습니다.
- `미팅`: 아이디어 회의, 기획 회의, 개발 회의 등을 회의록으로 기록하였습니다.
- `자료함`: 요구사항 정의서, 기능명세서, API 명세서 등 공유 문서를 기록하여 모두가 동일한 목표를 가지고 개발 할 수 있도록 하였습니다.



## 팀원


|                                                                    [김은서](https://github.com/EunSeo119)                                                                    |                        [이효경](https://github.com/HyoKyoung1004)                         |                           [조해린](https://github.com/zosunny)                           |                           [최준서](https://github.com/wnstj7788/wnstj7788)                            |                        [허다은](https://github.com/qor4)                         |                        
| :-----------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | 
| <img src="https://avatars.githubusercontent.com/u/64001133?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/90086799?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/104357560?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/61938768?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/58421346?v=4" width="100" height="100"> |
|                                                                               **Full-Stack**                                                                                |                                        **Full-Stack**                                        |                                        **Full-Stack**                                        |                                       **Infra**                                        |                                      **Infra**                                       |                                       


## 프로젝트 산출물

### 요구사항명세서
![요구사항명세서](./img/demand.png)

### API 명세서
![API_1](./img/api1.png)
![API_2](./img/api2.png)

## 프로젝트 발표자료

발표자료~~

