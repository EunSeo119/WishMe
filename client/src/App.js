import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage'
import DeskPage from './pages/deskPage/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
import SchooLetterDetaillPage from './pages/schoolPage/schoolLetterDetail'
import SchooLetterAssetList from './pages/schoolPage/schoolLetterAssetList'

import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
      {/* <img src="assets/desk.png" className={style.bg} /> */}
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/school" element={<SchoolPage />}></Route>
        <Route
          path="/schoolLetterDetail/:letterId"
          element={<SchooLetterDetaillPage />}
        />
        <Route
          path="/schoolLetterAssetList/:schoolId"
          element={<SchooLetterAssetList />}
        />
      </Routes>
    </div>
  )
}

export default App
