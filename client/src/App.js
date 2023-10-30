import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage/mainPage'
import MyPage from './pages/mypage/myPage'
import KakaoRedirectPage from './pages/mainPage/kakaoRedirectPage'
import DeskPage from './pages/deskPage/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
import SelectDeskAsset from './pages/deskLetter/selectDeskAsset'
import WriteDeskLetter from './pages/deskLetter/writeDeskLetter'
import SchooLetterDetaillPage from './pages/schoolPage/schoolLetterDetail'
import SchooLetterAssetList from './pages/schoolPage/schoolLetterAssetList'
import SchooLetterWritePage from './pages/schoolPage/schoolLetterWritePage'
import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/mypage" element={<MyPage />}></Route>
        <Route path="/kakao/callback" element={<KakaoRedirectPage />}></Route>
        <Route path="/desk/:deskUuid" element={<DeskPage />}></Route>
        <Route path="/desk/selectAsset" element={<SelectDeskAsset />} />
        <Route
          path="/desk/writeLetter/:assetSeq"
          element={<WriteDeskLetter />}
        />
        {/* 추가 */}
        <Route path="/school" element={<SchoolPage />}></Route>
        <Route
          path="/schoolLetterDetail/:letterId"
          element={<SchooLetterDetaillPage />}
        />
        <Route
          path="/schoolLetterAssetList/:schoolId"
          element={<SchooLetterAssetList />}
        />
        <Route
          path="/schoolLetterWritePage/:assetId"
          element={<SchooLetterWritePage />}
        />
      </Routes>
    </div>
  )
}

export default App
