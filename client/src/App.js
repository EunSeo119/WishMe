import React from 'react'
import { Route, Routes } from 'react-router-dom'
import MainPage from './pages/mainPage/mainPage'
import DeskPage from './pages/deskPage'
import SchoolPage from './pages/schoolPage/schoolPage'
import style from './app.module.css'

function App() {
  return (
    <div className={style.app}>
      <img src="assets/desk.png" className={style.bg} />
      <Routes>
        <Route path="/" element={<MainPage />}></Route>
        <Route path="/desk" element={<DeskPage />}></Route>
        <Route path="/school" element={<SchoolPage />}></Route>
      </Routes>
    </div>
  )
}

export default App
