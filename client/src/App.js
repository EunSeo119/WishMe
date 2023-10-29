import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage/mainPage'
import DeskPage from './pages/deskPage/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
<<<<<<< HEAD

function App() {
  return (
=======
import WriteDeskLetter from './pages/deskLetter/writeDeskLetter'
import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
>>>>>>> 186da0691f8c7097e9766733f003517b51c9632a
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/desk/write" element={<WriteDeskLetter />} /> {/* 추가 */}
        <Route path="/school" element={<SchoolPage />}></Route>
      </Routes>
  )
}

export default App
