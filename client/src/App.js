import React from 'react'
import { Route, Routes } from 'react-router-dom'
<<<<<<< HEAD
import MainPage from './pages/mainPage/mainPage'
import DeskPage from './pages/deskPage'
=======
import MainPage from './pages/mainPage'
import DeskPage from './pages/deskPage/deskPage'
>>>>>>> e16a8e67bf53d55c46a6536c0f6b7ad7261e0607
import SchoolPage from './pages/schoolPage/schoolPage'
import WriteDeskLetter from './pages/deskLetter/writeDeskLetter'
import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/desk/write" element={<WriteDeskLetter />} /> {/* 추가 */}
        <Route path="/school" element={<SchoolPage />}></Route>
      </Routes>
    </div>
  )
}

export default App
