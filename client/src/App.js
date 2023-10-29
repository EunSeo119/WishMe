import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage'
import DeskPage from './pages/deskPage/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
import SelectDeskAsset from './pages/deskLetter/selectDeskAsset'
import WriteDeskLetter from './pages/deskLetter/writeDeskLetter'
import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/desk/selectAsset" element={<SelectDeskAsset />} />
        <Route path="/desk/writeLetter/:assetSeq" element={<WriteDeskLetter />} />
        {/* 추가 */}
        <Route path="/school" element={<SchoolPage />}></Route>
      </Routes>
    </div>
  );
}

export default App;
