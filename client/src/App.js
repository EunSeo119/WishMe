import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage/mainPage'
import MainPageUuid from './pages/mainPage/mainPageUuid'
import MyPage from './pages/mypage/myPage'
import KakaoRedirectPage from './pages/mainPage/kakaoRedirectPage'
import DeskPage from './pages/deskPage/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
import CheckDeskLogin from './pages/deskLetter/checkDeskLogin'
import SelectDeskAsset from './pages/deskLetter/selectDeskAsset'
import WriteDeskLetter from './pages/deskLetter/writeDeskLetter'
import SchooLetterDetaillPage from './pages/schoolPage/schoolLetterDetail'
import SchooLetterAssetList from './pages/schoolPage/schoolLetterAssetList'
import SchooLetterWritePage from './pages/schoolPage/schoolLetterWritePage'
import SearchSchoolPage from './pages/searchSchoolPage/searchSchoolPage'
import DeveloperPage from './pages/developerPage/developerPage'
import SelectDeveloperAsset from './pages/developerPage/selectDeveloperAsset'
import WriteDeveloperLetter from './pages/developerPage/writeDeveloperLetter'
import DeveloperLetterDetail from './pages/developerPage/developerLetterDetail'
import ReplyListPage from './pages/replyPage/replyListPage'
import ReplyWritePage from './pages/replyPage/replyWritePage'
import ReplyDetailPage from './pages/replyPage/replyDetailPage'
import DeskLetterDetail from './pages/deskLetter/deskLetterDetail'
import MyLetterDetailPage from './pages/replyPage/myLetterDetailPage'
import style from './app.module.css'


function App() {
  return (
    <div className={style.app}>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/login/:deskUuid" element={<MainPageUuid />}></Route>
        <Route path="/searchSchool" element={<SearchSchoolPage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
        <Route path="/kakao/callback" element={<KakaoRedirectPage />}></Route>
        <Route
          path="/desk/:deskUuid/:letterPage?"
          element={<DeskPage />}
        ></Route>
        <Route path="/desk/:deskUuid/checkLogin" element={<CheckDeskLogin />} />
        <Route
          path="/desk/:deskUuid/selectAsset"
          element={<SelectDeskAsset />}
        />
        <Route
          path="/desk/:deskUuid/writeLetter/:assetSeq"
          element={<WriteDeskLetter />}
        />
        <Route
          path="/deskLetterDetail/:deskUuid/:letterId/:page"
          element={<DeskLetterDetail />}
        />
        {/* 학교 */}
        <Route
          path="/school/:schoolUuid/:letterPage?"
          element={<SchoolPage />}
        ></Route>
        <Route
          path="/schoolLetterDetail/:schoolUuid/:letterId/:page"
          element={<SchooLetterDetaillPage />}
        />
        <Route
          path="/schoolLetterAssetList/:schoolUuid"
          element={<SchooLetterAssetList />}
        />
        <Route
          path="/schoolLetterWritePage/:assetId/:schoolUuid"
          element={<SchooLetterWritePage />}
        />
        {/* 개발자 편지 */}
        <Route
          path="/developer/:letterPage?"
          element={<DeveloperPage />}
        ></Route>
        <Route
          path="/developer/selectAsset"
          element={<SelectDeveloperAsset />}
        />
        <Route
          path="/developer/writeLetter/:assetSeq"
          element={<WriteDeveloperLetter />}
        />
        <Route
          path="/developerLetterDetail/:page/:letterId"
          element={<DeveloperLetterDetail />}
        />
        {/* 답장 */}
        <Route path="/replyList" element={<ReplyListPage />}></Route>
        <Route path="/replyWritePage/:letterId" element={<ReplyWritePage />} />
        <Route path="/replyDetailPage/:replyId" element={<ReplyDetailPage />} />
        <Route
          path="/myLetterDetailPage/:letterId"
          element={<MyLetterDetailPage />}
        />
      </Routes>
    </div>
  )
}

export default App
